/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.map;

import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.joice.cache.CacheManager;
import org.joice.cache.clone.Cloner;
import org.joice.cache.config.CacheConfig;
import org.joice.cache.exception.CacheCenterConnectionException;
import org.joice.cache.to.CacheKeyTO;
import org.joice.cache.to.CacheWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用ConcurrentHashMap管理缓存
 * @author HuHui
 * @version $Id: MapCacheManager.java, v 0.1 2017年9月28日 下午4:14:08 HuHui Exp $
 */
public class MapCacheManager implements CacheManager {

    private static final Logger                     logger                = LoggerFactory.getLogger(MapCacheManager.class);

    private final ConcurrentHashMap<String, Object> cache                 = new ConcurrentHashMap<String, Object>();

    private final CacheChangeListener               changeListener;

    private final Cloner                            cloner;

    private final CacheConfig                       config;

    /**
     * 允许不持久化变更数
     * 当缓存变更数量超过此值才做持久化操作
     */
    private int                                     unPersistMaxSize      = 0;

    private Thread                                  thread                = null;

    private CacheTask                               cacheTask             = null;

    /** 缓存持久化文件 */
    private String                                  persistFile;

    /**
     * 是否持久化
     * true:允许持久化; false:不允许持久化
     */
    private boolean                                 needPersist           = true;

    /**
     * 从缓存中取数据时是否克隆.克隆缓存值可以避免外界修改缓存值
     * true:克隆; false:不克隆
     */
    private boolean                                 copyValueOnGet        = false;

    /**
     * 往缓存写数据时是否把克隆后的值放入缓存
     * true:放入克隆后的值; false:不放入
     */
    private boolean                                 copyValueOnSet        = false;

    /** 清除和持久化的时间间隔,默认1分钟 */
    private int                                     clearAndPersistPeriod = 60 * 1000;

    public MapCacheManager(CacheConfig config, Cloner cloner) {
        this.cloner = cloner;
        this.config = config;
        cacheTask = new CacheTask();//TODO
        changeListener = cacheTask;
    }

    public synchronized void start() {
        if (null == thread) {
            thread = new Thread(cacheTask);
            cacheTask.start();
            thread.start();
        }
    }

    public synchronized void destory() {
        cacheTask.destroy();
        if (thread != null) {
            thread.interrupt();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setCache(CacheKeyTO cacheKeyTO, CacheWrapper<Object> result, Method method, Object[] args) throws CacheCenterConnectionException {
        String cacheKey = null;
        if (cacheKeyTO == null || StringUtils.isEmpty(cacheKey = cacheKeyTO.getCacheKey())) {
            return;
        }
        CacheWrapper<Object> value = null;
        if (copyValueOnSet) {
            try {
                value = (CacheWrapper<Object>) cloner.deepClone(result, null);
            } catch (Exception e) {
                logger.error("", e);
            }
        } else {
            value = result;
        }
        //构造软引用
        SoftReference<CacheWrapper<Object>> reference = new SoftReference<CacheWrapper<Object>>(value);
        String hfield = cacheKeyTO.getHfield();
        if (StringUtils.isBlank(hfield)) {
            cache.put(cacheKey, reference);
        } else {
            Object tmpObj = cache.get(cacheKey);
            ConcurrentHashMap<String, SoftReference<CacheWrapper<Object>>> hash;
            if (tmpObj == null) {
                hash = new ConcurrentHashMap<String, SoftReference<CacheWrapper<Object>>>();
                ConcurrentHashMap<String, SoftReference<CacheWrapper<Object>>> _hash = null;//_hash的作用是防止并发情况下,hash为null
                _hash = (ConcurrentHashMap<String, SoftReference<CacheWrapper<Object>>>) cache.putIfAbsent(cacheKey, hash);
                if (_hash != null) {
                    hash = _hash;
                }
            } else {
                if (tmpObj instanceof ConcurrentHashMap) {
                    hash = (ConcurrentHashMap<String, SoftReference<CacheWrapper<Object>>>) tmpObj;
                } else {
                    logger.error(method.getClass().getName() + "." + method.getName() + "中key为" + cacheKey + "的缓存,已经被占用,请删除缓存再试 ");
                    return;
                }
            }
            hash.put(hfield, reference);
        }
        changeListener.cacheChange();
    }

    @Override
    public CacheWrapper<Object> get(CacheKeyTO key, Method method, Object[] args) throws CacheCenterConnectionException {
        return null;
    }

    @Override
    public void delete(CacheKeyTO key) throws CacheCenterConnectionException {
    }

    public int getUnPersistMaxSize() {
        return unPersistMaxSize;
    }

    public void setUnPersistMaxSize(int unPersistMaxSize) {
        if (unPersistMaxSize > 0) {
            this.unPersistMaxSize = unPersistMaxSize;
        }
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public CacheTask getCacheTask() {
        return cacheTask;
    }

    public void setCacheTask(CacheTask cacheTask) {
        this.cacheTask = cacheTask;
    }

    public String getPersistFile() {
        return persistFile;
    }

    public void setPersistFile(String persistFile) {
        this.persistFile = persistFile;
    }

    public boolean isNeedPersist() {
        return needPersist;
    }

    public void setNeedPersist(boolean needPersist) {
        this.needPersist = needPersist;
    }

    public boolean isCopyValueOnGet() {
        return copyValueOnGet;
    }

    public void setCopyValueOnGet(boolean copyValueOnGet) {
        this.copyValueOnGet = copyValueOnGet;
    }

    public boolean isCopyValueOnSet() {
        return copyValueOnSet;
    }

    public void setCopyValueOnSet(boolean copyValueOnSet) {
        this.copyValueOnSet = copyValueOnSet;
    }

    public int getClearAndPersistPeriod() {
        return clearAndPersistPeriod;
    }

    public void setClearAndPersistPeriod(int clearAndPersistPeriod) {
        this.clearAndPersistPeriod = clearAndPersistPeriod;
    }

    public ConcurrentHashMap<String, Object> getCache() {
        return cache;
    }

    public CacheChangeListener getChangeListener() {
        return changeListener;
    }

    public Cloner getCloner() {
        return cloner;
    }

    public CacheConfig getConfig() {
        return config;
    }

}
