/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.ke.gene;

import java.lang.reflect.Method;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 生成对象的HashCode值作为缓存的key
 * @author HuHui
 * @version $Id: HashCodeKeyGenerator.java, v 0.1 2017年10月26日 下午3:27:01 HuHui Exp $
 */
public class HashCodeKeyGenerator implements KeyGenerator<Integer> {

    @Override
    public Integer getKey(Object target, Method method, Object... params) {
        KeyWrapper keyWrapper = new KeyWrapper(target.getClass().getName(), method.getName(), params);
        int code = HashCodeBuilder.reflectionHashCode(keyWrapper.toString(), true);
        return code;
    }

}
