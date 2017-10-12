/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.test;

import java.util.ArrayList;
import java.util.List;

import org.joice.cache.redis.ShardedJedisCacheManager;
import org.joice.cache.serializer.HessianSerializer;
import org.joice.cache.test.domain.Department;
import org.joice.cache.test.domain.Employee;
import org.joice.cache.to.CacheKeyTO;
import org.joice.cache.to.CacheWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 测试ShardedJedisCacheManager
 * @author HuHui
 * @version $Id: ShardedJedisCacheTest.java, v 0.1 2017年10月12日 下午5:19:17 HuHui Exp $
 */
public class ShardedJedisCacheTest {

    private ShardedJedisPool         shardedJedisPool;

    private ShardedJedisCacheManager jedisCache;

    private Employee                 emp;

    @Before
    public void init() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(200);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(1);
        jedisPoolConfig.setMaxWaitMillis(2000);
        jedisPoolConfig.setTestOnBorrow(false);
        jedisPoolConfig.setTestOnReturn(false);
        jedisPoolConfig.setTestWhileIdle(false);

        JedisShardInfo info1 = new JedisShardInfo("168.33.130.79", 6379, "redis-server-a");
        JedisShardInfo info2 = new JedisShardInfo("168.33.131.55", 6379, "redis-server-b");
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        shards.add(info1);
        shards.add(info2);

        shardedJedisPool = new ShardedJedisPool(jedisPoolConfig, shards);

        jedisCache = new ShardedJedisCacheManager(new HessianSerializer());
        jedisCache.setShardedJedisPool(shardedJedisPool);

        //组装要缓存的对象
        Department dept = new Department(1, "水泊梁山");
        emp = new Employee(1, "宋江", dept);

    }

    @Test
    public void testShard() {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        for (int i = 0; i < 30; i++) {
            Jedis shard = shardedJedis.getShard(i + "");
            shardedJedis.set(i + "", "val: " + i);
            System.out.println(shard.getClient().getHost() + ", key=" + i);
        }
    }

    @Test
    public void testSetWithoutExpire() throws Exception {
        Assert.assertNotNull(jedisCache);

        CacheKeyTO cacheKeyTO = new CacheKeyTO("ShardedJedisCache", emp.getId() + "", null);
        CacheWrapper<Object> wrapper = new CacheWrapper<Object>();
        wrapper.setCacheObject(emp);

        jedisCache.setCache(cacheKeyTO, wrapper, null, null);

        CacheWrapper<Object> ret = jedisCache.get(cacheKeyTO, null, null);

        Assert.assertNotNull(ret);
        Assert.assertTrue(((Employee) ret.getCacheObject()).getId() == emp.getId());

    }

    @Test
    public void testSetWithExpire() throws Exception {
        CacheKeyTO cacheKeyTO = new CacheKeyTO("ShardedJedisCache", emp.getId() + "", null);
        CacheWrapper<Object> wrapper = new CacheWrapper<Object>(emp, 5);
        jedisCache.setCache(cacheKeyTO, wrapper, null, null);
        CacheWrapper<Object> ret = jedisCache.get(cacheKeyTO, null, null);

        Assert.assertNotNull(ret);
        Assert.assertTrue(((Employee) ret.getCacheObject()).getId() == emp.getId());

        Thread.sleep(5500);

        ret = jedisCache.get(cacheKeyTO, null, null);
        Assert.assertNull(ret);
    }

    @Test
    public void testDelete() throws Exception {
        CacheKeyTO cacheKeyTO = new CacheKeyTO("ShardedJedisCache", emp.getId() + "", null);
        jedisCache.delete(cacheKeyTO);
        CacheWrapper<Object> ret = jedisCache.get(cacheKeyTO, null, null);
        Assert.assertNull(ret);
    }

}
