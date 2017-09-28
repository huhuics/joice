/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.serializer;

import java.lang.reflect.Type;

import org.joice.cache.clone.Cloner;

/**
 * 序列化接口
 * @author HuHui
 * @version $Id: Serializer.java, v 0.1 2017年9月28日 上午11:37:27 HuHui Exp $
 */
public interface Serializer<T> extends Cloner {

    /**
     * 将obj对象序列化成二进制数据
     * @param obj   object to serialize
     * @return      binary data
     * @throws Exception
     */
    byte[] serialize(final T obj) throws Exception;

    /**
     * 将二进制数据反序列化成对象
     * @param bytes      二进制数组
     * @param returnType 对象类型
     * @return           对象实例
     * @throws Exception
     */
    T deserialize(final byte[] bytes, final Type returnType) throws Exception;

}
