package org.joice.common.dao.domain;

import java.util.Date;

import org.joice.common.base.BaseDomain;

public class TaskFireLog extends BaseDomain {

    /**  */
    private static final long serialVersionUID = -482971254242432805L;

    private Long              id;

    private String            groupName;

    private String            taskName;

    private Date              startTime;

    private Date              endTime;

    private String            status;

    private String            serverHost;

    private String            serverDuid;

    private String            fireInfo;

    /**
     * 日志表状态
     */
    public interface JOBSTATUS {
        /**
         * 日志表状态，初始状态，插入
         */
        static final String INIT_STATUS    = "I";
        /**
         * 日志表状态，成功
         */
        static final String SUCCESS_STATUS = "S";
        /**
         * 日志表状态，失败
         */
        static final String ERROR_STATUS   = "E";
        /**
         * 日志表状态，未执行
         */
        static final String UN_STATUS      = "N";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost == null ? null : serverHost.trim();
    }

    public String getServerDuid() {
        return serverDuid;
    }

    public void setServerDuid(String serverDuid) {
        this.serverDuid = serverDuid == null ? null : serverDuid.trim();
    }

    public String getFireInfo() {
        return fireInfo;
    }

    public void setFireInfo(String fireInfo) {
        this.fireInfo = fireInfo == null ? null : fireInfo.trim();
    }
}