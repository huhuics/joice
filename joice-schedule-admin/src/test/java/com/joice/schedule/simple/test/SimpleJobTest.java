/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package com.joice.schedule.simple.test;

import org.junit.Before;
import org.junit.Test;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 测试简单调度任务
 * @author HuHui
 * @version $Id: SimpleJobTest.java, v 0.1 2018年1月16日 上午11:14:31 HuHui Exp $
 */
public class SimpleJobTest {

    private Scheduler scheduler;

    @Before
    public void init() throws SchedulerException {
        scheduler = StdSchedulerFactory.getDefaultScheduler();
    }

    @Test
    public void testRunJob() throws SchedulerException, InterruptedException {
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity("simple-job", "quartz-test")
            .usingJobData("startTime", System.currentTimeMillis()).build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("simple-job-trigger", "quartz test").startNow()
            .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(5)).build();

        scheduler.scheduleJob(jobDetail, trigger);

        scheduler.start();

        Thread.sleep(60 * 1000);
    }

}
