package com.sunveee.joice.cache.model;

/**
 * 缓存Value
 * 
 * @author 51
 * @version $Id: Value.java, v 0.1 2017年10月30日 下午2:39:03 51 Exp $
 */
public class Value extends BaseModel {
    /**  */
    private static final long serialVersionUID = -1523692869938241263L;

    /** 被缓存的内容 */
    private Object            obj;

    /** 过期时间,单位:秒.0表示不过期 */
    private int               expire;

    /** 创建时间 */
    private long              createTime;

    /** 最后一次访问该缓存时间 */
    private long              lastAccessTime;

    public Value(Object obj) {
        this(obj, 0);
    }

    public Value(Object obj, int expire) {
        this.obj = obj;
        this.expire = expire;
        this.createTime = System.currentTimeMillis();
        this.lastAccessTime = System.currentTimeMillis();
    }

    /**
     * 判断该缓存是否已过期
     */
    public boolean isExpire() {
        if (expire > 0) {
            return createTime + expire * 1000 < System.currentTimeMillis();
        }
        return false;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
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
