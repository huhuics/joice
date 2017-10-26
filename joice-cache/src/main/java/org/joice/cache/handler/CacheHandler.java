/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.handler;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.joice.cache.Cache;
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
 * @version $Id: CacheHandler.java, v 0.1 2017年10月25日 下午8:39:00 HuHui Exp $
 */
public class CacheHandler {

    private static final Logger         logger = LoggerFactory.getLogger(CacheHandler.class);

    private final Cache                 cache;

    private final String                nameSpace;

    private final KeyGenerator<Integer> keyGenerator;

    private final SpringELParser        elParser;

    public CacheHandler(Cache cache) {
        this.cache = cache;
        nameSpace = cache.getConfig().getNameSpace();
        keyGenerator = new HashCodeKeyGenerator();
        elParser = new SpringELParser();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object handleCacheable(Cacheable cacheable, ProceedingJoinPoint jp) throws Throwable {
        LogUtil.info(logger, "进入CacheHandler");

        Object proceedRet = null;

        //获取注解内参数
        String key = cacheable.key();
        int expireTime = cacheable.expireTime();
        boolean sync = cacheable.sync();
        String condition = cacheable.condition();
        Object[] args = jp.getArgs();
        AssertUtil.assertTrue(expireTime >= 0, "超时时间不能为负");

        //如果不满足缓存条件直接返回
        if (!isCacheable(condition, args)) {
            return jp.proceed();
        }

        Class returnType = TargetDetailUtil.getReturnType(jp);

        //组装CacheKey
        if (StringUtils.isBlank(key)) {
            key = (keyGenerator.getKey(jp.getTarget(), TargetDetailUtil.getMethod(jp), args)).toString();
        } else {
            key = getKeySpELValue(key, args);
        }
        CacheKey cacheKey = new CacheKey(nameSpace, key);

        //查询缓存
        CacheWrapper cacheWrapper = cache.get(cacheKey);

        if (cacheWrapper != null) { //缓存命中
            Object obj = cacheWrapper.getObj();
            if (returnType.isAssignableFrom(obj.getClass())) {
                return obj;
            }
        }

        //缓存未命中或者缓存的对象与实际返回值不一样
        proceedRet = jp.proceed();

        //放入缓存
        if (proceedRet != null) {
            CacheWrapper newWrapper = new CacheWrapper(proceedRet, expireTime);
            cache.set(cacheKey, newWrapper);
        }

        return proceedRet;
    }

    private boolean isCacheable(String conditionSpEl, Object[] args) {
        return elParser.getELBooleanValue(conditionSpEl, args);
    }

    private String getKeySpELValue(String keySpEl, Object[] args) {
        return elParser.getELStringValue(keySpEl, args);
    }

}
