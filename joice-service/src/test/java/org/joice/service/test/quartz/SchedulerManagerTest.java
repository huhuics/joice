/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.test.quartz;

import java.util.List;

import javax.annotation.Resource;

import org.joice.common.dao.domain.TaskSchedule;
import org.joice.common.dao.domain.TaskSchedule.JobType;
import org.joice.service.support.scheduler.SchedulerManager;
import org.joice.service.test.BaseTest;
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
        taskSchedule.setJobType(JobType.defaultJob);
        taskSchedule.setTargetObject("sayHi");
        taskSchedule.setTargetMethod("say");
    }

    @Test
    public void testAddJob() {
        boolean ret = schedulerManager.addTask(taskSchedule);
        Assert.assertTrue(ret);
    }

    @Test
    public void testAddDefaultJob() {
        TaskSchedule taskSchedule = new TaskSchedule();
        taskSchedule.setTaskName("joice_default_job_001");
        taskSchedule.setTaskGroup("joice_default_job");
        taskSchedule.setStatus("1");
        taskSchedule.setTaskCron("0 0/5 * * * ?");
        taskSchedule.setTaskDesc("每5分钟执行一次的测试任务，非阻塞");
        taskSchedule.setJobType(JobType.defaultJob);
        taskSchedule.setTargetObject("sayHi");
        taskSchedule.setTargetMethod("say");

        boolean ret = schedulerManager.addTask(taskSchedule);
        Assert.assertTrue(ret);
    }

    @Test
    public void testAddStatefulJob() {
        TaskSchedule taskSchedule = new TaskSchedule();
        taskSchedule.setTaskName("joice_stateful_job_001");
        taskSchedule.setTaskGroup("joice_stateful_job");
        taskSchedule.setStatus("1");
        taskSchedule.setTaskCron("0/3 * * * * ?");
        taskSchedule.setTaskDesc("每3秒钟执行一次的测试任务，阻塞任务");
        taskSchedule.setJobType(JobType.statefulJob);
        taskSchedule.setTargetObject("sayHi");
        taskSchedule.setTargetMethod("sayAndSleep");

        boolean ret = schedulerManager.addTask(taskSchedule);
        Assert.assertTrue(ret);
    }

    @Test
    public void testGetAllJobDetail() {
        List<TaskSchedule> jobDetails = schedulerManager.getAllJobDetail();
        Assert.assertTrue(jobDetails.size() > 0);
    }

    @Test
    public void testDelJob() {
        TaskSchedule scheduleJob = new TaskSchedule();
        scheduleJob.setTaskName("joice_default_job_001");
        scheduleJob.setTaskGroup("joice_default_job");

        schedulerManager.delJob(scheduleJob);
    }

    /**
     * 测试修改任务执行计划并运行
     */
    @Test
    public void testModifyAndRun() {
        TaskSchedule taskSchedule = new TaskSchedule();
        taskSchedule.setTaskName("joice_job_003");
        taskSchedule.setTaskGroup("joice_job");

        //1.暂停任务
        schedulerManager.stopJob(taskSchedule);

        //2.修改配置
        taskSchedule.setStatus("1");
        taskSchedule.setTaskCron("0/15 * * * * ?");
        taskSchedule.setTaskDesc("每15秒执行一次的测试任务");
        taskSchedule.setJobType(JobType.defaultJob);
        taskSchedule.setTargetObject("sayHi");
        taskSchedule.setTargetMethod("say");

        schedulerManager.addTask(taskSchedule);

        //3.恢复启动
        schedulerManager.resumeJob(taskSchedule);

    }

}
