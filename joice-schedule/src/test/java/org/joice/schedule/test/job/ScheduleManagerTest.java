/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.schedule.test.job;

import java.util.List;

import javax.annotation.Resource;

import org.joice.schedule.core.ScheduleManager;
import org.joice.schedule.domain.JobInfo;
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

}
