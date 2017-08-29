/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.support.scheduler.job;

import org.joice.service.support.scheduler.TaskSchedule.TaskType;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * 默认调度任务对象(非阻塞)
 * @author HuHui
 * @version $Id: DefaultJob.java, v 0.1 2017年8月25日 上午9:19:21 HuHui Exp $
 */
public class DefaultJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(DefaultJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String taskType = jobDataMap.getString("taskType");
        String targetObject = jobDataMap.getString("targetObject");
        String targetMethod = jobDataMap.getString("targetMethod");

        try {
            ApplicationContext applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");
            if (TaskType.local.equals(taskType)) {
                Object refer = applicationContext.getBean(targetObject);
                refer.getClass().getDeclaredMethod(targetMethod).invoke(refer);
            } else if (TaskType.dubbo.equals(taskType)) {
                // TODO dubbo任务
            }
        } catch (Exception e) {
            throw new RuntimeException("执行任务出错", e);
        }

    }

}
