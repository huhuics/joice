/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.serializer;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * Simple String to byte[] (and back) serializer
 * @author HuHui
 * @version $Id: StringSerializer.java, v 0.1 2017年9月28日 上午11:42:49 HuHui Exp $
 */
public class StringSerializer implements Serializer<String> {

    private final Charset charset;

    public StringSerializer() {
        this(Charset.forName("UTF8"));
    }

    public StringSerializer(Charset charset) {
        this.charset = charset;
    }

    @Override
    public Object deepClone(Object obj, Type type) throws Exception {
        if (obj == null) {
            return obj;
        }
        String str = (String) obj;
        return String.copyValueOf(str.toCharArray());
    }

    @Override
    public Object[] deepCloneMethodArgs(Method method, Object[] args) throws Exception {
        return (Object[]) deepClone(args, null);
    }

    @Override
    public byte[] serialize(String string) throws Exception {
        return (string == null ? null : string.getBytes(charset));
    }

    @Override
    public String deserialize(byte[] bytes, Type returnType) throws Exception {
        return (bytes == null ? null : new String(bytes, charset));
    }

}
