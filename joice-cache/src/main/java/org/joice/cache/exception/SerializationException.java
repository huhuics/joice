/**
 * 
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.exception;

/**
 * 序列化异常
 * @author HuHui
 * @version $Id: SerializationException.java, v 0.1 2017年10月18日 下午7:00:22 HuHui Exp $
 */
public class SerializationException extends RuntimeException {

    /**  */
    private static final long serialVersionUID = -8270466295212466761L;

    public SerializationException() {
        super();
    }

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(Throwable cause) {
        super(cause);
    }

}
