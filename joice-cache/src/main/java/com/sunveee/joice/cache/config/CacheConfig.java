/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package com.sunveee.joice.cache.config;

import org.apache.commons.lang3.StringUtils;

import com.sunveee.joice.cache.enums.CacheDiscardStrategyEnum;

/**
 * 缓存配置类
 * 
 * @author 51
 * @version $Id: CacheConfig.java, v 0.1 2017年11月7日 上午10:15:46 51 Exp $
 */
public class CacheConfig {

    /** 命名空间,可避免多个应用使用缓存时造成key冲突 */
    private String                   nameSpace;

    /** 缓存条数,用于MapCache */
    private int                      maxCacheNums;

    /** 当缓存数据量到达maxCacheNums时采取的缓存丢弃策略 */
    private CacheDiscardStrategyEnum cacheDiscardStrategyEnum;

    /** 是否持久化缓存 */
    private boolean                  isPersistent        = false;

    /** 缓存存储文件路径 */
    private String                   persistFileFolderPath;

    /** 缓存存储文件名 */
    private String                   persistFilename;

    /** 每隔多长时间清理过期缓存以及持久化缓存,单位:秒 */
    private int                      persistTimeInterval = 2;

    public CacheConfig(String nameSpace, int maxCacheNums, CacheDiscardStrategyEnum cacheDiscardStrategyEnum, String persistFileFolderPath, String persistFilename, int persistTimeInterval) {
        this.nameSpace = nameSpace;
        this.maxCacheNums = maxCacheNums;
        this.cacheDiscardStrategyEnum = cacheDiscardStrategyEnum;
        this.persistFileFolderPath = persistFileFolderPath;
        this.persistFilename = persistFilename;
        if (StringUtils.isNoneBlank(persistFileFolderPath) && StringUtils.isNoneBlank(persistFilename) && persistTimeInterval > 0) {
            this.persistTimeInterval = persistTimeInterval;
            this.isPersistent = true;
        }
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public int getMaxCacheNums() {
        return maxCacheNums;
    }

    public CacheDiscardStrategyEnum getCacheDiscardStrategyEnum() {
        return cacheDiscardStrategyEnum;
    }

    public boolean isPersistent() {
        return isPersistent;
    }

    public String getPersistFileFolderPath() {
        return persistFileFolderPath;
    }

    public String getPersistFilename() {
        return persistFilename;
    }

    public int getPersistTimeInterval() {
        return persistTimeInterval;
    }

}
