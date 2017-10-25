/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存注解
 * @author HuHui
 * @version $Id: Cache.java, v 0.1 2017年10月24日 下午6:41:05 HuHui Exp $
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {

    /** 缓存key */
    String key() default "";

    /** 超时时间,默认为0即不超时 */
    int expireTime() default 0;

    /** 当有多个线程访问同一个缓存时是否同步 */
    boolean sync() default false;

}
