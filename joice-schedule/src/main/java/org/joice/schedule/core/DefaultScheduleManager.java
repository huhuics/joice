/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.schedule.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.joice.common.util.LogUtil;
import org.joice.schedule.constant.Constant;
import org.joice.schedule.domain.JobInfo;
import org.joice.schedule.enums.JobTypeEnum;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>ScheduleManager</code>实现类
 * <p>提供对调度任务的增、删、改、查接口</p>
 * @author HuHui
 * @version $Id: DefaultScheduleManager.java, v 0.1 2018年1月23日 上午10:49:31 HuHui Exp $
 */
public class DefaultScheduleManager implements ScheduleManager {

    private static final Logger logger = LoggerFactory.getLogger(DefaultScheduleManager.class);

    /** quartz任务器 */
    private Scheduler           scheduler;

    /** job监听器 */
    private List<JobListener>   jobListeners;

    /**
     * 初始化方法
     */
    public void init() {
        LogUtil.info(logger, "ScheduleManager init...");

        for (JobListener jobListener : jobListeners) {
            try {
                scheduler.getListenerManager().addJobListener(jobListener);
            } catch (SchedulerException e) {
                throw new RuntimeException("添加任务监听器异常", e);
            }
        }

        LogUtil.info(logger, "ScheduleManager init success...");
    }

    @Override
    public List<JobInfo> getAllJobDetail() {
        LogUtil.info(logger, "收到查询所有定时任务请求");
        List<JobInfo> jobDetails = new ArrayList<JobInfo>();
        try {
            Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.anyJobGroup());
            for (JobKey jobKey : jobKeys) {
                JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(jobKey);
                //组装JobInfo
                JobInfo jobInfo = new JobInfo();
                jobInfo.setSchedulerName(jobInfo.getSchedulerName());
                jobInfo.setJobName(jobDetail.getName());
                jobInfo.setJobGroup(jobDetail.getGroup());
                jobInfo.setTriggers(scheduler.getTriggersOfJob(jobKey));
                jobInfo.setJobDescription(jobDetail.getDescription());
                jobInfo.setJobClassName(jobDetail.getJobDataMap().getString(Constant.JOB_CLASS_NAME));
                jobInfo.setJobMethodName(jobDetail.getJobDataMap().getString(Constant.JOB_METHOD_NAME));
                jobInfo.setJobType(JobTypeEnum.valueOf(jobDetail.getJobDataMap().getString(Constant.JOB_TYPE)));

                jobDetails.add(jobInfo);
            }
        } catch (SchedulerException e) {
            throw new RuntimeException("查询所有任务失败", e);
        }

        LogUtil.info(logger, "所有定时任务查询结果:{0}", jobDetails.size());

        return jobDetails;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public List<JobListener> getJobListeners() {
        return jobListeners;
    }

    public void setJobListeners(List<JobListener> jobListeners) {
        this.jobListeners = jobListeners;
    }

}
