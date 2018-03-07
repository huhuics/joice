/**
 * Joice
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.schedule.job;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.joice.schedule.constant.Constant;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 普通Job
 * <p>通过反射执行实际任务类的方法</p>
 * @author HuHui
 * @version $Id: CommonJob.java, v 0.1 2018年1月23日 下午3:54:09 HuHui Exp $
 */
public class CommonJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String className = jobDataMap.getString(Constant.JOB_CLASS_NAME);
        String methodName = jobDataMap.getString(Constant.JOB_METHOD_NAME);

        try {
            Object obj = Class.forName(className).newInstance();
            MethodUtils.invokeMethod(obj, methodName);
        } catch (Exception e) {
            throw new RuntimeException("调用job类失败", e);
        }
    }
}
