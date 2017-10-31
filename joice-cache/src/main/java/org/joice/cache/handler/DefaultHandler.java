/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.handler;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.joice.cache.Cache;
import org.joice.cache.annotation.CacheDel;
import org.joice.cache.annotation.CacheDelItem;
import org.joice.cache.annotation.Cacheable;
import org.joice.cache.ke.gene.HashCodeKeyGenerator;
import org.joice.cache.ke.gene.KeyGenerator;
import org.joice.cache.ke.gene.SpringELParser;
import org.joice.cache.to.CacheKey;
import org.joice.cache.to.CacheWrapper;
import org.joice.cache.util.TargetDetailUtil;
import org.joice.common.util.AssertUtil;
import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存处理类
 * @author HuHui
 * @version $Id: CacheableHandler.java, v 0.1 2017年10月25日 下午8:39:00 HuHui Exp $
 */
public class DefaultHandler implements Handler {

    private static final Logger         logger = LoggerFactory.getLogger(DefaultHandler.class);

    private Cache                       cache;

    private final String                nameSpace;

    private final KeyGenerator<Integer> keyGenerator;

    private final SpringELParser        elParser;

    public DefaultHandler(Cache cache) {
        this.cache = cache;
        nameSpace = cache.getConfig().getNameSpace();
        keyGenerator = new HashCodeKeyGenerator();
        elParser = new SpringELParser();
    }

    @Override
    public Object handleCacheable(ProceedingJoinPoint jp) throws Throwable {

        Method method = TargetDetailUtil.getMethod(jp);
        Cacheable cacheable = method.getAnnotation(Cacheable.class);

        //获取注解内参数
        String key = cacheable.key();
        int expireTime = cacheable.expireTime();
        String condition = cacheable.condition();
        boolean sync = cacheable.sync();
        Object[] args = jp.getArgs();
        AssertUtil.assertTrue(expireTime >= 0, "超时时间不能为负");

        LogUtil.info(logger, "key={0},expireTime={1},condition={2},args={3}", key, expireTime, condition, args);

        //如果不满足缓存条件直接返回
        if (StringUtils.isNotBlank(condition) && !isCacheable(condition, args)) {
            return jp.proceed();
        }

        //组装CacheKey
        if (StringUtils.isBlank(key)) {
            key = (keyGenerator.getKey(jp.getTarget(), method, args)).toString();
        } else {
            key = getKeySpELValue(key, args);
        }
        CacheKey cacheKey = new CacheKey(nameSpace, key);

        return getValue(jp, cacheKey, sync, expireTime);

    }

    private Object getValue(ProceedingJoinPoint jp, CacheKey cacheKey, boolean sync, int expireTime) throws Throwable {
        CacheWrapper cacheWrapper = cache.get(cacheKey);
        if (cacheWrapper != null) {//缓存命中
            return cacheWrapper.getObj();
        }

        Object proceedRet = null;
        if (sync) {
            if (cache.setMutex(cacheKey) == 1L) {
                proceedRet = jp.proceed();
                //放入缓存
                if (proceedRet != null) {
                    CacheWrapper newWrapper = new CacheWrapper(proceedRet, expireTime);
                    cache.set(cacheKey, newWrapper);
                }
                return proceedRet;
            } else {
                Thread.sleep(50);
                return getValue(jp, cacheKey, sync, expireTime);
            }
        } else {
            proceedRet = jp.proceed();
            //放入缓存
            if (proceedRet != null) {
                CacheWrapper newWrapper = new CacheWrapper(proceedRet, expireTime);
                cache.set(cacheKey, newWrapper);
            }
            return proceedRet;
        }
    }

    /**
     * 缓存删除的逻辑:
     * 先操作数据库,成功以后再删除缓存
     */
    @Override
    public Object handleCacheDel(ProceedingJoinPoint jp) throws Throwable {
        Object proceedRet = jp.proceed();

        Method method = TargetDetailUtil.getMethod(jp);
        CacheDel cacheDel = method.getAnnotation(CacheDel.class);
        String condition = cacheDel.condition();

        if (StringUtils.isNotBlank(condition) && !isDel(condition, proceedRet)) {//不满足缓存删除条件
            return proceedRet;
        }

        try {
            CacheDelItem[] items = cacheDel.items();
            for (CacheDelItem item : items) {
                String keySpEL = item.key();
                if (StringUtils.isBlank(keySpEL)) {
                    continue;
                }
                String key = getKeySpELValue(keySpEL, jp.getArgs());
                CacheKey cacheKey = new CacheKey(nameSpace, key);
                cache.delete(cacheKey);
            }
        } catch (Exception e) {
            logger.error("删除缓存出错", e);
        }

        return proceedRet;
    }

    private boolean isCacheable(String conditionSpEl, Object[] args) {
        return elParser.getELBooleanValue(conditionSpEl, args);
    }

    private String getKeySpELValue(String keySpEl, Object[] args) {
        return elParser.getELStringValue(keySpEl, args);
    }

    private boolean isDel(String conditionSpE, Object retVal) {
        return elParser.getELRetVal(conditionSpE, null, retVal);
    }

}
