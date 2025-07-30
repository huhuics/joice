/**
 * 
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.redis;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.joice.cache.Cache;
import org.joice.cache.config.CacheConfig;
import org.joice.cache.serializer.HessianSerializer;
import org.joice.cache.serializer.StringSerializer;
import org.joice.cache.to.CacheKey;
import org.joice.cache.to.CacheWrapper;
import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

/**
 * 基于ShardedJedis实现Redis缓存管理
 * @author HuHui
 * @version $Id: ShardedJedisCache.java, v 0.1 2017年10月24日 上午11:37:06 HuHui Exp $
 */
public class ShardedJedisCache implements Cache {

    private static final Logger                   logger      = LoggerFactory.getLogger(ShardedJedisCache.class);

    /** 缓存配置类 */
    private final CacheConfig                     config;

    private final ShardedJedis                    shardedJedis;

    private final StringSerializer                stringSerializer;

    private final HessianSerializer<CacheWrapper> hessianSerializer;

    /** mutex过期时间,单位：秒 */
    private static final int                      EXPIRE_TIME = 20;

    private static final String                   MUTEX_VAL   = "temp_mutex_val";

    public ShardedJedisCache(ShardedJedis shardedJedis) {
        this(shardedJedis, new CacheConfig());
    }

    public ShardedJedisCache(ShardedJedis shardedJedis, CacheConfig config) {
        LogUtil.info(logger, "ShardedJedisCache init...");
        this.config = config;
        this.shardedJedis = shardedJedis;
        stringSerializer = new StringSerializer();
        hessianSerializer = new HessianSerializer<CacheWrapper>();
        LogUtil.info(logger, "ShardedJedisCache init success!");
    }

    @Override
    public void set(CacheKey cacheKey, CacheWrapper wrapper) {
        String key;
        if (cacheKey == null || StringUtils.isEmpty(key = cacheKey.getKey()) || wrapper == null) {
            return;
        }

        int exp = wrapper.getExpireTime();
        if (exp > 0) {
            shardedJedis.setex(stringSerializer.serialize(key), exp, hessianSerializer.serialize(wrapper));
        } else {
            shardedJedis.set(stringSerializer.serialize(key), hessianSerializer.serialize(wrapper));
        }
    }

    @Override
    public CacheWrapper get(CacheKey cacheKey) {
        String key;
        if (cacheKey == null || StringUtils.isEmpty(key = cacheKey.getKey())) {
            return null;
        }

        CacheWrapper wrapper = null;
        try {
            byte[] bytes = shardedJedis.get(stringSerializer.serialize(key));
            wrapper = hessianSerializer.deserialize(bytes);
        } catch (Exception e) {
            LogUtil.error(e, logger, "查询Redis缓存失败,cacheKey={0}", cacheKey);
        }
        if (wrapper != null) {
            wrapper.setLastAccessTime(System.currentTimeMillis());
        }

        return wrapper;
    }

    @Override
    public Long delete(CacheKey cacheKey) {
        String key;
        if (cacheKey == null || StringUtils.isEmpty(key = cacheKey.getKey())) {
            return 0L;
        }
        return shardedJedis.del(stringSerializer.serialize(key));
    }

    @Override
    public void clear() {
        Collection<Jedis> shards = shardedJedis.getAllShards();
        Iterator<Jedis> iterator = shards.iterator();
        while (iterator.hasNext()) {
            Jedis jedis = iterator.next();
            jedis.flushDB();
        }
    }

    @Override
    public void shutdown() {
        shardedJedis.close();
    }

    public ShardedJedis getShardedJedis() {
        return shardedJedis;
    }

    @Override
    public CacheConfig getConfig() {
        return config;
    }

    @Override
    public Long setMutex(CacheKey cacheKey) {
        String key;
        if (cacheKey == null || StringUtils.isEmpty(key = cacheKey.getKey())) {
            return 0L;
        }
        Long ret = shardedJedis.setnx(stringSerializer.serialize(key), stringSerializer.serialize(MUTEX_VAL));
        shardedJedis.expire(stringSerializer.serialize(key), EXPIRE_TIME);

        return ret;
    }
}
