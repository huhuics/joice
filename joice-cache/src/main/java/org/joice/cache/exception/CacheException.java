/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.exception;

/**
 * 缓存异常
 * @author HuHui
 * @version $Id: CacheException.java, v 0.1 2017年10月19日 下午8:17:34 HuHui Exp $
 */
public class CacheException extends RuntimeException {

    /**  */
    private static final long serialVersionUID = -750891225464697966L;

    public CacheException() {
        super();
    }

    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheException(String message) {
        super(message);
    }

    public CacheException(Throwable cause) {
        super(cause);
    }

}
