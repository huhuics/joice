package com.sunveee.joice.cache;

import com.sunveee.joice.cache.model.Key;
import com.sunveee.joice.cache.model.Value;

/**
 * 缓存接口
 * 
 * @author 51
 * @version $Id: Cache.java, v 0.1 2017年10月30日 上午9:29:42 51 Exp $
 */
public interface Cache {

    /**
     * 设置缓存
     * 
     * @param key
     * @param value
     */
    void set(Key key, Value value);

    /**
     * 获取缓存
     * 
     * @param key
     * @return
     */
    Value get(Key key);

}
