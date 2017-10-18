/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.config;

/**
 * 缓存配置
 * @author HuHui
 * @version $Id: CacheConfig.java, v 0.1 2017年10月18日 下午3:32:54 HuHui Exp $
 */
public class CacheConfig {

    /**
     * 命名空间,可避免多个应用使用缓存时造成key冲突
     */
    private String nameSpace;

    /** 缓存条数,用于MapCache */
    private int    cacheNums = 3000;

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public int getCacheNums() {
        return cacheNums;
    }

    public void setCacheNums(int cacheNums) {
        if (cacheNums > 0) {
            this.cacheNums = cacheNums;
        }
    }

}
