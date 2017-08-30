/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.test.quartz;

import javax.annotation.Resource;

import org.joice.service.support.scheduler.SchedulerService;
import org.joice.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author HuHui
 * @version $Id: SchedulerServiceTest.java, v 0.1 2017年8月30日 下午7:52:32 HuHui Exp $
 */
public class SchedulerServiceTest extends BaseTest {

    @Resource
    private SchedulerService schedulerService;

    @Test
    public void test() {
        Assert.assertNotNull(schedulerService);
    }

}
