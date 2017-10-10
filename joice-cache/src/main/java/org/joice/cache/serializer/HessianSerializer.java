/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.joice.cache.util.BeanUtil;

import com.caucho.hessian.io.AbstractHessianInput;
import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.AbstractSerializerFactory;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;

/**
 * 使用hessian序列化
 * @author HuHui
 * @version $Id: HessianSerializer.java, v 0.1 2017年9月29日 下午4:08:31 HuHui Exp $
 */
public class HessianSerializer implements Serializer<Object> {

    private static final SerializerFactory serializerFactory = new SerializerFactory();

    static {
        serializerFactory.addFactory(new HessianBigDecimalSerializerFactory());
        serializerFactory.addFactory(new HessianSoftReferenceSerializerFactory());
    }

    /**
     * 添加自定义SerializerFactory
     */
    public void addSerializerFactory(AbstractSerializerFactory factory) {
        serializerFactory.addFactory(serializerFactory);
    }

    @Override
    public byte[] serialize(final Object obj) throws Exception {
        if (obj == null) {
            return null;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        AbstractHessianOutput output = new Hessian2Output(outputStream);
        output.setSerializerFactory(serializerFactory);
        //将对象写到流里
        output.writeObject(obj);
        output.flush();
        byte[] val = outputStream.toByteArray();
        output.close();
        return val;
    }

    @Override
    public Object deserialize(final byte[] bytes, final Type returnType) throws Exception {
        if (ArrayUtils.isEmpty(bytes)) {
            return null;
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        AbstractHessianInput input = new Hessian2Input(inputStream);
        input.setSerializerFactory(serializerFactory);
        Object obj = input.readObject();
        input.close();
        return obj;
    }

    @Override
    public Object deepClone(Object obj, Type type) throws Exception {
        if (null == obj) {
            return null;
        }
        Class<?> clazz = obj.getClass();
        //常见不会被修改的数据类型
        if (BeanUtil.isPrimitive(obj) || clazz.isEnum() || obj instanceof Class || clazz.isAnnotation() || clazz.isSynthetic()) {
            return obj;
        }
        if (obj instanceof Date) {
            return ((Date) obj).clone();
        } else if (obj instanceof Calendar) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(((Calendar) obj).getTime().getTime());
            return cal;
        }
        return deserialize(serialize(obj), null);

    }

    @Override
    public Object[] deepCloneMethodArgs(Method method, Object[] args) throws Exception {
        if (args == null || args.length == 0) {
            return args;
        }
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        if (args.length != genericParameterTypes.length) {
            throw new RuntimeException("length of " + method.getClass().getName() + "." + method.getName() + " must " + genericParameterTypes.length);
        }
        Object[] res = new Object[args.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = deepClone(args[i], null);
        }
        return res;
    }

}
