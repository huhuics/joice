/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.facade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.joice.common.dao.TaskFireLogMapper;
import org.joice.common.dao.domain.TaskFireLog;
import org.joice.common.dao.domain.TaskSchedule;
import org.joice.facade.api.SchedulerFacade;
import org.joice.service.support.scheduler.SchedulerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 调度门面接口实现类
 * @author HuHui
 * @version $Id: SchedulerFacadeImpl.java, v 0.1 2017年8月30日 下午8:40:33 HuHui Exp $
 */
@Service("schedulerFacade")
public class SchedulerFacadeImpl implements SchedulerFacade {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerFacadeImpl.class);

    @Resource
    SchedulerManager            schedulerManager;

    @Resource
    private TaskFireLogMapper   taskFireLogMapper;

    @Override
    public List<TaskSchedule> getAllTaskDetail() {
        return schedulerManager.getAllJobDetail();
    }

    @Override
    public void execTask(TaskSchedule taskSchedule) {
        schedulerManager.runJob(taskSchedule);
    }

    @Override
    public void resumeTask(TaskSchedule taskSchedule) {
        schedulerManager.resumeJob(taskSchedule);
    }

    @Override
    public void stopTask(TaskSchedule taskSchedule) {
        schedulerManager.stopJob(taskSchedule);
    }

    @Override
    public void delTask(TaskSchedule taskSchedule) {
        schedulerManager.delJob(taskSchedule);
    }

    @Override
    public void addOrUpdateTask(TaskSchedule taskSchedule) {
        schedulerManager.addTask(taskSchedule);
    }

    @Override
    public List<TaskFireLog> queryFireLog() {
        //TODO
        return null;
    }

}
