/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.support.scheduler.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.PersistJobDataAfterExecution;

/**
 * 阻塞调度
 * @author HuHui
 * @version $Id: StatefulJob.java, v 0.1 2017年8月25日 上午10:17:38 HuHui Exp $
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class StatefulJob extends DefaultJob implements Job {

}
