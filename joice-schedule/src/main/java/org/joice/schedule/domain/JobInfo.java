/**
 * Joice
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.schedule.domain;

import java.util.List;

import org.joice.common.base.BaseDomain;
import org.joice.schedule.enums.JobTypeEnum;
import org.quartz.Trigger;

/**
 * 封装Job相关信息
 * @author HuHui
 * @version $Id: JobInfo.java, v 0.1 2018年1月23日 下午3:32:10 HuHui Exp $
 */
public class JobInfo extends BaseDomain {

    /**  */
    private static final long       serialVersionUID = 624985773641778019L;

    private String                  schedulerName;

    private String                  jobName;

    private String                  jobGroup;

    private String                  triggerName;

    private String                  triggerGroup;

    /** Trigger s that are associated with the job */
    private List<? extends Trigger> triggers;

    /** cron表达式 */
    private String                  cronExpression;

    /** 任务描述 */
    private String                  jobDescription;

    /** job执行类的全路径名 */
    private String                  jobClassName;

    /** job执行方法的simple name */
    private String                  jobMethodName;

    /** job类型 */
    private JobTypeEnum             jobType;

    public String getSchedulerName() {
        return schedulerName;
    }

    public void setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getJobMethodName() {
        return jobMethodName;
    }

    public void setJobMethodName(String jobMethodName) {
        this.jobMethodName = jobMethodName;
    }

    public JobTypeEnum getJobType() {
        return jobType;
    }

    public void setJobType(JobTypeEnum jobType) {
        this.jobType = jobType;
    }

    public List<? extends Trigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<? extends Trigger> triggers) {
        this.triggers = triggers;
    }

}
