/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.to;

import org.apache.commons.lang3.StringUtils;

/**
 * 表示缓存的Key
 * @author HuHui
 * @version $Id: CacheKey.java, v 0.1 2017年10月18日 下午3:49:32 HuHui Exp $
 */
public class CacheKey extends BaseTO {

    /**  */
    private static final long serialVersionUID = 985800908443850484L;

    /** 命名空间 */
    private String            nameSpace;

    private String            key;

    public CacheKey(String key) {
        this(null, key);
    }

    public CacheKey(String nameSpace, String key) {
        this.nameSpace = nameSpace;
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(nameSpace)) {
            sb.append(nameSpace).append(".");
        }
        sb.append(key);
        this.key = sb.toString();
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
