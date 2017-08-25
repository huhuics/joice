/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.support.scheduler;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joice.common.dao.TaskFireLogMapper;
import org.joice.common.dao.domain.TaskFireLog;
import org.joice.common.dao.domain.TaskFireLog.JOBSTATUS;
import org.joice.common.util.LogUtil;
import org.joice.service.util.NativeUtil;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 调度监听器
 * @author HuHui
 * @version $Id: JobListener.java, v 0.1 2017年8月25日 下午2:16:19 HuHui Exp $
 */
public class JobListener implements org.quartz.JobListener {

    private static final Logger logger  = LoggerFactory.getLogger(JobListener.class);

    @Resource
    private SchedulerManager    schedulerManager;

    @Resource
    private TaskFireLogMapper   taskFireLogMapper;

    private static final String JOB_LOG = "jobLog";

    @Override
    public String getName() {
        return "taskListener";
    }

    /**
     * 任务开始前
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String targetObject = jobDataMap.getString("targetObject");
        String targetMethod = jobDataMap.getString("targetMethod");
        LogUtil.info(logger, "定时任务开始执行:{0}.{1}", targetObject, targetMethod);

        //保存日志
        TaskFireLog log = new TaskFireLog();
        log.setStartTime(context.getFireTime());
        log.setGroupName(targetObject);
        log.setTaskName(targetMethod);
        log.setStatus(JOBSTATUS.INIT_STATUS);
        log.setServerHost(NativeUtil.getHostName());
        log.setServerDuid(NativeUtil.getDUID());

        if (log.getId() == null) {
            taskFireLogMapper.insert(log);
        } else {
            taskFireLogMapper.updateByPrimaryKeyWithBLOBs(log);
        }

        jobDataMap.put(JOB_LOG, log);
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
    }

    /**
     * 任务结束后
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String targetObject = jobDataMap.getString("targetObject");
        String targetMethod = jobDataMap.getString("targetMethod");
        LogUtil.info(logger, "定时任务执行结束:{0}.{1}", targetObject, targetMethod);

        //更新任务执行状态
        TaskFireLog log = (TaskFireLog) jobDataMap.get(JOB_LOG);
        if (log != null) {
            log.setEndTime(endTime);
            if (jobException != null) {
                LogUtil.error(jobException, logger, "定时任务执行失败:{0}.{1}", targetObject, targetMethod);
                log.setStatus(JOBSTATUS.ERROR_STATUS);
                log.setFireInfo(jobException.getMessage());
            } else {
                if (StringUtils.equals(log.getStatus(), JOBSTATUS.INIT_STATUS)) {
                    log.setStatus(JOBSTATUS.SUCCESS_STATUS);
                }
            }
        }
        taskFireLogMapper.updateByPrimaryKeyWithBLOBs(log);
    }

}
