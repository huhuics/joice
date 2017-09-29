/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.serializer;

import java.io.IOException;
import java.lang.ref.SoftReference;

import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.AbstractSerializer;
import com.caucho.hessian.io.ObjectSerializer;
import com.caucho.hessian.io.Serializer;

/**
 * 
 * @author HuHui
 * @version $Id: SoftReferenceSerializer.java, v 0.1 2017年9月29日 下午4:24:23 HuHui Exp $
 */
public class SoftReferenceSerializer extends AbstractSerializer implements ObjectSerializer {

    @Override
    public Serializer getObjectSerializer() {
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void writeObject(Object obj, AbstractHessianOutput out) throws IOException {
        if (out.addRef(obj)) {
            return;
        }

        SoftReference<Object> data = (SoftReference<Object>) obj;

        int refV = out.writeObjectBegin(SoftReference.class.getName());

        if (refV == -1) {
            out.writeInt(1);
            out.writeString("ref");
            out.writeObjectBegin(SoftReference.class.getName());
        }

        if (data != null) {
            Object ref = data.get();
            if (ref != null) {
                out.writeObject(ref);
            } else {
                out.writeNull();
            }
        } else {
            out.writeNull();
        }

    }

}
