/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.support.scheduler;

import java.util.List;

import javax.annotation.Resource;

import org.joice.common.dao.TaskFireLogMapper;
import org.joice.common.dao.domain.TaskFireLog;
import org.joice.common.dao.domain.TaskSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 调度服务类
 * @author HuHui
 * @version $Id: SchedulerService.java, v 0.1 2017年8月30日 下午7:47:05 HuHui Exp $
 */
@Service
public class SchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    @Resource
    SchedulerManager            schedulerManager;

    @Resource
    private TaskFireLogMapper   taskFireLogMapper;

    /**
     * 获取所有作业
     */
    public List<TaskSchedule> getAllTaskDetail() {
        return schedulerManager.getAllJobDetail();
    }

    /**
     * 执行作业
     */
    public void execTask(TaskSchedule taskSchedule) {
        schedulerManager.runJob(taskSchedule);
    }

    /**
     * 恢复作业
     */
    public void resumeTask(TaskSchedule taskSchedule) {
        schedulerManager.resumeJob(taskSchedule);
    }

    /**
     * 暂停作业
     */
    public void stopTask(TaskSchedule taskSchedule) {
        schedulerManager.stopJob(taskSchedule);
    }

    /**
     * 删除作业
     */
    public void delTask(TaskSchedule taskSchedule) {
        schedulerManager.delJob(taskSchedule);
    }

    /**
     * 增加或修改任务
     */
    public void addOrUpdateTask(TaskSchedule taskSchedule) {
        schedulerManager.addTask(taskSchedule);
    }

    /**
     * 查询执行记录
     */
    public List<TaskFireLog> queryFireLog() {
        //TODO
        return null;
    }

}
