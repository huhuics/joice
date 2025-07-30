/**
 * 
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.serializer;

import org.joice.cache.exception.SerializationException;

/**
 * 序列化接口
 * @author HuHui
 * @version $Id: Serializer.java, v 0.1 2017年10月18日 下午6:57:44 HuHui Exp $
 */
public interface Serializer<T> {

    /**
     * 将制定对象序列化为二进制数据
     */
    byte[] serialize(T t) throws SerializationException;

    /**
     * 将二进制数据反序列化为对象
     */
    T deserialize(byte[] bytes) throws SerializationException;

}
