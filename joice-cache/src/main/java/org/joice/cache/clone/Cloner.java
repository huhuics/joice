/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.clone;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 深度复制
 * @author HuHui
 * @version $Id: Cloner.java, v 0.1 2017年9月28日 上午11:28:54 HuHui Exp $
 */
public interface Cloner {

    /**
     * 深度复制Object
     * @param obj  Object
     * @param type obj的类型,当以json来处理时,可以提升性能,如果获取不到type则可以为null
     * @return     Object
     * @throws Exception
     */
    Object deepClone(Object obj, final Type type) throws Exception;

    /**
     * 深度复制Method中的参数
     * @param method  Method
     * @param args    参数
     * @return        参数数组
     * @throws Exception
     */
    Object[] deepCloneMethodArgs(Method method, Object[] args) throws Exception;

}
