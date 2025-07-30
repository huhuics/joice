/**
 *
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package com.sunveee.joice.cache.serializer;

import java.nio.charset.Charset;

import org.apache.commons.lang3.SerializationException;

/**
 * 字符对象序列化类
 * 
 * @author HuHui
 * @version $Id: StringSerializer.java, v 0.1 2017年10月18日 下午7:06:55 HuHui Exp $
 */
public class StringSerializer implements Serializer<String> {

    private final Charset charset;

    public StringSerializer() {
        charset = Charset.forName("UTF-8");
    }

    @Override
    public byte[] serialize(String t) throws SerializationException {
        return t == null ? null : t.getBytes(charset);
    }

    @Override
    public String deserialize(byte[] bytes) throws SerializationException {
        return bytes == null ? null : new String(bytes, charset);
    }

}
