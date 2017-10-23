/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.config;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.joice.cache.enums.CacheDiscardPolicyEnum;

/**
 * 缓存配置
 * @author HuHui
 * @version $Id: CacheConfig.java, v 0.1 2017年10月18日 下午3:32:54 HuHui Exp $
 */
public class CacheConfig {

    /**
     * 命名空间,可避免多个应用使用缓存时造成key冲突
     */
    private String  nameSpace;

    /** 缓存条数,用于MapCache */
    private int     maxCacheNums       = 3000;

    /**
     * 当缓存数据量到达maxCacheNums时采取的缓存丢弃策略,默认为FIFO
     */
    private String  discardPolicy      = CacheDiscardPolicyEnum.FIFO.name();

    /** 缓存存储路径 */
    private String  persistecePath     = File.separatorChar + "tmp" + File.separatorChar + "joice-cache";

    /** 是否持久化缓存 */
    private boolean isPersist          = true;

    /** 每隔多长时间持久化缓存,单位:秒 */
    private int     timeBetweenPersist = 5;

    public String getNameSpace() {
        return nameSpace;
    }

    public CacheConfig setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
        return this;
    }

    public int getMaxCacheNums() {
        return maxCacheNums;
    }

    public CacheConfig setMaxCacheNums(int maxCacheNums) {
        if (maxCacheNums > 0) {
            this.maxCacheNums = maxCacheNums;
        }
        return this;
    }

    public String getPersistecePath() {
        return persistecePath;
    }

    public CacheConfig setPersistecePath(String persistecePath) {
        if (StringUtils.isNotBlank(persistecePath)) {
            this.persistecePath = persistecePath;
        }
        return this;
    }

    public boolean isPersist() {
        return isPersist;
    }

    public CacheConfig setPersist(boolean isPersist) {
        this.isPersist = isPersist;
        return this;
    }

    public int getTimeBetweenPersist() {
        return timeBetweenPersist;
    }

    public CacheConfig setTimeBetweenPersist(int timeBetweenPersist) {
        this.timeBetweenPersist = timeBetweenPersist;
        return this;
    }

    public String getDiscardPolicy() {
        return discardPolicy;
    }

    public void setDiscardPolicy(String discardPolicy) {
        this.discardPolicy = discardPolicy;
    }

}
