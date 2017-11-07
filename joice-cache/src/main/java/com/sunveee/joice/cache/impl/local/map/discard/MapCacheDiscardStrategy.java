package com.sunveee.joice.cache.impl.local.map.discard;

import java.util.concurrent.ConcurrentHashMap;

import com.sunveee.joice.cache.model.Value;

/**
 * Map缓存丢弃策略接口
 * 
 * @author 51
 * @version $Id: MapCacheDiscardStrategy.java, v 0.1 2017年11月7日 下午4:09:38 51 Exp $
 */
public interface MapCacheDiscardStrategy {
    /**
     * 丢弃缓存
     * 
     * @param map
     */
    public void discard(ConcurrentHashMap<String, Value> cache);

}
