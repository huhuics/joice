/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.joice.cache.type.CacheOpType;

/**
 * 缓存注释
 * @author HuHui
 * @version $Id: Cache.java, v 0.1 2017年10月13日 下午3:29:48 HuHui Exp $
 */
@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {

    /** 缓存过期时间,单位:秒.为0则表示永久缓存 */
    int expire();

    /** 动态获取缓存过期时间的表达式 */
    String expireExpression() default "";

    /** 自定义缓存key,支持表达式 */
    String key();

    /** 设置Hash表中的字段,如果设置此项,则使用Hash表进行存储,支持表达式 */
    String hfield() default "";

    /** 缓存的条件表达式,可以为空,值为true或者false,为true才进行缓存 */
    String condition() default "";

    /** 缓存的操作类型 */
    CacheOpType opType() default CacheOpType.READ_WRITE;

    /** 并发等待时间(ms),等待正在DAO中加载数据的线程返回的等待时间 */
    int waitTimeOut() default 500;

}
