package com.sunveee.joice.cache.impl.local.map.discard;

import com.sunveee.joice.cache.enums.CacheDiscardStrategyEnum;

/**
 * 生成CacheDiscard工厂方法类
 * 使用单例模式生成CacheDiscardFactory
 * 
 * @author HuHui
 * @version $Id: MapCacheDiscardStrategyFactory.java, v 0.1 2017年11月7日 下午4:18:54 51 Exp $
 */
public class MapCacheDiscardStrategyFactory {

    private MapCacheDiscardStrategy fifoDiscard, lruDiscard;

    private MapCacheDiscardStrategyFactory() {
        fifoDiscard = new MapCacheDiscardStrategyFIFO();
        lruDiscard = new MapCacheDiscardStrategyLRU();
    }

    public static MapCacheDiscardStrategyFactory getInstance() {
        return FactoryHolder.instance;
    }

    /**
     * 根据丢弃策略的枚举值获取策略的实现类
     */
    public MapCacheDiscardStrategy getCacheDiscard(CacheDiscardStrategyEnum cdsEnum) {
        if (cdsEnum == CacheDiscardStrategyEnum.FIFO) {
            return fifoDiscard;
        } else if (cdsEnum == CacheDiscardStrategyEnum.LRU) {
            return lruDiscard;
        }
        return null;
    }

    private static class FactoryHolder {
        private static final MapCacheDiscardStrategyFactory instance = new MapCacheDiscardStrategyFactory();
    }
}
