/**
 * Joice
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package com.joice.schedule.simple.test;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * 
 * @author HuHui
 * @version $Id: SimpleJob.java, v 0.1 2018年1月16日 上午11:44:25 HuHui Exp $
 */
public class SimpleJob implements Job {

    private Long startTime;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();

        System.out.println(jobKey + "执行,参数start-time=" + startTime);
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

}
