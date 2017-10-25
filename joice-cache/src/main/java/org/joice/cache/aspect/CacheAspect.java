/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.joice.cache.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存切面
 * @author HuHui
 * @version $Id: CacheAspect.java, v 0.1 2017年10月24日 下午7:56:48 HuHui Exp $
 */
public class CacheAspect {

    private static final Logger logger = LoggerFactory.getLogger(CacheAspect.class);

    public Object around(ProceedingJoinPoint jp) throws Throwable {
        String className = jp.getTarget().getClass().getName();
        String method = jp.getSignature().getName();

        LogUtil.info(logger, "切面className={0},method={1}", className, method);

        return jp.proceed();
    }

}
