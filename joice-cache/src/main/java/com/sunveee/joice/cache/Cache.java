package com.sunveee.joice.cache;

import com.sunveee.joice.cache.config.CacheConfig;
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

    /**
     * 删除某条缓存
     * 
     * @param key
     * @return <code>true</code> 存在并删除成功<br> 
     * <code>false</code> 不存在匹配的key<br> 
     * <code>null</code> 传入的key不合法<br> 
     */
    Boolean delete(Key key);

    /**
     * 清空所有缓存
     * 
     * @return 清空缓存条数
     */
    void deleteAll();

    /**
     * 缓存满时根据策略丢弃缓存
     */
    void discard();

    /**
     * 获取缓存配置
     * 
     * @return
     */
    CacheConfig getConfig();

}
