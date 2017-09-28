/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.to;

import java.io.Serializable;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

/**
 * 缓存Key
 * @author HuHui
 * @version $Id: CacheKeyTO.java, v 0.1 2017年9月28日 下午3:39:43 HuHui Exp $
 */
@Data
public final class CacheKeyTO implements Serializable {

    /**  */
    private static final long serialVersionUID = -41353561425804897L;

    private final String      namespace;

    /** 缓存key */
    private final String      key;

    /** 设置哈希表中的字段,如果设置此项,则用哈希表进行存储 */
    private final String      hfield;

    public CacheKeyTO(String namespace, String key, String hfield) {
        this.namespace = namespace;
        this.key = key;
        this.hfield = hfield;
    }

    public String getCacheKey() {
        if (StringUtils.isNotBlank(namespace)) {
            return new StringBuilder(namespace).append(":").append(key).toString();
        }
        return key;
    }

    public String getLockKey() {
        StringBuilder key = new StringBuilder(getCacheKey());
        if (StringUtils.isNotBlank(hfield)) {
            key.append(":").append(hfield);
        }
        key.append(":lock");
        return key.toString();
    }

}
