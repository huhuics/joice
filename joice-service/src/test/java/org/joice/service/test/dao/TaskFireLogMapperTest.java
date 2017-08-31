/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.test.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.joice.common.dao.TaskFireLogMapper;
import org.joice.common.dao.domain.TaskFireLog;
import org.joice.common.dao.domain.TaskFireLog.JOBSTATUS;
import org.joice.common.util.LogUtil;
import org.joice.service.test.BaseTest;
import org.joice.service.util.NativeUtil;
import org.junit.Test;

/**
 * 
 * @author HuHui
 * @version $Id: TaskFireLogMapperTest.java, v 0.1 2017年8月29日 下午8:13:34 HuHui Exp $
 */
public class TaskFireLogMapperTest extends BaseTest {

    @Resource
    private TaskFireLogMapper taskFireLogMapper;

    @Test
    public void testInsertAndGetId() {
        TaskFireLog log = new TaskFireLog();
        log.setStartTime(new Date());
        log.setGroupName("group name");
        log.setTaskName("task name");
        log.setStatus(JOBSTATUS.INIT_STATUS);
        log.setServerHost(NativeUtil.getHostName());
        log.setServerDuid(NativeUtil.getDUID());

        taskFireLogMapper.insertAndGetId(log);

        Long id = log.getId();

        LogUtil.info(logger, "id = {0}", id);

    }
}
