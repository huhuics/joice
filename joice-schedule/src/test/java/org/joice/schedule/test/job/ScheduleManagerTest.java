/**
 * Joice
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.schedule.test.job;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.joice.schedule.core.ScheduleManager;
import org.joice.schedule.domain.JobInfo;
import org.joice.schedule.enums.JobTypeEnum;
import org.joice.schedule.request.AddJobRequest;
import org.joice.schedule.request.RemoveJobRequest;
import org.joice.schedule.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author HuHui
 * @version $Id: ScheduleManagerTest.java, v 0.1 2018年1月23日 下午2:36:32 HuHui Exp $
 */
public class ScheduleManagerTest extends BaseTest {

    @Resource
    private ScheduleManager manager;

    @Test
    public void testGetAllJobDetail() throws InterruptedException {
        Assert.assertNotNull(manager);

        List<JobInfo> jobInfos = manager.getAllJobDetail();

        Assert.assertTrue(jobInfos.size() > 0);

        Thread.sleep(60 * 1000);
    }

    @Test
    public void testAddJob() {

        boolean ret = manager.addJob(buildAddCommonJobRequest());

        Assert.assertTrue(ret);
    }

    @Test
    public void testRemoveJob() {

        RemoveJobRequest request = new RemoveJobRequest("simple-cron-job", "quartz-test");

        boolean ret = manager.removeJob(request);

        Assert.assertTrue(ret);
    }

    private AddJobRequest buildAddCommonJobRequest() {
        AddJobRequest request = new AddJobRequest();
        Date date = new Date();
        request.setJobName("job_" + date);
        request.setJobGroup("job_group_" + date);
        request.setTriggerName("trigger_" + date);
        request.setTriggerGroup("trigger_group_" + date);
        request.setJobDescription("job_desc" + date);
        request.setCronExpression("*/8 * * * * ?");
        request.setJobDescription("a test add job");
        request.setJobClassName("org.joice.schedule.test.job.SimpleJob");
        request.setJobMethodName("org.joice.schedule.test.job.CronPrinter");
        request.setJobMethodName("print");
        request.setJobType(JobTypeEnum.COMMON_JOB);

        return request;
    }
}
