/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.discard;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.joice.cache.map.MapCache;
import org.joice.cache.to.CacheKey;
import org.joice.cache.to.CacheWrapper;

/**
 * FIFO缓存丢弃策略
 * @author HuHui
 * @version $Id: FifoCacheDiscard.java, v 0.1 2017年10月23日 下午9:12:27 HuHui Exp $
 */
public class FifoCacheDiscard implements CacheDiscard {

    private final MapCache mapCache;

    public FifoCacheDiscard(MapCache mapCache) {
        this.mapCache = mapCache;
    }

    @Override
    public void discard() {
        ConcurrentHashMap<String, Object> cache = mapCache.getCache();
        Iterator<Entry<String, Object>> iterator = cache.entrySet().iterator();

        //寻找最早被缓存的对象
        String earliestKey = null;
        CacheWrapper earliestWrapper = new CacheWrapper(null);
        while (iterator.hasNext()) {
            Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object obj = entry.getValue();
            if (obj instanceof CacheWrapper) {
                CacheWrapper wrapper = (CacheWrapper) obj;
                if (wrapper.getCreateTime() < earliestWrapper.getCreateTime()) {
                    earliestKey = key;
                }
            }
        }

        //构造需要被删除的缓存CacheKey
        CacheKey delKey = new CacheKey(mapCache.getConfig().getNameSpace(), earliestKey);

    }
}
