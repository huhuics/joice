/**
 *
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package com.sunveee.joice.cache.util;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取切点的详细信息工具类
 * 
 * @author 51
 * @version $Id: TargetDetailUtil.java, v 0.1 2017年11月3日 下午2:37:44 51 Exp $
 */
public class TargetDetailUtil {
    private static final Logger logger = LoggerFactory.getLogger(TargetDetailUtil.class);

    /**
     * 获取切点方法所在类
     */
    public static Class<?> getClass(ProceedingJoinPoint pjp) {
        return pjp.getTarget().getClass();
    }

    /**
     * 获取切点方法
     */
    public static Method getMethod(ProceedingJoinPoint pjp) {
        Signature signature = pjp.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        MethodSignature ms = (MethodSignature) signature;
        Object target = pjp.getTarget();

        Method result = null;
        try {
            result = target.getClass().getMethod(ms.getName(), ms.getParameterTypes());
        } catch (NoSuchMethodException | SecurityException e) {
            LogUtil.error(e, logger, "获取注解方法时发生异常");
        }
        return result;
    }

    /**
     * 获取切点方法的返回值类型
     */
    public static Class<?> getReturnType(ProceedingJoinPoint pjp) {
        Signature signature = pjp.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        MethodSignature ms = (MethodSignature) signature;
        Class<?> returnType = ms.getReturnType();
        return returnType;
    }

}
