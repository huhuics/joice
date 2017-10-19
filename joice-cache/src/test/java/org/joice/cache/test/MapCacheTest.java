/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.test;

import org.joice.cache.config.CacheConfig;
import org.joice.cache.map.MapCache;
import org.joice.cache.test.domain.Department;
import org.joice.cache.test.domain.Employee;
import org.joice.cache.to.CacheKey;
import org.joice.cache.to.CacheWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试{@link MapCache}
 * @author HuHui
 * @version $Id: MapCacheTest.java, v 0.1 2017年10月19日 下午6:37:21 HuHui Exp $
 */
public class MapCacheTest {

    private CacheConfig cacheConfig = new CacheConfig();

    private MapCache    cache;

    private Department  dept        = new Department(11L, "陆军");

    private Employee    emp         = new Employee(1L, "林冲", dept);

    @Before
    public void init() {
        cache = new MapCache(cacheConfig);
    }

    @Test
    public void testSetAndGet() throws Exception {
        Assert.assertNotNull(cache);
        CacheKey cacheKey = new CacheKey(emp.getId() + "");
        int expire = 5;

        CacheWrapper wrapper = new CacheWrapper(emp, expire);

        //测试普通set
        cache.set(cacheKey, wrapper);
        CacheWrapper ret = cache.get(cacheKey);
        Assert.assertNotNull(ret);
        Assert.assertTrue(((Employee) (ret.getObj())).getId() == emp.getId());

        //测试超时
        Thread.sleep(expire * 1000 + 200);//休眠时间略大于expire time

        ret = cache.get(cacheKey);
        Assert.assertNull(ret);

    }

    @Test
    public void testHashSetAndGet() {
        Assert.assertNotNull(cache);
        CacheKey cacheKey = new CacheKey(cacheConfig.getNameSpace(), emp.getId() + "", dept.getDeptId() + "");
        CacheWrapper wrapper = new CacheWrapper(emp);

        cache.set(cacheKey, wrapper);

        CacheWrapper ret = cache.get(cacheKey);
        Assert.assertNotNull(ret);
        Assert.assertTrue(((Employee) (ret.getObj())).getId() == emp.getId());
    }

    @Test
    public void testHashSetException() {
        Assert.assertNotNull(cache);
        CacheKey cacheKey = new CacheKey(cacheConfig.getNameSpace(), emp.getId() + "", dept.getDeptId() + "");
        CacheWrapper wrapper = new CacheWrapper(emp);

        cache.set(cacheKey, wrapper);

        cacheKey.setHfield(null);//ClassCastException
        CacheWrapper ret = cache.get(cacheKey);
        Assert.assertNull(ret);
    }

}
