/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.support.scheduler;

import java.util.Date;

import org.joice.common.base.BaseDomain;

/**
 * 计划任务信息
 * @author HuHui
 * @version $Id: TaskSchedule.java, v 0.1 2017年8月24日 下午7:47:59 HuHui Exp $
 */
public class TaskSchedule extends BaseDomain {

    /**  */
    private static final long serialVersionUID = 5318406412725223032L;

    /** 任务名称 */
    private String            taskName;

    /** 任务分组 */
    private String            taskGroup;

    /** 任务状态：0禁用 1启用 2删除 */
    private String            status;

    /** 任务运行时间表达式 */
    private String            taskCron;

    /** 最后一次执行时间 */
    private Date              previousFireTime;

    /** 下次执行时间 */
    private Date              nextFireTime;

    /** 任务描述 */
    private String            taskDesc;

    /** 任务类型(是否阻塞) */
    private String            jobType;

    /** 本地任务/dubbo任务 */
    private String            taskType;

    /** 运行系统(dubbo任务必填) */
    private String            targetSystem;

    /** 任务对象 */
    private String            targetObject;

    /** 任务方法 */
    private String            targetMethod;

    /** 通知邮箱联系人 */
    private String            contactName;

    /** 通知邮箱地址 */
    private String            contactEmail;

    public interface JobType {

        /** 普通任务 */
        String job         = "job";

        /** 阻塞任务 */
        String statefulJob = "statefulJob";
    }

    public interface TaskType {

        /** 本地任务 */
        String local = "LOCAL";

        /** dubbo任务 */
        String dubbo = "DUBBO";
    }

    public TaskSchedule() {

    }

    public TaskSchedule(String taskName, String taskGroup) {
        this.taskName = taskName;
        this.taskGroup = taskGroup;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(String taskGroup) {
        this.taskGroup = taskGroup;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskCron() {
        return taskCron;
    }

    public void setTaskCron(String taskCron) {
        this.taskCron = taskCron;
    }

    public Date getPreviousFireTime() {
        return previousFireTime;
    }

    public void setPreviousFireTime(Date previousFireTime) {
        this.previousFireTime = previousFireTime;
    }

    public Date getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTargetSystem() {
        return targetSystem;
    }

    public void setTargetSystem(String targetSystem) {
        this.targetSystem = targetSystem;
    }

    public String getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(String targetObject) {
        this.targetObject = targetObject;
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

}
