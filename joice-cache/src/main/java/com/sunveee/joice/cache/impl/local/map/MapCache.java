package com.sunveee.joice.cache.impl.local.map;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunveee.joice.cache.Cache;
import com.sunveee.joice.cache.config.CacheConfig;
import com.sunveee.joice.cache.impl.local.map.discard.MapCacheDiscardStrategy;
import com.sunveee.joice.cache.impl.local.map.discard.MapCacheDiscardStrategyFactory;
import com.sunveee.joice.cache.model.Key;
import com.sunveee.joice.cache.model.Value;

/**
 * 本地缓存实现类<br>
 * 以ConcurrentHashMap的方式实现本地缓存
 * 
 * @author 51
 * @version $Id: MapCache.java, v 0.1 2017年10月30日 下午5:11:33 51 Exp $
 */
public class MapCache implements Cache {

    private static final Logger              logger                         = LoggerFactory.getLogger(MapCache.class);

    private final CacheConfig                config;

    private ConcurrentHashMap<String, Value> cache;

    private MapCacheDiscardStrategyFactory   mapCacheDiscardStrategyFactory = MapCacheDiscardStrategyFactory.getInstance();

    private final MapCacheManager            mapCacheManager;

    private Thread                           daemonThread;

    public MapCache(CacheConfig config) {
        this.config = config;
        cache = new ConcurrentHashMap<>(config.getMaxCacheNums());
        mapCacheManager = new MapCacheManager(this);

        // 读取磁盘缓存
        mapCacheManager.restore();

        // 启动守护线程
        startDaemon();
    }

    @Override
    public void set(Key key, Value value) {
        String keyStr;
        if (null == key || null == value || StringUtils.isBlank(keyStr = key.getKeyStr())) {
            return;
        }
        cache.put(keyStr, value);
        LogUtil.info(logger, "MapCache写缓存成功: keyStr={0},value={1}", keyStr, value);

        if (cache.size() > config.getMaxCacheNums()) {
            this.discard();
        }

    }

    @Override
    public Value get(Key key) {
        LogUtil.info(logger, "MapCache读缓存: key={0}", key);
        String keyStr;
        if (null == key || StringUtils.isBlank(keyStr = key.getKeyStr())) {
            return null;
        }
        Value result = null;
        try {
            result = cache.get(keyStr);
        } catch (Exception e) {
            LogUtil.error(e, logger, "MapCache读缓存发生异常,key={0}", key);
        }
        if (null == result) {
            return null;
        }
        result.setLastAccessTime(System.currentTimeMillis());
        if (result.isExpire()) {
            cache.remove(keyStr);
            return null;
        }
        LogUtil.info(logger, "MapCache读缓存成功: key={0},result={1}", key, result);
        return result;
    }

    @Override
    public Boolean delete(Key key) {
        LogUtil.info(logger, "MapCache删除缓存: key={0}", key);
        String keyStr;
        if (null == key || StringUtils.isBlank(keyStr = key.getKeyStr())) {
            return null;
        }
        return null != cache.remove(keyStr);
    }

    @Override
    public void deleteAll() {
        LogUtil.info(logger, "MapCache清空缓存");
        cache.clear();
    }

    @Override
    public void discard() {
        MapCacheDiscardStrategy mapCacheDiscardStrategy = mapCacheDiscardStrategyFactory.getCacheDiscard(config.getCacheDiscardStrategyEnum());
        if (null == mapCacheDiscardStrategy) {
            LogUtil.error(logger, "配置了尚不支持的MapCache缓存丢弃策略:{0}", config.getCacheDiscardStrategyEnum());
            interruptDaemon();
            throw new RuntimeException("配置了尚不支持的MapCache缓存丢弃策略");
        }
        mapCacheDiscardStrategy.discard(cache);
    }

    @Override
    public CacheConfig getConfig() {
        return config;
    }

    public ConcurrentHashMap<String, Value> getCache() {
        return cache;
    }

    private synchronized void startDaemon() {
        if (daemonThread == null) {
            daemonThread = new Thread(mapCacheManager, "map-cache-daemon");
            daemonThread.setDaemon(true);
            daemonThread.start();
        }
    }

    private synchronized void interruptDaemon() {
        mapCacheManager.setRunnable(false);
        if (daemonThread != null) {
            daemonThread.interrupt();
        }
    }

}
