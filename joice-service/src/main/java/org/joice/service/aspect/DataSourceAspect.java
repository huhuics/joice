/**
 * 深圳金融电子结算中心
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
import org.springframework.core.NamedThreadLocal;
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

    private static final Logger       logger      = LoggerFactory.getLogger(DataSourceAspect.class);

    private final ThreadLocal<String> threadLocal = new NamedThreadLocal<String>("DataSourceAspect");

    @Around("execution(* org.joice.service.facade.impl.*.*(..))")
    public void around(ProceedingJoinPoint point) {
        try {
            String className = point.getTarget().getClass().getName();
            String method = point.getSignature().getName();

            LogUtil.info(logger, "{0}.{1}({2})", className, method, StringUtils.join(point.getArgs(), ","));

            String dataSourceType = threadLocal.get();
            if (StringUtils.isNotBlank(threadLocal.get())) {
                LogUtil.info(logger, "dataSourceType : {0}", dataSourceType);
                HandleDataSource.putDataSource(dataSourceType);
                threadLocal.set(dataSourceType);
                return;
            }

            try {
                for (String key : ChooseDataSource.METHODTYPE.keySet()) {
                    for (String type : ChooseDataSource.METHODTYPE.get(key)) {
                        if (method.startsWith(type)) {
                            LogUtil.info(logger, "key : {0}", key);
                            HandleDataSource.putDataSource(key);
                            threadLocal.set(key);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                LogUtil.error(e, logger, "");
            }
        } finally {
            HandleDataSource.clear();
            LogUtil.info(logger, "数据库切换结束");
        }

    }

}
