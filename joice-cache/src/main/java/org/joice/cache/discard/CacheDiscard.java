/**
 * 
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.discard;

import org.joice.cache.map.MapCache;

/**
 * 缓存丢弃接口
 * @author HuHui
 * @version $Id: CacheDiscard.java, v 0.1 2017年10月23日 下午8:58:09 HuHui Exp $
 */
public interface CacheDiscard {

    /**
     * 丢弃缓存方法
     * @param mapCache
     */
    void discard(MapCache mapCache);

}
