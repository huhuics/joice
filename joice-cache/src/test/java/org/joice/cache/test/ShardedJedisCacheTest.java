/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.test;

import java.util.ArrayList;
import java.util.List;

import org.joice.cache.redis.ShardedJedisCache;
import org.joice.cache.test.domain.Department;
import org.joice.cache.test.domain.Employee;
import org.joice.cache.to.CacheKey;
import org.joice.cache.to.CacheWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

/**
 * 测试基于Redis的缓存
 * @author HuHui
 * @version $Id: ShardedJedisCacheTest.java, v 0.1 2017年10月24日 下午3:03:31 HuHui Exp $
 */
public class ShardedJedisCacheTest {

    private ShardedJedisCache cache;

    private CacheKey          cacheKey1, cacheKey2;

    private CacheWrapper      wrapper1, wrapper2;

    private int               expTime = 3;

    @Before
    public void init() {
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        JedisShardInfo info1 = new JedisShardInfo("168.33.131.55", 6379, "redis-machine-1");
        JedisShardInfo info2 = new JedisShardInfo("168.33.130.79", 6379, "redis-machine-2");
        shards.add(info1);
        shards.add(info2);

        ShardedJedis shardedJedis = new ShardedJedis(shards);
        cache = new ShardedJedisCache(shardedJedis);

        //初始化数据
        Employee emp1 = new Employee(1L, "卢俊义", new Department(1L, "管理层"));
        Employee emp2 = new Employee(2L, "李逵", new Department(2L, "陆军"));

        cacheKey1 = new CacheKey(emp1.getId() + "");
        wrapper1 = new CacheWrapper(emp1);
        cacheKey2 = new CacheKey(emp2.getId() + "");
        wrapper2 = new CacheWrapper(emp2, expTime);//expire

        cache.set(cacheKey1, wrapper1);
        cache.set(cacheKey2, wrapper2);
    }

    @Test
    public void testCache() throws Exception {
        Assert.assertNotNull(cache);

        //测试get
        CacheWrapper cacheWrapper1 = cache.get(cacheKey1);
        CacheWrapper cacheWrapper2 = cache.get(cacheKey2);
        Assert.assertTrue(wrapper1.getCreateTime() == cacheWrapper1.getCreateTime());
        Assert.assertTrue(wrapper1.getExpireTime() == cacheWrapper1.getExpireTime());
        Assert.assertTrue(((Employee) wrapper1.getObj()).getId() == ((Employee) cacheWrapper1.getObj()).getId());
        Assert.assertTrue(((Employee) wrapper1.getObj()).getDept().getDeptId() == ((Employee) cacheWrapper1.getObj()).getDept().getDeptId());

        Assert.assertTrue(wrapper2.getCreateTime() == cacheWrapper2.getCreateTime());
        Assert.assertTrue(wrapper2.getExpireTime() == cacheWrapper2.getExpireTime());
        Assert.assertTrue(((Employee) wrapper2.getObj()).getId() == ((Employee) cacheWrapper2.getObj()).getId());
        Assert.assertTrue(((Employee) wrapper2.getObj()).getDept().getDeptId() == ((Employee) cacheWrapper2.getObj()).getDept().getDeptId());

        //测试expire
        Thread.sleep(expTime * 1000 + 100);

        cacheWrapper2 = cache.get(cacheKey2);
        Assert.assertNull(cacheWrapper2);

        //测试del
        Long delRet = cache.delete(cacheKey1);
        Assert.assertTrue(delRet == 1L);
        cacheWrapper1 = cache.get(cacheKey1);
        Assert.assertNull(cacheWrapper1);

    }

    @Test
    public void testClear() {
        CacheWrapper cacheWrapper1 = cache.get(cacheKey1);
        Assert.assertNotNull(cacheWrapper1);

        cache.clear();

        cacheWrapper1 = cache.get(cacheKey1);
        Assert.assertNull(cacheWrapper1);
    }

}
