/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.schedule.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务监听器
 * @author HuHui
 * @version $Id: JobListener.java, v 0.1 2018年1月23日 下午2:23:58 HuHui Exp $
 */
public class JobListener extends JobListenerSupport {

    private static final Logger logger            = LoggerFactory.getLogger(JobListener.class);

    private static final String JOB_LISTENER_NAME = "joice_job_listener";

    @Override
    public String getName() {
        return JOB_LISTENER_NAME;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {

    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {

    }

}
