/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.test.quartz;

import javax.annotation.Resource;

import org.joice.service.support.scheduler.SchedulerManager;
import org.joice.service.support.scheduler.TaskSchedule;
import org.joice.service.support.scheduler.TaskSchedule.JobType;
import org.joice.service.support.scheduler.TaskSchedule.TaskType;
import org.joice.test.BaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author HuHui
 * @version $Id: SchedulerManagerTest.java, v 0.1 2017年8月25日 下午8:34:04 HuHui Exp $
 */
public class SchedulerManagerTest extends BaseTest {

    @Resource
    private SchedulerManager schedulerManager;

    private TaskSchedule     taskSchedule = new TaskSchedule();

    @Before
    public void initJob() {
        taskSchedule.setStatus("1");
        taskSchedule.setTaskCron("0/3 * * * * ?");
        taskSchedule.setTaskDesc("这是一个简单的测试任务");
        taskSchedule.setJobType(JobType.job);
        taskSchedule.setTaskType(TaskType.local);
        taskSchedule.setTargetObject("sayHi");
        taskSchedule.setTargetMethod("say");
    }

    @Test
    public void testAddJob() {
        boolean ret = schedulerManager.updateTask(taskSchedule);
        Assert.assertTrue(ret);
    }

}
