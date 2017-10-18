/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache;

import org.joice.cache.to.CacheKey;
import org.joice.cache.to.CacheWrapper;

/**
 * 缓存类接口
 * @author HuHui
 * @version $Id: Cache.java, v 0.1 2017年10月18日 下午8:20:12 HuHui Exp $
 */
public interface Cache {

    /**
     * 设置缓存
     * @param key      缓存的key 
     * @param wrapper  缓存的对象
     */
    void set(CacheKey key, CacheWrapper wrapper);

    /**
     * 查询缓存
     * @param key  缓存的key
     * @return     缓存的对象
     */
    CacheWrapper get(CacheKey key);

    /**
     * 删除缓存
     * @param key  缓存的key
     * @return     缓存变化的数量
     */
    int delete(CacheKey key);

}
