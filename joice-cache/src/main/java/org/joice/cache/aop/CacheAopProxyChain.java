/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.aop;

import java.lang.reflect.Method;

/**
 * 
 * @author HuHui
 * @version $Id: CacheAopProxyChain.java, v 0.1 2017年9月28日 下午2:50:11 HuHui Exp $
 */
public interface CacheAopProxyChain {

    /**
     * 获取参数
     */
    Object[] getArgs();

    Class<?> getTargetClass();

    /**
     * 获取方法
     */
    Method getMethod();

    /**
     * 执行方法
     * @param arguments 参数
     * @return 执行结果
     */
    Object doProxyChain(Object[] arguments) throws Throwable;

}
