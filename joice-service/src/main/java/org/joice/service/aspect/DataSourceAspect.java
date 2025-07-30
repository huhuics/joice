/**
 * 
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.aspect;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * 切换数据源切面
 * 业务代码中不同方法调用不同数据源
 * @author HuHui
 * @version $Id: DataSourceAspect.java, v 0.1 2017年9月3日 下午5:29:26 HuHui Exp $
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    @Around("execution(* org.joice.common.dao.*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        try {
            String className = point.getTarget().getClass().getName();
            String method = point.getSignature().getName();

            LogUtil.info(logger, "{0}.{1}({2})", className, method, StringUtils.join(point.getArgs(), ","));

            try {
                L: for (String key : ChooseDataSource.METHODTYPE.keySet()) {
                    for (String type : ChooseDataSource.METHODTYPE.get(key)) {
                        if (method.startsWith(type)) {
                            LogUtil.info(logger, "current operation is [{0}]", key);
                            HandleDataSource.putDataSource(key);
                            break L;
                        }
                    }
                }
            } catch (Exception e) {
                LogUtil.error(e, logger, "");
            }
            return point.proceed();
        } finally {
            HandleDataSource.clear();
            LogUtil.info(logger, "数据库切换结束");
        }
    }

}
