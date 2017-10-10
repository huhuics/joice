/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.test;

import org.joice.cache.clone.Cloner;
import org.joice.cache.config.CacheConfig;
import org.joice.cache.map.MapCacheManager;
import org.joice.cache.serializer.HessianSerializer;
import org.joice.cache.test.domain.Department;
import org.joice.cache.test.domain.Employee;
import org.joice.cache.to.CacheKeyTO;
import org.joice.cache.to.CacheWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试Map类型的缓存
 * @author HuHui
 * @version $Id: MapCacheTest.java, v 0.1 2017年10月10日 下午4:55:12 HuHui Exp $
 */
public class MapCacheTest {

    private MapCacheManager mapCache;

    @Before
    public void init() {
        CacheConfig config = new CacheConfig();
        Cloner cloner = new HessianSerializer();

        mapCache = new MapCacheManager(config, cloner);
        mapCache.start();
    }

    @Test
    public void testMapCache() throws Exception {

        Assert.assertNotNull(mapCache);

        //组装要缓存的对象
        Department dept = new Department(1, "水泊梁山");
        Employee emp = new Employee(1, "宋江", dept);

        //组装cacheKey
        CacheKeyTO key = new CacheKeyTO(mapCache.getConfig().getNamespace(), emp.getId() + "", null);

        //组装wrapper
        CacheWrapper<Object> wrapper = new CacheWrapper<Object>(emp, 60);

        mapCache.setCache(key, wrapper, null, null);

        CacheWrapper<Object> cacheRet = mapCache.get(key, null, null);

        Assert.assertNotNull(cacheRet);
        Assert.assertNotNull(cacheRet.getCacheObject());
        Employee cacheEmp = (Employee) cacheRet.getCacheObject();
        Assert.assertTrue(emp.getId() == cacheEmp.getId());
    }
}
