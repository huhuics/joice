/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.to;

/**
 * 对缓存对象的封装类
 * @author HuHui
 * @version $Id: CacheWrapper.java, v 0.1 2017年10月18日 下午5:06:25 HuHui Exp $
 */
public class CacheWrapper<T> extends BaseTO {

    /**  */
    private static final long serialVersionUID = -4627300244753708729L;

    /** 被缓存的内容 */
    private T                 obj;

    /** 过期时间,单位:秒.0表示不过期 */
    private int               expireTime       = 0;

    /** 最后一次访问该缓存时间 */
    private long              lastAccess;

    public CacheWrapper(T obj) {
        this(obj, 0);
    }

    public CacheWrapper(T obj, int expireTime) {
        this.obj = obj;
        this.expireTime = expireTime;
        this.lastAccess = System.currentTimeMillis();
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public long getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(long lastAccess) {
        this.lastAccess = lastAccess;
    }

}
