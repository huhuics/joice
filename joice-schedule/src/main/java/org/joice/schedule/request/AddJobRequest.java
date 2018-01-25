/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.schedule.request;

import org.joice.common.util.AssertUtil;
import org.joice.schedule.enums.JobTypeEnum;

/**
 * 
 * @author HuHui
 * @version $Id: AddJobRequest.java, v 0.1 2018年1月25日 下午2:53:35 HuHui Exp $
 */
public class AddJobRequest extends BaseRequest {

    /**  */
    private static final long serialVersionUID = 45115359789101336L;

    private String            jobName;

    private String            jobGroup;

    private String            triggerName;

    private String            triggerGroup;

    /** cron表达式 */
    private String            cronExpression;

    /** 任务描述 */
    private String            jobDescription;

    /** job执行类的全路径名 */
    private String            jobClassName;

    /** job执行方法的simple name */
    private String            jobMethodName;

    /** job类型 */
    private JobTypeEnum       jobType;

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

    @Override
    public void validate() {

        AssertUtil.assertNotBlank(jobName, "jobName不能为空");

        AssertUtil.assertNotBlank(jobGroup, "jobGroup不能为空");

        AssertUtil.assertNotBlank(triggerName, "triggerName不能为空");

        AssertUtil.assertNotBlank(triggerGroup, "triggerGroup不能为空");

        AssertUtil.assertNotBlank(cronExpression, "cronExpression不能为空");

        AssertUtil.assertNotBlank(jobClassName, "jobClassName不能为空");

        AssertUtil.assertNotBlank(jobMethodName, "jobMethodName不能为空");

        AssertUtil.assertNotNull(jobType, "jobType不能为空");

    }

}
