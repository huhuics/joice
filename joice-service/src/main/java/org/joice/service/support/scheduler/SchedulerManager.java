/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.support.scheduler;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.joice.common.util.LogUtil;
import org.joice.service.support.scheduler.TaskSchedule.JobType;
import org.joice.service.support.scheduler.TaskSchedule.TaskType;
import org.joice.service.support.scheduler.job.BaseJob;
import org.joice.service.support.scheduler.job.StatefulJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * 默认的定时任务管理器
 * 实现InitializingBean接口为了初始化bean
 * @author HuHui
 * @version $Id: SchedulerManager.java, v 0.1 2017年8月24日 下午7:27:41 HuHui Exp $
 */
public class SchedulerManager implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerManager.class);

    private Scheduler           scheduler;

    private List<JobListener>   jobListeners;

    /**
     * Bean初始化时会调用该方法
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (jobListeners != null && jobListeners.size() > 0) {
            LogUtil.info(logger, "初始化scheduler:{0},增加listener个数:{1}", scheduler.getSchedulerName(), jobListeners.size());
        }
        for (JobListener listener : jobListeners) {
            LogUtil.info(logger, "Add JobListener : ", listener.getName());
            scheduler.getListenerManager().addJobListener(listener);
        }
    }

    public List<TaskSchedule> getAllJobDetail() {
        List<TaskSchedule> result = new LinkedList<TaskSchedule>();
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.jobGroupContains("");
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeys) {
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    TaskSchedule job = new TaskSchedule(jobKey.getName(), jobKey.getGroup());
                    TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    job.setStatus(triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        job.setTaskCron(cronExpression);
                    }

                    job.setPreviousFireTime(trigger.getPreviousFireTime());
                    job.setNextFireTime(trigger.getNextFireTime());

                    JobDataMap jobDataMap = trigger.getJobDataMap();

                    job.setTaskType(jobDataMap.getString("taskType"));
                    job.setTargetSystem(jobDataMap.getString("targetSystem"));
                    job.setTargetObject(jobDataMap.getString("targetObject"));
                    job.setTargetMethod(jobDataMap.getString("targetMethod"));
                    job.setContactName(jobDataMap.getString("contactName"));
                    job.setContactEmail(jobDataMap.getString("contactEmail"));

                    job.setTaskDesc(jobDetail.getDescription());
                    String jobClass = jobDetail.getJobClass().getSimpleName();
                    if (StringUtils.equals(jobClass, "StatefulJob")) {
                        job.setJobType(TaskSchedule.JobType.statefulJob);
                    } else if (StringUtils.equals(jobClass, "BaseJob")) {
                        job.setJobType(TaskSchedule.JobType.job);
                    }

                    result.add(job);
                }
            }
        } catch (Exception e) {
            LogUtil.error(e, logger, "加载所有JobDetail时发生异常");
        }

        return result;
    }

    /**
     * 增加任务
     */
    public boolean addTask(TaskSchedule taskSchedule) {
        String jobGroup = taskSchedule.getTaskGroup();
        if (StringUtils.isEmpty(jobGroup)) {
            jobGroup = "ds_job";
        }
        String jobName = taskSchedule.getTaskName();
        if (StringUtils.isEmpty(jobName)) {
            jobName = String.valueOf(System.currentTimeMillis());
        }
        String cronExpression = taskSchedule.getTaskCron();
        String targetObject = taskSchedule.getTargetObject();
        String targetMethod = taskSchedule.getTargetMethod();
        String jobDescription = taskSchedule.getTaskDesc();
        String jobType = taskSchedule.getJobType();
        String taskType = taskSchedule.getTaskType();

        JobDataMap jobDataMap = new JobDataMap();
        if (StringUtils.equals(TaskType.dubbo, taskType)) {
            jobDataMap.put("targetSystem", taskSchedule.getTargetSystem());
        }
        jobDataMap.put("targetObject", targetObject);
        jobDataMap.put("targetMethod", targetMethod);
        jobDataMap.put("taskType", taskType);
        jobDataMap.put("contactName", taskSchedule.getContactName());
        jobDataMap.put("contactEmail", taskSchedule.getContactEmail());

        JobBuilder jobBuilder = null;
        if (StringUtils.equals(JobType.job, jobType)) {
            jobBuilder = JobBuilder.newJob(BaseJob.class);
        } else if (StringUtils.equals(JobType.statefulJob, jobType)) {
            jobBuilder = JobBuilder.newJob(StatefulJob.class);
        }
        if (jobBuilder != null) {
            JobDetail jobDetail = jobBuilder.withIdentity(jobName, jobGroup).withDescription(jobDescription).storeDurably(true).usingJobData(jobDataMap)
                .build();

            Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).withIdentity(jobName, jobGroup)
                .withDescription(jobDescription).forJob(jobDetail).usingJobData(jobDataMap).build();

            try {
                JobDetail detail = scheduler.getJobDetail(new JobKey(jobName, jobGroup));
                if (detail == null) {
                    scheduler.scheduleJob(jobDetail, trigger);
                } else {
                    scheduler.addJob(jobDetail, true);
                    scheduler.rescheduleJob(new TriggerKey(jobName, jobGroup), trigger);
                }
                return true;
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }

        return false;
    }

    /**
     * 通过触发器名称获取与之关联的JobDetail
     */
    public JobDetail getJobDetailByTriggerName(Trigger trigger) {
        try {
            return scheduler.getJobDetail(trigger.getJobKey());
        } catch (Exception e) {
            LogUtil.error(e, logger, "获取JobDetail失败");
        }
        return null;
    }

    /**
     * 暂停所有触发器
     */
    public void pauseAllTrigger() {
        try {
            scheduler.standby();
        } catch (Exception e) {
            throw new RuntimeException("暂停所有触发器失败", e);
        }
    }

    /**
     * 启动所有触发器
     */
    public void startAllTrigger() {
        try {
            if (scheduler.isInStandbyMode()) {
                scheduler.start();
            }
        } catch (Exception e) {
            throw new RuntimeException("启动所有触发器失败", e);
        }
    }

    /**
     * 暂停任务
     */
    public void stopJob(TaskSchedule scheduleJob) {
        try {
            JobKey jobKey = JobKey.jobKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
            scheduler.pauseJob(jobKey);
        } catch (Exception e) {
            throw new RuntimeException("暂停任务失败", e);
        }
    }

    /**
     * 恢复启动任务
     */
    public void resumeJob(TaskSchedule scheduleJob) {
        try {
            JobKey jobKey = JobKey.jobKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
            scheduler.resumeJob(jobKey);
        } catch (Exception e) {
            throw new RuntimeException("恢复启动任务失败", e);
        }
    }

    /**
     * 执行任务
     */
    public void runJob(TaskSchedule scheduleJob) {
        try {
            JobKey jobKey = JobKey.jobKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
            scheduler.triggerJob(jobKey);
        } catch (Exception e) {
            throw new RuntimeException("执行任务失败", e);
        }
    }

    /**
     * 删除任务
     */
    public void delJob(TaskSchedule scheduleJob) {
        try {
            JobKey jobKey = JobKey.jobKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
            TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
            scheduler.pauseTrigger(triggerKey); //停止触发器
            scheduler.unscheduleJob(triggerKey); //移除触发器
            scheduler.deleteJob(jobKey); //删除任务
        } catch (Exception e) {
            throw new RuntimeException("删除任务异常", e);
        }
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void setJobListeners(List<JobListener> jobListeners) {
        this.jobListeners = jobListeners;
    }

}
