package com.sunveee.joice.cache.impl.local.map.discard;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.MapUtils;
import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunveee.joice.cache.model.Value;

/**
 * 最近最少使用缓存丢弃策略(LRU,Least Recently Used)
 * 
 * @author 51
 * @version $Id: MapCacheDiscardStrategyLRU.java, v 0.1 2017年11月7日 下午4:35:37 51 Exp $
 */
public class MapCacheDiscardStrategyLRU implements MapCacheDiscardStrategy {

    private static final Logger logger = LoggerFactory.getLogger(MapCacheDiscardStrategyLRU.class);

    @Override
    public void discard(ConcurrentHashMap<String, Value> cache) {
        if (null == cache || MapUtils.isEmpty(cache)) {
            return;
        }
        Iterator<Entry<String, Value>> iterator = cache.entrySet().iterator();

        Entry<String, Value> earliestAccess = iterator.next();
        while (iterator.hasNext()) {
            Entry<String, Value> temp = iterator.next();
            if (temp.getValue().getLastAccessTime() < earliestAccess.getValue().getLastAccessTime()) {
                earliestAccess = temp;
            }
        }

        cache.remove(earliestAccess.getKey());
        LogUtil.info(logger, "FIFO被删除对象key.keyStr={0}", earliestAccess.getKey());
    }

}
