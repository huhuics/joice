/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.discard;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.MapUtils;
import org.joice.cache.map.MapCache;
import org.joice.cache.to.CacheKey;
import org.joice.cache.to.CacheWrapper;

/**
 * 最近最少使用丢弃策略
 * @author HuHui
 * @version $Id: LruCacheDiscard.java, v 0.1 2017年10月23日 下午11:22:35 HuHui Exp $
 */
public class LruCacheDiscard implements CacheDiscard {

    @Override
    public void discard(MapCache mapCache) {

        if (mapCache == null || MapUtils.isEmpty(mapCache.getCache())) {
            return;
        }

        ConcurrentHashMap<String, Object> cache = mapCache.getCache();
        Iterator<Entry<String, Object>> iterator = cache.entrySet().iterator();

        //寻找lastAccessTime最小的对象
        String minAcsKey = null;
        CacheWrapper minAcsWrapper = new CacheWrapper(null);
        while (iterator.hasNext()) {
            Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object obj = entry.getValue();
            if (obj instanceof CacheWrapper) {
                CacheWrapper wrapper = (CacheWrapper) obj;
                if (wrapper.getLastAccessTime() < minAcsWrapper.getLastAccessTime()) {
                    minAcsKey = key;
                }
            }
        }

        //构造需要被删除的缓存CacheKey
        CacheKey delKey = new CacheKey(mapCache.getConfig().getNameSpace(), minAcsKey);

        mapCache.delete(delKey);

    }

}
