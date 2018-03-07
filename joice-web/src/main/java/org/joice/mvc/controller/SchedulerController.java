/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.mvc.controller;

import java.util.List;

import javax.annotation.Resource;

import org.joice.common.dao.domain.TaskSchedule;
import org.joice.common.util.LogUtil;
import org.joice.facade.api.SchedulerFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 调度Controller
 * @author HuHui
 * @version $Id: SchedulerController.java, v 0.1 2017年8月31日 下午9:29:08 HuHui Exp $
 */
@Controller
public class SchedulerController {

    private static final Logger logger         = LoggerFactory.getLogger(SchedulerController.class);

    private static final String SCHEDULER_LIST = "schedulerList";

    @Resource
    private SchedulerFacade     schedulerFacade;

    @GetMapping("scheduler/list")
    public String getSchedulerList(ModelMap modelMap) {
        LogUtil.info(logger, "收到获取调度任务列表请求");

        List<TaskSchedule> tasks = schedulerFacade.getAllTaskDetail();

        LogUtil.info(logger, "查询到调度任务数量:{0}", tasks.size());

        modelMap.put("tasks", tasks);
        modelMap.put("taskSize", tasks.size());

        return SCHEDULER_LIST;
    }

}
