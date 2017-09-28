/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache;

import java.lang.reflect.Method;

import org.joice.cache.exception.CacheCenterConnectionException;
import org.joice.cache.to.CacheKeyTO;
import org.joice.cache.to.CacheWrapper;

/**
 * 缓存管理接口
 * @author HuHui
 * @version $Id: CacheManager.java, v 0.1 2017年9月28日 下午3:35:59 HuHui Exp $
 */
public interface CacheManager {

    /**
     * 往缓存写数据
     * @param cacheKey  缓存Key
     * @param result    缓存数据
     * @param method    Method
     * @param args      args
     * @throws CacheCenterConnectionException
     */
    void setCache(final CacheKeyTO cacheKey, final CacheWrapper<Object> result, final Method method, final Object args[]) throws CacheCenterConnectionException;

    /**
     * 根据缓存key获取缓存中的数据
     * @param key       缓存key
     * @param method    Method
     * @param args      args
     * @return
     * @throws CacheCenterConnectionException
     */
    CacheWrapper<Object> get(final CacheKeyTO key, final Method method, final Object args[]) throws CacheCenterConnectionException;

    /**
     * 删除缓存
     * @param key  缓存key
     * @throws CacheCenterConnectionException
     */
    void delete(final CacheKeyTO key) throws CacheCenterConnectionException;

}
