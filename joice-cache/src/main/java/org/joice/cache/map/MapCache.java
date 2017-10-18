/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.map;

import java.util.concurrent.ConcurrentHashMap;

import org.joice.cache.Cache;
import org.joice.cache.config.CacheConfig;
import org.joice.cache.serializer.Serializer;
import org.joice.cache.to.CacheKey;
import org.joice.cache.to.CacheWrapper;
import org.joice.cache.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用Map实现本地缓存
 * @author HuHui
 * @version $Id: MapCache.java, v 0.1 2017年10月18日 下午8:33:26 HuHui Exp $
 */
public class MapCache implements Cache {

    private static final Logger                             logger = LoggerFactory.getLogger(MapCache.class);

    /** 序列化类 */
    private final Serializer<CacheWrapper>                  serializer;

    /** 缓存配置类 */
    private final CacheConfig                               config;

    private final ConcurrentHashMap<CacheKey, CacheWrapper> cache;

    public MapCache(Serializer<CacheWrapper> serializer, CacheConfig config) {
        LogUtil.info(logger, "Map Cache initing...");
        this.serializer = serializer;
        this.config = config;
        cache = new ConcurrentHashMap<CacheKey, CacheWrapper>(config.getCacheNums());
        LogUtil.info(logger, "Map Cache init success!");
    }

    @Override
    public void set(CacheKey key, CacheWrapper wrapper) {
    }

    @Override
    public CacheWrapper get(CacheKey key) {
        return null;
    }

    @Override
    public int delete(CacheKey key) {
        return 0;
    }

}
