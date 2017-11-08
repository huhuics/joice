package com.sunveee.joice.cache;

/**
 * 缓存管理线程接口
 * 
 * @author 51
 * @version $Id: CacheManager.java, v 0.1 2017年11月7日 下午3:20:46 51 Exp $
 */
public interface CacheManager extends Runnable {

    /**
     * 清理过期缓存
     * 
     */
    void cleanUpExpired();

    /**
     * 持久化缓存
     * 
     */
    void persist();

    /**
     * 根据持久化数据恢复缓存
     * 
     */
    void restore();

}
