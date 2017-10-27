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
 * 缓存删除注解
 * @author HuHui
 * @version $Id: CacheDel.java, v 0.1 2017年10月27日 上午10:37:04 HuHui Exp $
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheDel {

    /**
     * {@link CacheDelItem}数组,可以一个或多个,表示删除一个或多个缓存
     */
    CacheDelItem[] items();

    /**
     * 缓存删除条件,满足条件才删除缓存,支持Spring EL表达式
     */
    String condition() default "";

}
