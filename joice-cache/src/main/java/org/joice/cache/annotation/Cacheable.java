/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存注解
 * @author HuHui
 * @version $Id: Cacheable.java, v 0.1 2017年10月24日 下午6:41:05 HuHui Exp $
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {

    /**
     * 缓存key,为空时表示使用自动生成hashcode作为key
     * 支持Spring EL表达式
     */
    String key() default "";

    /** 超时时间,默认为0即不超时 */
    int expireTime() default 0;

    /**
     * 当有多个线程访问同一个缓存时是否同步 ,避免当缓存查询为空时大量请求访问数据库
     * 默认为false表示不同步,如果为true,则意味着同一时间只能有一个线程访问cache,这将降低性能
     */
    boolean sync() default false;

    /**
     * 缓存条件,使用Spring EL表达式编写,可以为空
     * 返回true表示缓存,false表示不缓存
     */
    String condition() default "";

}
