/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.test;

import org.joice.cache.config.CacheConfig;
import org.joice.cache.enums.CacheDiscardPolicyEnum;
import org.joice.cache.map.MapCache;
import org.joice.cache.test.domain.Employee;
import org.joice.cache.to.CacheKey;
import org.joice.cache.to.CacheWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author HuHui
 * @version $Id: CacheDiscardTest.java, v 0.1 2017年10月24日 上午9:19:53 HuHui Exp $
 */
public class CacheDiscardTest {

    private CacheConfig config = new CacheConfig();

    private MapCache    cache;

    private int         maxNum = 10;

    @Before
    public void init() {
        config.setMaxCacheNums(maxNum);
        config.setPersist(false);
        config.setTimeBetweenPersist(60);
    }

    @Test
    public void testFifoDiscard() throws Exception {
        cache = new MapCache(config);
        cache.clear();

        //创建缓存对象
        for (int i = 0; i < maxNum; i++) {
            Employee emp = new Employee(i, "name" + i, null);
            CacheKey key = new CacheKey(emp.getId() + "");
            CacheWrapper wrapper = new CacheWrapper(emp);
            cache.set(key, wrapper);
            Thread.sleep(50);
        }

        Thread.sleep(50);

        //再创建
        for (int i = maxNum; i < maxNum + 5; i++) {
            Employee emp = new Employee(i, "name" + i, null);
            CacheKey key = new CacheKey(emp.getId() + "");
            CacheWrapper wrapper = new CacheWrapper(emp);
            cache.set(key, wrapper);
            Thread.sleep(50);
        }

        Assert.assertTrue(cache.getCache().size() == maxNum);
    }

    @Test
    public void testLruDiscard() throws Exception {
        config.setDiscardPolicy(CacheDiscardPolicyEnum.LRU.name());
        cache = new MapCache(config);
        cache.clear();

        //创建缓存对象
        for (int i = 0; i < maxNum; i++) {
            Employee emp = new Employee(i, "name" + i, null);
            CacheKey key = new CacheKey(emp.getId() + "");
            CacheWrapper wrapper = new CacheWrapper(emp);
            cache.set(key, wrapper);
            Thread.sleep(10);
        }

        //依次访问前5个缓存对象
        for (int i = 0; i < 5; i++) {
            CacheKey key = new CacheKey(i + "");
            cache.get(key);
            Thread.sleep(10);
        }

        //再创建
        for (int i = maxNum; i < maxNum + 5; i++) {
            Employee emp = new Employee(i, "name" + i, null);
            CacheKey key = new CacheKey(emp.getId() + "");
            CacheWrapper wrapper = new CacheWrapper(emp);
            cache.set(key, wrapper);
            Thread.sleep(50);
        }

        Assert.assertTrue(cache.getCache().size() == maxNum);
    }

}
