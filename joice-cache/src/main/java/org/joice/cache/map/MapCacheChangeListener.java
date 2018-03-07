/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.map;

import org.joice.cache.config.CacheConfig;
import org.joice.cache.discard.CacheDiscard;
import org.joice.cache.discard.CacheDiscardFactory;
import org.joice.cache.enums.CacheDiscardPolicyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 监听Map Cache的变化
 * @author HuHui
 * @version $Id: MapCacheChangeListener.java, v 0.1 2017年10月23日 下午5:01:09 HuHui Exp $
 */
public class MapCacheChangeListener {

    private static final Logger          logger  = LoggerFactory.getLogger(MapCacheChangeListener.class);

    private final MapCache               cache;

    private final CacheDiscardPolicyEnum policyEnum;

    private final CacheDiscardFactory    factory = CacheDiscardFactory.getInstance();

    public MapCacheChangeListener(CacheConfig config, MapCache cache) {
        this.cache = cache;
        this.policyEnum = CacheDiscardPolicyEnum.valueOf(config.getDiscardPolicy().toUpperCase());
    }

    /**
     * 根据策略丢弃缓存
     */
    public void discard() {
        CacheDiscard cacheDiscard = factory.getCacheDiscard(policyEnum);
        cacheDiscard.discard(cache);
    }

}
