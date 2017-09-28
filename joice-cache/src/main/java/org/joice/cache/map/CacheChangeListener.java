/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.map;

/**
 * 缓存变更监听器
 * @author HuHui
 * @version $Id: CacheChangeListener.java, v 0.1 2017年9月28日 下午4:19:08 HuHui Exp $
 */
public interface CacheChangeListener {

    /**
     * 只变更一条记录
     */
    void cacheChange();

    /**
     * 变更多条记录
     * @param cnt 变更数量
     */
    void cacheChange(int cnt);

}
