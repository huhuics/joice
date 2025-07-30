/**
 * 
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.joice.cache.annotation.CacheDel;
import org.joice.cache.handler.Handler;

/**
 * 拦截{@link CacheDel}注解
 * @author HuHui
 * @version $Id: CacheDelAspect.java, v 0.1 2017年10月27日 上午11:07:58 HuHui Exp $
 */
public class CacheDelAspect {

    private Handler handler;

    public CacheDelAspect(Handler handler) {
        this.handler = handler;
    }

    public Object around(ProceedingJoinPoint jp) throws Throwable {
        return handler.handleCacheDel(jp);
    }

}
