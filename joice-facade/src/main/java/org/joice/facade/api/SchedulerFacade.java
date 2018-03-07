/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.facade.api;

import java.util.List;

import org.joice.common.dao.domain.TaskFireLog;
import org.joice.common.dao.domain.TaskSchedule;

/**
 * 调度接口门面
 * @author HuHui
 * @version $Id: SchedulerFacade.java, v 0.1 2017年8月30日 下午8:26:13 HuHui Exp $
 */
public interface SchedulerFacade {

    /**
     * 获取所有作业
     */
    List<TaskSchedule> getAllTaskDetail();

    /**
     * 执行作业
     */
    void execTask(TaskSchedule taskSchedule);

    /**
     * 恢复作业
     */
    void resumeTask(TaskSchedule taskSchedule);

    /**
     * 暂停作业
     */
    void stopTask(TaskSchedule taskSchedule);

    /**
     * 删除作业
     */
    void delTask(TaskSchedule taskSchedule);

    /**
     * 增加或修改任务
     */
    void addOrUpdateTask(TaskSchedule taskSchedule);

    /**
     * 查询执行记录
     */
    List<TaskFireLog> queryFireLog();
}
