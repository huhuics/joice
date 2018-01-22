/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.schedule.test.job;

import javax.annotation.Resource;

import org.joice.schedule.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

/**
 * 
 * @author HuHui
 * @version $Id: SimpleJobTest.java, v 0.1 2018年1月22日 下午8:46:40 HuHui Exp $
 */
public class SimpleJobTest extends BaseTest {

    @Resource
    private Scheduler scheduler;

    @Test
    public void testSimpleJob() throws SchedulerException, InterruptedException {
        Assert.assertNotNull(scheduler);

        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity("simple-cron-job", "quartz-test")
            .usingJobData("startTime", System.currentTimeMillis()).build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("simple-cron-job-trigger", "quartz-test").startNow()
            .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?")).build();

        scheduler.scheduleJob(jobDetail, trigger);

        scheduler.start();

        Thread.sleep(60 * 1000);
    }

}
