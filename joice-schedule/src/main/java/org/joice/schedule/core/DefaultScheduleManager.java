/**
 * Joice
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
import org.joice.schedule.job.CommonJob;
import org.joice.schedule.job.NoConcurrentJob;
import org.joice.schedule.request.AddJobRequest;
import org.joice.schedule.request.RemoveJobRequest;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
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

    @Override
    public boolean addJob(AddJobRequest request) {

        LogUtil.info(logger, "收到增加任务请求");

        request.validate();

        JobTypeEnum jobTypeEnum = request.getJobType();

        JobDetail jobDetail = JobBuilder.newJob(getJobTypeClass(jobTypeEnum)).withIdentity(request.getJobName(), request.getJobGroup())
            .usingJobData(assembleDataMap(request)).withDescription(request.getJobDescription()).build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(request.getTriggerName(), request.getTriggerGroup())
            .withSchedule(CronScheduleBuilder.cronSchedule(request.getCronExpression())).startNow().build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            throw new RuntimeException("增加任务失败", e);
        }

        return true;
    }

    @Override
    public boolean removeJob(RemoveJobRequest request) {

        LogUtil.info(logger, "收到删除任务请求");

        request.validate();

        JobKey jobKey = new JobKey(request.getJobName(), request.getJobGroup());
        try {
            return scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException("删除任务失败", e);
        }
    }

    private Class<? extends Job> getJobTypeClass(JobTypeEnum jobTypeEnum) {
        if (jobTypeEnum == JobTypeEnum.COMMON_JOB) {
            return CommonJob.class;
        } else if (jobTypeEnum == JobTypeEnum.NO_CONCURRENT_JOB) {
            return NoConcurrentJob.class;
        }

        throw new RuntimeException("无此任务类型:" + jobTypeEnum);
    }

    private JobDataMap assembleDataMap(AddJobRequest request) {
        JobDataMap map = new JobDataMap();

        map.put(Constant.JOB_CLASS_NAME, request.getJobClassName());
        map.put(Constant.JOB_METHOD_NAME, request.getJobMethodName());
        map.put(Constant.JOB_TYPE, request.getJobType().toString());

        return map;
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
