/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.redis;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.joice.cache.CacheManager;
import org.joice.cache.exception.CacheCenterConnectionException;
import org.joice.cache.serializer.Serializer;
import org.joice.cache.serializer.StringSerializer;
import org.joice.cache.to.CacheKeyTO;
import org.joice.cache.to.CacheWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 基于分片模式的缓存管理器
 * @author HuHui
 * @version $Id: ShardedJedisCacheManager.java, v 0.1 2017年10月12日 下午12:29:15 HuHui Exp $
 */
public class ShardedJedisCacheManager implements CacheManager {

    private static final Logger             logger             = LoggerFactory.getLogger(ShardedJedisCacheManager.class);

    private static final StringSerializer   keySerializer      = new StringSerializer();

    private final Serializer<Object>        serializer;

    private ShardedJedisPool                shardedJedisPool;

    /**
     * Hash的缓存时长
     * 等于0时永久缓存;大于0时,主要是为了防止一些已经不用的缓存占用内存;小于0时,则使用@Cache中设置的expire值
     */
    private int                             hashExpire         = -1;

    /** 是否通过脚本来设置Hash的缓存时长 */
    private boolean                         hashExpireByScript = false;

    private static final Map<Jedis, byte[]> hashSetScriptSha   = new ConcurrentHashMap<Jedis, byte[]>();

    private static byte[]                   hashSetScript;

    static {
        try {
            String tmpScript = "redis.call('HSET', KEYS[1], KEYS[2], ARGV[1]);\nredis.call('EXPIRE', KEYS[1], tonumber(ARGV[2]));";
            hashSetScript = tmpScript.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            logger.error("", ex);
        }
    }

    private static byte[]                   delScript;

    static {
        StringBuilder tmp = new StringBuilder();
        tmp.append("local keys = redis.call('keys', KEYS[1]);\n");
        tmp.append("if(not keys or #keys == 0) then \n return nil; \n end \n");
        tmp.append("redis.call('del', unpack(keys)); \n return keys;");
        try {
            delScript = tmp.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public ShardedJedisCacheManager(Serializer<Object> serializer) {
        this.serializer = serializer;
    }

    @Override
    public void setCache(CacheKeyTO cacheKeyTO, CacheWrapper<Object> wrapper, Method method, Object[] args) throws CacheCenterConnectionException {
        if (shardedJedisPool == null || cacheKeyTO == null) {
            return;
        }
        String cacheKey = cacheKeyTO.getCacheKey();
        if (StringUtils.isEmpty(cacheKey)) {
            return;
        }
        ShardedJedis shardedJedis = null;
        try {
            int expire = wrapper.getExpire();
            shardedJedis = shardedJedisPool.getResource();
            Jedis jedis = shardedJedis.getShard(cacheKey);
            String hfield = cacheKeyTO.getHfield();
            if (StringUtils.isEmpty(hfield)) {
                if (expire == 0) {
                    jedis.set(keySerializer.serialize(cacheKey), serializer.serialize(wrapper));
                } else if (expire > 0) {
                    jedis.setex(keySerializer.serialize(cacheKey), expire, serializer.serialize(wrapper));
                }
            } else {
                hashSet(jedis, cacheKey, hfield, wrapper);
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            closeResource(shardedJedis);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public CacheWrapper<Object> get(CacheKeyTO cacheKeyTO, Method method, Object[] args) throws CacheCenterConnectionException {
        if (null == shardedJedisPool || null == cacheKeyTO) {
            return null;
        }
        String cacheKey = cacheKeyTO.getCacheKey();
        if (StringUtils.isEmpty(cacheKey)) {
            return null;
        }
        CacheWrapper<Object> res = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            Jedis jedis = shardedJedis.getShard(cacheKey);
            byte bytes[] = null;
            String hfield = cacheKeyTO.getHfield();
            if (StringUtils.isEmpty(hfield)) {
                bytes = jedis.get(keySerializer.serialize(cacheKey));
            } else {
                bytes = jedis.hget(keySerializer.serialize(cacheKey), keySerializer.serialize(hfield));
            }
            Type returnType = null;
            if (method != null) {
                returnType = method.getGenericReturnType();
            }
            res = (CacheWrapper<Object>) serializer.deserialize(bytes, returnType);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            closeResource(shardedJedis);
        }
        return res;
    }

    @Override
    public void delete(CacheKeyTO cacheKeyTO) throws CacheCenterConnectionException {
        if (shardedJedisPool == null || cacheKeyTO == null) {
            return;
        }
        String cacheKey = cacheKeyTO.getCacheKey();
        if (StringUtils.isEmpty(cacheKey)) {
            return;
        }
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if ("*".equals(cacheKey)) {
                Collection<Jedis> shards = shardedJedis.getAllShards();
                for (Jedis jedis : shards) {
                    jedis.flushDB();
                }
            } else if (cacheKey.indexOf("*") != -1) {
                // 如果key中带有*或?通配符,则应该使用批量删除,这将遍历所有Redis服务器,性能较差,不建议使用
                // 建议使用hash表方式缓存需要批量删除的数据
            } else {
                Jedis jedis = shardedJedis.getShard(cacheKey);
                String hfield = cacheKeyTO.getHfield();
                if (StringUtils.isEmpty(hfield)) {
                    jedis.del(keySerializer.serialize(cacheKey));
                } else {
                    jedis.hdel(keySerializer.serialize(cacheKey), keySerializer.serialize(hfield));
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            closeResource(shardedJedis);
        }
    }

    private void hashSet(Jedis jedis, String cacheKey, String hfield, CacheWrapper<Object> wrapper) throws Exception {
        byte[] key = keySerializer.serialize(cacheKey);
        byte[] field = keySerializer.serialize(hfield);
        byte[] val = serializer.serialize(wrapper);
        int hExpire;
        if (hashExpire < 0) {
            hExpire = wrapper.getExpire();
        } else {
            hExpire = hashExpire;
        }
        if (hExpire == 0) {
            jedis.hset(key, field, val);
        } else if (hExpire > 0) {
            if (hashExpireByScript) {
                // TODO 通过脚本来设置Hash的缓存时长 
            } else {
                Pipeline pipe = jedis.pipelined();
                pipe.hset(key, field, val);
                pipe.expire(key, hExpire);
                pipe.sync();
            }
        }
    }

    private void closeResource(ShardedJedis shardedJedis) {
        shardedJedis.close();
    }

    public ShardedJedisPool getShardedJedisPool() {
        return shardedJedisPool;
    }

    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

    public int getHashExpire() {
        return hashExpire;
    }

    public void setHashExpire(int hashExpire) {
        if (hashExpire < 0) {
            return;
        }
        this.hashExpire = hashExpire;
    }

    public boolean isHashExpireByScript() {
        return hashExpireByScript;
    }

    public void setHashExpireByScript(boolean hashExpireByScript) {
        this.hashExpireByScript = hashExpireByScript;
    }

}
