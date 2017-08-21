/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.test.quartz;

import org.joice.test.BaseTest;
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
 * 简单的Quartz测试
 * @author HuHui
 * @version $Id: SimpleQuartzTest.java, v 0.1 2017年8月21日 下午7:40:40 HuHui Exp $
 */
public class SimpleQuartzTest extends BaseTest {

    @Test
    public void testPrint() throws SchedulerException, InterruptedException {

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        scheduler.start();

        //设置任务
        JobDetail job = JobBuilder.newJob(SayHello.class).withIdentity("Simple Quartz", "Test Quartz").build();

        //设置触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("Simple Trigger", "Test Trigger").startNow()
            .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(3).withRepeatCount(4)).build();

        scheduler.scheduleJob(job, trigger);

        Thread.sleep(15 * 1000);

        scheduler.shutdown();

    }

}
