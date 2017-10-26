/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.ke.gene;

import java.lang.reflect.Method;

/**
 * Key生成器接口
 * @author HuHui
 * @version $Id: KeyGenerator.java, v 0.1 2017年10月26日 上午12:26:29 HuHui Exp $
 */
public interface KeyGenerator<T> {

    /**
     * 获取key
     * @param obj
     * @return
     */
    T getKey(Object target, Method method, Object... params);

}
