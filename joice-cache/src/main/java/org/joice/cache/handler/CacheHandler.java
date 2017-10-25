/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.handler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.joice.cache.Cache;
import org.joice.cache.annotation.Cacheable;
import org.joice.cache.to.CacheKey;
import org.joice.cache.to.CacheWrapper;
import org.joice.common.util.AssertUtil;
import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存处理类
 * @author HuHui
 * @version $Id: CacheHandler.java, v 0.1 2017年10月25日 下午8:39:00 HuHui Exp $
 */
public class CacheHandler {

    private static final Logger logger = LoggerFactory.getLogger(CacheHandler.class);

    private final Cache         cache;

    private final String        nameSpace;

    public CacheHandler(Cache cache) {
        this.cache = cache;
        nameSpace = cache.getConfig().getNameSpace();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object handleCacheable(Cacheable cacheable, ProceedingJoinPoint jp) throws Throwable {
        LogUtil.info(logger, "进入CacheHandler");

        Object proceedRet = null;

        //获取注解内参数
        String key = cacheable.key();
        int expireTime = cacheable.expireTime();
        boolean sync = cacheable.sync();
        AssertUtil.assertTrue(expireTime >= 0, "超时时间不能为负");

        MethodSignature signature = (MethodSignature) jp.getSignature();
        Class returnType = signature.getReturnType();

        //组装CacheKey
        CacheKey cacheKey = new CacheKey(nameSpace, key);

        //查询缓存
        CacheWrapper cacheWrapper = cache.get(cacheKey);

        if (cacheWrapper != null) { //缓存中有
            Object obj = cacheWrapper.getObj();
            if (returnType.isAssignableFrom(obj.getClass())) {
                return obj;
            }
        }

        //缓存中没有或者缓存的对象与实际返回值不一样
        proceedRet = jp.proceed();

        //放入缓存
        CacheWrapper newWrapper = new CacheWrapper(proceedRet, expireTime);
        cache.set(cacheKey, newWrapper);

        return proceedRet;
    }
}
