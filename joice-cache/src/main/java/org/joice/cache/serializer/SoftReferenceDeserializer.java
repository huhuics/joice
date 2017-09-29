/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.serializer;

import java.io.IOException;
import java.lang.ref.SoftReference;

import com.caucho.hessian.io.AbstractHessianInput;
import com.caucho.hessian.io.AbstractMapDeserializer;

/**
 * 
 * @author HuHui
 * @version $Id: SoftReferenceDeserializer.java, v 0.1 2017年9月29日 下午4:35:33 HuHui Exp $
 */
public class SoftReferenceDeserializer extends AbstractMapDeserializer {

    @Override
    public Object readObject(AbstractHessianInput in) throws IOException {
        try {
            SoftReference<Object> obj = instantiate();
            in.addRef(obj);
            Object value = in.readObject();
            obj = null;
            return new SoftReference<Object>(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected SoftReference<Object> instantiate() throws Exception {
        Object obj = new Object();
        return new SoftReference<Object>(obj);
    }

}
