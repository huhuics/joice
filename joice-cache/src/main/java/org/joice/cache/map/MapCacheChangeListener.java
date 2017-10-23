/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.map;

import java.util.concurrent.atomic.AtomicInteger;

import org.joice.cache.config.CacheConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 监听Map Cache的变化
 * @author HuHui
 * @version $Id: MapCacheChangeListener.java, v 0.1 2017年10月23日 下午5:01:09 HuHui Exp $
 */
public class MapCacheChangeListener {

    private static final Logger logger   = LoggerFactory.getLogger(MapCacheChangeListener.class);

    private final MapCache      cache;

    private final int           maxCacheCnt;

    private final String        discardPolicy;

    /** 缓存计数 */
    private final AtomicInteger cacheCnt = new AtomicInteger(0);

    public MapCacheChangeListener(CacheConfig config, MapCache cache) {
        this.cache = cache;
        this.maxCacheCnt = config.getMaxCacheNums();
        this.discardPolicy = config.getDiscardPolicy();
    }

    /**
     * 缓存数量加1
     */
    public void increase() {
        if (cacheCnt.incrementAndGet() > maxCacheCnt) { //超过最大缓存数,则根据策略删除缓存

        }
    }

    /**
     * 缓存数量加n
     */
    public void increase(int n) {
        if (n > 0) {
            for (int i = 0; i < n; i++) {
                increase();
            }
        }
    }

    /**
     * 缓存数量减1
     */
    public void decrease() {
        cacheCnt.decrementAndGet();
    }

    /**
     * 缓存数量清0
     */
    public void clear() {
        cacheCnt.set(0);
    }

}
