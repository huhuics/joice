/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.schedule.core;

import java.util.List;

import org.joice.schedule.domain.JobInfo;

/**
 * 任务调度管理接口
 * @author HuHui
 * @version $Id: ScheduleManager.java, v 0.1 2018年1月23日 上午10:35:20 HuHui Exp $
 */
public interface ScheduleManager {

    /**
     * 查询所有的调度任务
     */
    List<JobInfo> getAllJobDetail();

}
