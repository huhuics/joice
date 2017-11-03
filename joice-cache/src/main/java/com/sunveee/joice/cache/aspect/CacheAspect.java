package com.sunveee.joice.cache.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;

import com.sunveee.joice.cache.annotation.Cacheable;
import com.sunveee.joice.cache.handler.CacheHandler;
import com.sunveee.joice.cache.util.TargetDetailUtil;

/**
 * 缓存切面
 * 
 * @author 51
 * @version $Id: CacheAspect.java, v 0.1 2017年11月3日 下午2:28:24 51 Exp $
 */
public class CacheAspect {

    private CacheHandler handler;

    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //判断是否存在@Cache注解
        Method method = TargetDetailUtil.getMethod(pjp);
        boolean cacheAnnoPresent = method.isAnnotationPresent(Cacheable.class);

        if (cacheAnnoPresent) {
            Cacheable cacheable = method.getAnnotation(Cacheable.class);
            return handler.handleCacheable(cacheable, pjp);
        }

        return pjp.proceed();
    }
}
