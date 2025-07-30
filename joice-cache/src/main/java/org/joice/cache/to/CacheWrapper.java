/**
 * 
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.to;

/**
 * 对缓存对象的封装类
 * @author HuHui
 * @version $Id: CacheWrapper.java, v 0.1 2017年10月18日 下午5:06:25 HuHui Exp $
 */
public class CacheWrapper extends BaseTO {

    /**  */
    private static final long serialVersionUID = -4627300244753708729L;

    /** 被缓存的内容 */
    private Object            obj;

    /** 过期时间,单位:秒.0表示不过期 */
    private int               expireTime       = 0;

    /** 创建时间 */
    private long              createTime;

    /** 最后一次访问该缓存时间 */
    private long              lastAccessTime;

    public CacheWrapper(Object obj) {
        this(obj, 0);
    }

    public CacheWrapper(Object obj, int expireTime) {
        this.obj = obj;
        this.expireTime = expireTime;
        this.createTime = System.currentTimeMillis();
        this.lastAccessTime = System.currentTimeMillis();
    }

    /**
     * 判断该缓存是否已过期
     */
    public boolean isExpire() {
        if (expireTime > 0) {
            return createTime + expireTime * 1000 < System.currentTimeMillis();
        }
        return false;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

}
