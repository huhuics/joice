/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.type;

/**
 * 缓存操作类型
 * @author HuHui
 * @version $Id: CacheOpType.java, v 0.1 2017年10月13日 下午4:21:13 HuHui Exp $
 */
public enum CacheOpType {

    /**
     * 读写缓存操作:如果缓存中有数据,则使用缓存中的数据;如果缓存中没有数据,则加载数据,并写入缓存
     */
    READ_WRITE,

    /**
     * 从数据源中加载最新的数据并写入缓存
     */
    WRITE,

    /**
     * 只从缓存中读取,只读场景
     */
    READ_ONLY,

    /**
     * 只从数据源加载数据,不读取也不写入缓存
     */
    LOAD

    ;

}
