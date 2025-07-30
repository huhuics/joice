/**
 * 
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.web.test.dubbo;

import java.util.List;

import javax.annotation.Resource;

import org.joice.common.dao.domain.TaskSchedule;
import org.joice.facade.api.SchedulerFacade;
import org.joice.web.test.base.BaseTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试dubbo调用
 * @author HuHui
 * @version $Id: SchedulerFacadeTest.java, v 0.1 2017年8月31日 下午9:16:07 HuHui Exp $
 */
public class SchedulerFacadeTest extends BaseTest {

    @Resource
    private SchedulerFacade schedulerFacade;

    @Test
    public void testGetAllTaskDetail() {
        Assert.assertNotNull(schedulerFacade);
        List<TaskSchedule> ret = schedulerFacade.getAllTaskDetail();

        Assert.assertTrue(ret.size() > 0);
    }

}
