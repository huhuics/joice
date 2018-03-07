/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.joice.cache.annotation.Cacheable;
import org.joice.cache.handler.Handler;

/**
 * 拦截{@link Cacheable}注解
 * @author HuHui
 * @version $Id: CacheAspect.java, v 0.1 2017年10月24日 下午7:56:48 HuHui Exp $
 */
public class CacheableAspect {

    private Handler handler;

    public CacheableAspect(Handler handler) {
        this.handler = handler;
    }

    public Object around(ProceedingJoinPoint jp) throws Throwable {
        return handler.handleCacheable(jp);
    }
}
