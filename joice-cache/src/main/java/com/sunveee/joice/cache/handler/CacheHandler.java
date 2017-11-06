/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package com.sunveee.joice.cache.handler;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.joice.common.util.AssertUtil;
import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunveee.joice.cache.Cache;
import com.sunveee.joice.cache.annotation.Cacheable;
import com.sunveee.joice.cache.model.Key;
import com.sunveee.joice.cache.model.Value;
import com.sunveee.joice.cache.util.KeyGenetrator;
import com.sunveee.joice.cache.util.TargetDetailUtil;

/**
 * 缓存处理类
 * 
 * @author 51
 * @version $Id: CacheHandler.java, v 0.1 2017年11月3日 下午4:24:22 51 Exp $
 */
public class CacheHandler {

    private static final Logger logger = LoggerFactory.getLogger(CacheHandler.class);

    private final Cache         cache;

    public CacheHandler(Cache cache) {
        this.cache = cache;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object handleCacheable(Cacheable cacheable, ProceedingJoinPoint pjp) throws Throwable {
        Object proceedRet = null;

        // 获取注解配置
        int[] argRange = cacheable.argRange();
        int expireTime = cacheable.expireTime();
        Object[] args = pjp.getArgs();
        AssertUtil.assertTrue(expireTime >= 0, "超时时间不能为负");

        LogUtil.info(logger, "注解配置: argRange={0},expireTime={1}", StringUtils.join(argRange, ','), expireTime);

        Class returnType = TargetDetailUtil.getReturnType(pjp);

        // 根据配置获取Key
        Key key = KeyGenetrator.generateKey(TargetDetailUtil.getClass(pjp).getCanonicalName(), TargetDetailUtil.getMethod(pjp).getName(), args, argRange);

        // 查询缓存
        Value value = cache.get(key);

        if (value != null) { // 缓存命中
            Object obj = value.getObj();
            if (returnType.isAssignableFrom(obj.getClass())) {
                return obj;
            }
        }

        // 缓存未命中或者缓存的对象与实际返回值不一样
        proceedRet = pjp.proceed();

        // 放入缓存
        if (proceedRet != null) {
            Value newValue = new Value(proceedRet, expireTime);
            cache.set(key, newValue);
        }

        return proceedRet;
    }

}
