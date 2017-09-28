/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.to;

import java.io.Serializable;

import org.joice.cache.aop.CacheAopProxyChain;

/**
 * 用于处理自动加载数据到缓存
 * @author HuHui
 * @version $Id: AutoLoadTO.java, v 0.1 2017年9月28日 下午2:49:05 HuHui Exp $
 */
public class AutoLoadTO implements Serializable {

    /**  */
    private static final long  serialVersionUID = 8250081176062856531L;

    private CacheAopProxyChain joinPoint;

    private Object             args[];

}
