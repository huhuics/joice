/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.joice.cache.annotation.Cache;
import org.joice.cache.aop.CacheAopProxyChain;
import org.joice.cache.config.CacheConfig;
import org.joice.cache.to.CacheKeyTO;
import org.joice.cache.to.CacheWrapper;

/**
 * 缓存刷新处理器
 * @author HuHui
 * @version $Id: RefreshHandler.java, v 0.1 2017年10月15日 上午11:09:40 HuHui Exp $
 */
public class RefreshHandler {

    /** 刷新缓存线程池 */
    private final ThreadPoolExecutor                  refreshThreadPool;

    /** 正在刷新的缓存队列 */
    private final ConcurrentHashMap<CacheKeyTO, Byte> refreshingMap;

    private final CacheHandler                        cacheHandler;

    public RefreshHandler(CacheHandler cacheHandler, CacheConfig config) {
        this.cacheHandler = cacheHandler;
        int corePoolSize = config.getRefreshThreadPoolSize();
        int maximumPoolSize = config.getRefreshThreadPoolMaxSize();
        int keepAliveTime = config.getRefreshThreadPoolKeepAliveTime();
        TimeUnit unit = TimeUnit.MINUTES;
        int queueCapacity = config.getRefreshQueueCapacity();
        refreshingMap = new ConcurrentHashMap<CacheKeyTO, Byte>(queueCapacity);
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(queueCapacity);
        RejectedExecutionHandler rejectedHandler = new RefreshRejectedExecutionHandler();
        refreshThreadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, queue, new ThreadFactory() {

            private final AtomicInteger threadNumber = new AtomicInteger(1);

            private final String        namePrefix   = "joic-cache-refreshHandler-";

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, namePrefix + threadNumber.getAndIncrement());
                t.setDaemon(true);
                return t;
            }
        }, rejectedHandler);

    }

    public void removeTask(CacheKeyTO cacheKey) {
        refreshingMap.remove(cacheKey);
    }

    public void doRefresh(CacheAopProxyChain pjp, Cache cache, CacheKeyTO cacheKey, CacheWrapper<Object> cacheWrapper) {
        int expire = cacheWrapper.getExpire();
        if (expire < 120) {//如果过期时间太小则不允许自动加载,避免加载过于频繁影响系统稳定性
            return;
        }
        //计算超时时间
        int alarmTime = cache.alarmTime();
        long timeout;
        if (alarmTime > 0 && alarmTime < expire) {
            timeout = expire - alarmTime;
        } else {
            if (expire >= 600) {
                timeout = expire - 120;
            } else {
                timeout = expire - 60;
            }
        }
        //如果数据频繁被访问则不自动加载
        if ((System.currentTimeMillis() - cacheWrapper.getLastLoadTime()) < (timeout * 1000)) {
            return;
        }

        Byte tmpByte = refreshingMap.get(cacheKey);
        if (tmpByte != null) {//如果正在刷新的请求则不处理
            return;
        }
        tmpByte = 1;
        if (refreshingMap.putIfAbsent(cacheKey, tmpByte) == null) {
            //TODO
            refreshThreadPool.execute(new RefreshTask(pjp, cache, cacheKey, cacheWrapper));
        }
    }

    class RefreshTask implements Runnable {

        private final CacheAopProxyChain   pjp;

        private final Cache                cache;

        private final CacheKeyTO           cacheKey;

        private final CacheWrapper<Object> cacheWrapper;

        private final Object[]             arguments;

        public RefreshTask(CacheAopProxyChain pjp, Cache cache, CacheKeyTO cacheKey, CacheWrapper<Object> cacheWrapper) {
            this.pjp = pjp;
            this.cache = cache;
            this.cacheKey = cacheKey;
            this.cacheWrapper = cacheWrapper;
            this.arguments = pjp.getArgs();
        }

        @Override
        public void run() {
        }

        public CacheKeyTO getCacheKey() {
            return cacheKey;
        }

    }

    class RefreshRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (!executor.isShutdown()) {
                Runnable last = executor.getQueue().poll();
                if (last instanceof RefreshTask) {
                    RefreshTask lastTask = (RefreshTask) last;
                    refreshingMap.remove(lastTask.getCacheKey());
                }
            }
        }
    }

}
