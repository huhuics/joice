/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.serializer;

import java.lang.ref.SoftReference;

import com.caucho.hessian.io.AbstractSerializerFactory;
import com.caucho.hessian.io.Deserializer;
import com.caucho.hessian.io.HessianProtocolException;
import com.caucho.hessian.io.Serializer;

/**
 * 
 * @author HuHui
 * @version $Id: HessianSoftReferenceSerializerFactory.java, v 0.1 2017年9月29日 下午4:16:29 HuHui Exp $
 */
public class HessianSoftReferenceSerializerFactory extends AbstractSerializerFactory {

    private final SoftReferenceSerializer   beanSerializer   = new SoftReferenceSerializer();

    private final SoftReferenceDeserializer beanDeserializer = new SoftReferenceDeserializer();

    @Override
    @SuppressWarnings("rawtypes")
    public Serializer getSerializer(Class cl) throws HessianProtocolException {
        if (SoftReference.class.isAssignableFrom(cl)) {
            return beanSerializer;
        }
        return null;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Deserializer getDeserializer(Class cl) throws HessianProtocolException {
        if (SoftReference.class.isAssignableFrom(cl)) {
            return beanDeserializer;
        }
        return null;
    }

}
