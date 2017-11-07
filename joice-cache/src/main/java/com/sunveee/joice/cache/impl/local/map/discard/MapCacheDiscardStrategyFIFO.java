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
 * 先进先出缓存丢弃策略(FIFO, First In First Out)
 * 
 * @author 51
 * @version $Id: MapCacheDiscardStrategyFIFO.java, v 0.1 2017年11月7日 下午4:36:35 51 Exp $
 */
public class MapCacheDiscardStrategyFIFO implements MapCacheDiscardStrategy {

    private static final Logger logger = LoggerFactory.getLogger(MapCacheDiscardStrategyFIFO.class);

    @Override
    public void discard(ConcurrentHashMap<String, Value> cache) {
        if (null == cache || MapUtils.isEmpty(cache)) {
            return;
        }
        Iterator<Entry<String, Value>> iterator = cache.entrySet().iterator();

        Entry<String, Value> earliest = iterator.next();
        while (iterator.hasNext()) {
            Entry<String, Value> temp = iterator.next();
            if (temp.getValue().getCreateTime() < earliest.getValue().getCreateTime()) {
                earliest = temp;
            }
        }

        cache.remove(earliest.getKey());
        LogUtil.info(logger, "FIFO被删除对象key.keyStr={0}", earliest.getKey());
    }

}
