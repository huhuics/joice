/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.test.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * @author HuHui
 * @version $Id: SayHello.java, v 0.1 2017年8月21日 下午9:20:39 HuHui Exp $
 */
public class SayHello implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("hello quartz! " + new Date() + " by " + context.getTrigger().getNextFireTime());
    }

}
