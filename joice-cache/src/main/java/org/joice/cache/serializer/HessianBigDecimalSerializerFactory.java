/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.serializer;

import java.math.BigDecimal;

import com.caucho.hessian.io.AbstractSerializerFactory;
import com.caucho.hessian.io.BigDecimalDeserializer;
import com.caucho.hessian.io.Deserializer;
import com.caucho.hessian.io.HessianProtocolException;
import com.caucho.hessian.io.Serializer;
import com.caucho.hessian.io.StringValueSerializer;

/**
 * 
 * @author HuHui
 * @version $Id: HessianBigDecimalSerializerFactory.java, v 0.1 2017年9月29日 下午4:15:36 HuHui Exp $
 */
public class HessianBigDecimalSerializerFactory extends AbstractSerializerFactory {

    private static final StringValueSerializer  bigDecimalSerializer   = new StringValueSerializer();

    private static final BigDecimalDeserializer bigDecimalDeserializer = new BigDecimalDeserializer();

    @Override
    @SuppressWarnings("rawtypes")
    public Serializer getSerializer(Class cl) throws HessianProtocolException {
        if (BigDecimal.class.isAssignableFrom(cl)) {
            return bigDecimalSerializer;
        }
        return null;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Deserializer getDeserializer(Class cl) throws HessianProtocolException {
        if (BigDecimal.class.isAssignableFrom(cl)) {
            return bigDecimalDeserializer;
        }
        return null;
    }

}
