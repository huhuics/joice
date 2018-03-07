/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.discard;

import org.joice.cache.enums.CacheDiscardPolicyEnum;

/**
 * 生成CacheDiscard工厂方法类
 * 使用单例模式生成CacheDiscardFactory
 * @author HuHui
 * @version $Id: CacheDiscardFactory.java, v 0.1 2017年10月24日 上午12:24:25 HuHui Exp $
 */
public class CacheDiscardFactory {

    private CacheDiscard fifoDiscard, lruDiscard;

    private CacheDiscardFactory() {
        fifoDiscard = new FifoCacheDiscard();
        lruDiscard = new LruCacheDiscard();
    }

    public static CacheDiscardFactory getInstance() {
        return FactoryHolder.instance;
    }

    /**
     * 根据丢弃策略的枚举值获取策略的实现类
     */
    public CacheDiscard getCacheDiscard(CacheDiscardPolicyEnum dpEnum) {
        if (dpEnum == CacheDiscardPolicyEnum.FIFO) {
            return fifoDiscard;
        } else if (dpEnum == CacheDiscardPolicyEnum.LRU) {
            return lruDiscard;
        }
        return null;
    }

    private static class FactoryHolder {
        private static final CacheDiscardFactory instance = new CacheDiscardFactory();
    }

}
