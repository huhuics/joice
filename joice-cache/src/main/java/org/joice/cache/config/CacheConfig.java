/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.config;

import java.util.Map;

import org.joice.cache.type.AutoLoadQueueSortType;

/**
 * 缓存处理相关配置
 * @author HuHui
 * @version $Id: CacheConfig.java, v 0.1 2017年9月28日 下午12:27:05 HuHui Exp $
 */
public class CacheConfig {

    /** 命名空间 */
    private String                namespace;

    /** 处理自动加载队列的线程数 */
    private int                   threadCnt                      = 10;

    /** 自动加载队列中允许存放的最大容量 */
    private int                   maxElement                     = 20000;

    /** 是否打印比较耗时的请求 */
    private boolean               printSlowLog                   = true;

    /** 当请求耗时超过此值时,记录日志(printSlowLog=true时才有效),单位：毫秒 */
    private int                   slowLoadTime                   = 500;

    /** 自动加载队列排序算法 */
    private AutoLoadQueueSortType sortType                       = AutoLoadQueueSortType.NONE;

    /** 加载数据之前去缓存服务器中检查数据是否快过期
     *  如果应用程序部署的服务器数量较少,设置为false,如果数量较多,可考虑设置为true
     */
    private boolean               checkFromCacheBeforeLoad       = false;

    /** 单个线程中执行自动加载的时间间隔 */
    private int                   autoLoadPeriod                 = 50;

    /** 异步刷新缓存线程池corePoolSize */
    private int                   refreshThreadPoolSize          = 2;

    /** 异步刷新缓存线程池的maxSize */
    private int                   refreshThreadPoolMaxSize       = 20;

    /** 异步刷新缓存线程池的keepAliveTime.单位：分钟 */
    private int                   refreshThreadPoolKeepAliveTime = 20;

    /** 异步刷新缓存队列容量 */
    private int                   refreshQueueCapacity           = 2000;

    private Map<String, String>   functions;

    /** 加载数据重试册数,默认值为1 */
    private int                   loadDataTryCnt                 = 1;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public int getThreadCnt() {
        return threadCnt;
    }

    public void setThreadCnt(int threadCnt) {
        if (threadCnt <= 0) {
            return;
        }
        this.threadCnt = threadCnt;
    }

    public int getMaxElement() {
        return maxElement;
    }

    public void setMaxElement(int maxElement) {
        if (maxElement <= 0) {
            return;
        }
        this.maxElement = maxElement;
    }

    public boolean isPrintSlowLog() {
        return printSlowLog;
    }

    public void setPrintSlowLog(boolean printSlowLog) {
        this.printSlowLog = printSlowLog;
    }

    public int getSlowLoadTime() {
        return slowLoadTime;
    }

    public void setSlowLoadTime(int slowLoadTime) {
        if (slowLoadTime < 0) {
            return;
        }
        this.slowLoadTime = slowLoadTime;
    }

    public AutoLoadQueueSortType getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = AutoLoadQueueSortType.getById(sortType);
    }

    public boolean isCheckFromCacheBeforeLoad() {
        return checkFromCacheBeforeLoad;
    }

    public void setCheckFromCacheBeforeLoad(boolean checkFromCacheBeforeLoad) {
        this.checkFromCacheBeforeLoad = checkFromCacheBeforeLoad;
    }

    public int getAutoLoadPeriod() {
        return autoLoadPeriod;
    }

    public void setAutoLoadPeriod(int autoLoadPeriod) {
        if (autoLoadPeriod < 5) {
            return;
        }
        this.autoLoadPeriod = autoLoadPeriod;
    }

    public int getRefreshThreadPoolSize() {
        return refreshThreadPoolSize;
    }

    public void setRefreshThreadPoolSize(int refreshThreadPoolSize) {
        if (refreshThreadPoolSize > 1) {
            this.refreshThreadPoolSize = refreshThreadPoolSize;
        }
    }

    public int getRefreshThreadPoolMaxSize() {
        return refreshThreadPoolMaxSize;
    }

    public void setRefreshThreadPoolMaxSize(int refreshThreadPoolMaxSize) {
        if (refreshThreadPoolMaxSize > 1) {
            this.refreshThreadPoolMaxSize = refreshThreadPoolMaxSize;
        }
    }

    public int getRefreshThreadPoolKeepAliveTime() {
        return refreshThreadPoolKeepAliveTime;
    }

    public void setRefreshThreadPoolKeepAliveTime(int refreshThreadPoolKeepAliveTime) {
        if (refreshThreadPoolKeepAliveTime > 1) {
            this.refreshThreadPoolKeepAliveTime = refreshThreadPoolKeepAliveTime;
        }
    }

    public int getRefreshQueueCapacity() {
        return refreshQueueCapacity;
    }

    public void setRefreshQueueCapacity(int refreshQueueCapacity) {
        if (refreshQueueCapacity > 1) {
            this.refreshQueueCapacity = refreshQueueCapacity;
        }
    }

    public Map<String, String> getFunctions() {
        return functions;
    }

    public void setFunctions(Map<String, String> functions) {
        if (functions == null || functions.isEmpty()) {
            return;
        }
        this.functions = functions;
    }

    public int getLoadDataTryCnt() {
        return loadDataTryCnt;
    }

    public void setLoadDataTryCnt(int loadDataTryCnt) {
        if (loadDataTryCnt >= 0 && loadDataTryCnt < 5) {
            this.loadDataTryCnt = loadDataTryCnt;
        }
    }

    @Override
    public String toString() {
        return "CacheConfig [namespace=" + namespace + ", threadCnt=" + threadCnt + ", maxElement=" + maxElement + ", printSlowLog=" + printSlowLog
               + ", slowLoadTime=" + slowLoadTime + ", sortType=" + sortType + ", checkFromCacheBeforeLoad=" + checkFromCacheBeforeLoad + ", autoLoadPeriod="
               + autoLoadPeriod + ", refreshThreadPoolSize=" + refreshThreadPoolSize + ", refreshThreadPoolMaxSize=" + refreshThreadPoolMaxSize
               + ", refreshThreadPoolKeepAliveTime=" + refreshThreadPoolKeepAliveTime + ", refreshQueueCapacity=" + refreshQueueCapacity + ", functions="
               + functions + ", loadDataTryCnt=" + loadDataTryCnt + "]";
    }

}
