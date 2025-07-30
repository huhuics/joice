/**
 * 
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.support.scheduler.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

/**
 * 默认调度任务对象(非阻塞)
 * @author HuHui
 * @version $Id: DefaultJob.java, v 0.1 2017年8月25日 上午9:19:21 HuHui Exp $
 */
public class DefaultJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String targetObject = jobDataMap.getString("targetObject");
        String targetMethod = jobDataMap.getString("targetMethod");

        try {
            ApplicationContext applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");
            Object refer = applicationContext.getBean(targetObject);
            refer.getClass().getDeclaredMethod(targetMethod).invoke(refer);
        } catch (Exception e) {
            throw new RuntimeException("执行任务出错", e);
        }

    }
}
