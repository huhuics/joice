/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.script;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.joice.cache.annotation.Cache;

/**
 * 表达式处理抽象类
 * @author HuHui
 * @version $Id: AbstractScriptParser.java, v 0.1 2017年10月13日 下午3:11:31 HuHui Exp $
 */
public abstract class AbstractScriptParser {

    protected static final String ARGS    = "args";

    protected static final String RET_VAL = "retVal";

    protected static final String HASH    = "hash";

    protected static final String EMPTY   = "empty";

    /**
     * 为简化表达式,方便调用Java static方法,在这里注入表达式自定义方法
     * @param name     自定义方法名
     * @param method   调用的方法
     */
    public abstract void addFunction(String name, Method method);

    /**
     * 将表达式转换为期望的值
     * @param keyEL      生成缓存key的表达式
     * @param arguments  参数
     * @param retVal     结果值(缓存数据)
     * @param hasRetVal  是否使用retVal参数
     * @param valueType  表达式最终返回值类型
     * @return T value   返回值
     * @throws Exception
     */
    public abstract <T> T getELValue(String keyEL, Object[] arguments, Object retVal, boolean hasRetVal, Class<T> valueType) throws Exception;

    /**
     * 将表达式转换为期望的值
     * @param keyEL
     * @param arguments
     * @param valueType
     * @return
     * @throws Exception
     */
    public <T> T getELValue(String keyEL, Object[] arguments, Class<T> valueType) throws Exception {
        return getELValue(keyEL, arguments, null, false, valueType);
    }

    /**
     * 根据请求参数和执行结果值进行构造缓存Key
     * @param keyEL
     * @param arguments
     * @param retVal
     * @param hasRetVal
     * @return
     * @throws Exception
     */
    public String getDefinedCacheKey(String keyEL, Object[] arguments, Object retVal, boolean hasRetVal) throws Exception {
        return getELValue(keyEL, arguments, retVal, hasRetVal, String.class);
    }

    /**
     * 是否可以缓存
     * @param cache      Cache
     * @param arguments  参数
     * @return cacheAble 是否可以进行缓存
     * @throws Exception
     */
    public boolean isCacheable(Cache cache, Object[] arguments) throws Exception {
        boolean rv = true;
        if (arguments != null && arguments.length > 0 && cache.condition() != null && cache.condition().length() > 0) {
            rv = getELValue(cache.condition(), arguments, Boolean.class);
        }
        return rv;
    }

    /**
     * 是否可以缓存
     * @param cache      Cache
     * @param arguments  参数
     * @param result     执行结果
     * @return cacheAble 是否可以进行缓存
     * @throws Exception
     */
    public boolean isCacheable(Cache cache, Object[] arguments, Object result) throws Exception {
        boolean rv = true;
        if (cache.condition() != null && cache.condition().length() > 0) {
            rv = getELValue(cache.condition(), arguments, result, true, Boolean.class);
        }
        return rv;
    }

    /**
     * 获取真实的缓存失效时间值
     */
    public int getRealExpire(int expire, String expireExpression, Object[] arguments, Object result) throws Exception {
        Integer tmpExpire = null;
        if (StringUtils.isNotEmpty(expireExpression)) {
            tmpExpire = getELValue(expireExpression, arguments, result, true, Integer.class);
            if (tmpExpire != null && tmpExpire.intValue() >= 0) {
                return tmpExpire.intValue();
            }
        }
        return expire;
    }

}
