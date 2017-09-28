/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.to;

import java.io.Serializable;

import lombok.Data;

/**
 * 缓存数据封装类
 * @author HuHui
 * @version $Id: CacheWrapper.java, v 0.1 2017年9月28日 下午4:00:29 HuHui Exp $
 */
@Data
public class CacheWrapper<T> implements Serializable, Cloneable {

    /**  */
    private static final long serialVersionUID = 2441147681235299668L;

    /** 缓存数据 */
    private T                 cacheObject;

    /** 最后加载时间 */
    private long              lastLoadTime;

    /** 缓存时长 */
    private int               expire;

    public CacheWrapper() {
    }

    public CacheWrapper(T cacheObject, int expire) {
        this.cacheObject = cacheObject;
        this.expire = expire;
        this.lastLoadTime = System.currentTimeMillis();
    }

    /**
     * 判断缓存是否已经过期
     */
    public boolean isExpired() {
        if (expire > 0) {
            return (System.currentTimeMillis() - lastLoadTime) > expire * 1000;
        }
        return false;
    }

}
