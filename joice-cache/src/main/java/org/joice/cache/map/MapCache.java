/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.map;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.joice.cache.Cache;
import org.joice.cache.config.CacheConfig;
import org.joice.cache.serializer.Serializer;
import org.joice.cache.serializer.StringSerializer;
import org.joice.cache.to.CacheKey;
import org.joice.cache.to.CacheWrapper;
import org.joice.cache.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用Map实现本地缓存
 * 默认使用{@link StringSerializer}对缓存的key进行序列化
 * @author HuHui
 * @version $Id: MapCache.java, v 0.1 2017年10月18日 下午8:33:26 HuHui Exp $
 */
public class MapCache implements Cache {

    private static final Logger                     logger = LoggerFactory.getLogger(MapCache.class);

    /** 序列化类 */
    private final Serializer<String>                serializer;

    /** 缓存配置类 */
    private final CacheConfig                       config;

    private final ConcurrentHashMap<String, Object> cache;

    public MapCache(CacheConfig config) {
        this(new StringSerializer(), config);
    }

    public MapCache(Serializer<String> serializer, CacheConfig config) {
        LogUtil.info(logger, "Map Cache initing...");
        this.serializer = serializer;
        this.config = config;
        cache = new ConcurrentHashMap<String, Object>(config.getCacheNums());
        LogUtil.info(logger, "Map Cache init success!");
    }

    @Override
    public void set(CacheKey cacheKey, CacheWrapper wrapper) {
        String key;
        if (cacheKey == null || StringUtils.isEmpty(key = cacheKey.getKey()) || wrapper == null) {
            return;
        }
        String hfield = cacheKey.getHfield();
        SoftReference<CacheWrapper> ref = new SoftReference<CacheWrapper>(wrapper);
        if (StringUtils.isBlank(hfield)) {
            cache.put(key, ref);
        } else { //缓存hash结构的数据
            ConcurrentHashMap<String, CacheWrapper> map = new ConcurrentHashMap<String, CacheWrapper>();
            map.put(hfield, wrapper);
            SoftReference<ConcurrentHashMap<String, CacheWrapper>> hashMapRef = new SoftReference<ConcurrentHashMap<String, CacheWrapper>>(map);
            cache.put(key, hashMapRef);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public CacheWrapper get(CacheKey cacheKey) {
        String key;
        if (cacheKey == null || StringUtils.isEmpty(key = cacheKey.getKey())) {
            return null;
        }

        CacheWrapper wrapper = null;
        Object value = cache.get(key);
        if (value != null) {
            try {
                String hfield;
                if (StringUtils.isBlank(hfield = cacheKey.getHfield())) {
                    SoftReference<CacheWrapper> ref = (SoftReference<CacheWrapper>) value;
                    wrapper = ref.get();
                } else { //hash结构数据
                    SoftReference<ConcurrentHashMap<String, CacheWrapper>> hashMapRef = (SoftReference<ConcurrentHashMap<String, CacheWrapper>>) value;
                    ConcurrentHashMap<String, CacheWrapper> map = hashMapRef.get();
                    wrapper = map.get(hfield);
                }
            } catch (Exception e) {
                LogUtil.error(e, logger, "获取缓存异常,cacheKey={0}", cacheKey);
                return null;
            }
        }

        if (wrapper != null && wrapper.isExpire()) {
            return null;
        }

        return wrapper;
    }

    @Override
    public int delete(CacheKey cacheKey) {
        return 0;
    }

}
