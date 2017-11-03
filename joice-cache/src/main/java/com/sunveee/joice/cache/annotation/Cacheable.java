package com.sunveee.joice.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存注解
 * 
 * @author 51
 * @version $Id: Cacheable.java, v 0.1 2017年10月30日 下午5:21:26 51 Exp $
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {

    /**
     * 缓存所计入的目标参数位置
     * <p>默认为<code>""</code>，表示不包括参数
     * 
     * @return
     */
    String argRange() default "";

    /**
     * 缓存过期时间
     * <p>默认为0，表示永不过期
     * 
     * @return
     */
    int expireTime() default 0;

}
