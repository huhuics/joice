/**
 * 
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.test;

import org.joice.cache.config.CacheConfig;
import org.joice.cache.map.MapCache;
import org.joice.cache.map.MapCacheDaemon;
import org.joice.cache.test.domain.Department;
import org.joice.cache.test.domain.Employee;
import org.joice.cache.to.CacheKey;
import org.joice.cache.to.CacheWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author HuHui
 * @version $Id: MapCacheDaemonTest.java, v 0.1 2017年10月20日 上午9:50:58 HuHui Exp $
 */
public class MapCacheDaemonTest {

    private MapCacheDaemon daemon;

    private MapCache       mapCache;

    private Employee       emp;

    @Before
    public void init() {
        CacheConfig config = new CacheConfig();
        mapCache = new MapCache(config);
        daemon = new MapCacheDaemon(mapCache, config);
        //组装数据
        Department dept = new Department(11L, "陆军");
        emp = new Employee(1L, "林冲", dept);
    }

    @Test
    public void testPersistCache() {
        CacheKey cacheKey = new CacheKey(emp.getId() + "");
        CacheWrapper wrapper = new CacheWrapper(emp);

        //测试普通set
        mapCache.set(cacheKey, wrapper);
        daemon.persistCache();
    }

    @Test
    public void testReadCache() {
        mapCache.clear();
        Assert.assertTrue(mapCache.getCache().isEmpty());
        daemon.readCacheFromDisk();
        CacheKey cacheKey = new CacheKey(emp.getId() + "");

        CacheWrapper wrapper = mapCache.get(cacheKey);
        Employee cacheEmp = (Employee) wrapper.getObj();

        Assert.assertNotNull(cacheEmp);
        Assert.assertTrue(emp.getId() == cacheEmp.getId());
    }

    @Test
    public void testClearCache() throws InterruptedException {
        int expireTime = 3;
        CacheKey cacheKey = new CacheKey(emp.getId() + "");
        CacheWrapper wrapper = new CacheWrapper(emp, expireTime);

        mapCache.set(cacheKey, wrapper);
        CacheWrapper ret = mapCache.get(cacheKey);
        Assert.assertNotNull(ret);

        Thread.sleep(expireTime * 1000 + 500);

        daemon.clearCache();

        ret = mapCache.get(cacheKey);
        Assert.assertNull(ret);
    }

}
