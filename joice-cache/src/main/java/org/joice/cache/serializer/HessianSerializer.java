/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.joice.cache.exception.SerializationException;
import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.io.AbstractHessianInput;
import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;

/**
 * 使用Hessian2进行对象的序列化和反序列化
 * @author HuHui
 * @version $Id: HessianSerializer.java, v 0.1 2017年10月18日 下午7:33:09 HuHui Exp $
 */
public class HessianSerializer<T> implements Serializer<T> {

    private static final Logger     logger            = LoggerFactory.getLogger(HessianSerializer.class);

    private final SerializerFactory serializerFactory = new SerializerFactory();

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return null;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        AbstractHessianOutput out = new Hessian2Output(os);
        out.setSerializerFactory(serializerFactory);
        try {
            out.writeObject(t);
        } catch (Exception e) {
            LogUtil.error(e, logger, "hessian serialize failed");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.error("", e);
            }
        }
        return os.toByteArray();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T deserialize(byte[] bytes) throws SerializationException {
        if (ArrayUtils.isEmpty(bytes)) {
            return null;
        }
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        AbstractHessianInput in = new Hessian2Input(is);
        in.setSerializerFactory(serializerFactory);
        T t = null;
        try {
            t = (T) in.readObject();
        } catch (Exception e) {
            LogUtil.error(e, logger, "hessian deserialize failed");
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                logger.error("", e);
            }
        }
        return t;
    }

}
