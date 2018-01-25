/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.schedule.request;

import org.joice.common.util.AssertUtil;

/**
 * 任务删除请求
 * @author HuHui
 * @version $Id: RemoveJobRequest.java, v 0.1 2018年1月25日 下午4:22:51 HuHui Exp $
 */
public class RemoveJobRequest extends BaseRequest {

    /**  */
    private static final long serialVersionUID = 3726649566232314739L;

    private String            jobName;

    private String            jobGroup;

    public RemoveJobRequest() {
    }

    public RemoveJobRequest(String jobName, String jobGroup) {
        this.jobName = jobName;
        this.jobGroup = jobGroup;
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

    @Override
    public void validate() {

        AssertUtil.assertNotEmpty(jobName, "jobName不能为空");

        AssertUtil.assertNotEmpty(jobGroup, "jobName不能为空");

    }

}
