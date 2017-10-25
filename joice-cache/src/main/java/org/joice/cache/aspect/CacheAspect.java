/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.joice.cache.annotation.Cacheable;
import org.joice.cache.handler.CacheHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cache缓存切面,拦截@Cache注解的方法
 * @author HuHui
 * @version $Id: CacheAspect.java, v 0.1 2017年10月24日 下午7:56:48 HuHui Exp $
 */
public class CacheAspect {

    private static final Logger logger = LoggerFactory.getLogger(CacheAspect.class);

    private CacheHandler        handler;

    public CacheAspect(CacheHandler handler) {
        this.handler = handler;
    }

    public Object around(ProceedingJoinPoint jp) throws Throwable {

        //判断是否存在@Cache注解
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        boolean cacheAnnoPresent = method.isAnnotationPresent(Cacheable.class);

        if (cacheAnnoPresent) {
            Cacheable cacheable = method.getAnnotation(Cacheable.class);
            return handler.handleCacheable(cacheable, jp);
        }

        return jp.proceed();
    }
}
