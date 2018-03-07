/**
 * Joice
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.schedule.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.PersistJobDataAfterExecution;

/**
 * 串行执行任务类
 * @author HuHui
 * @version $Id: NoConcurrentJob.java, v 0.1 2018年1月23日 下午4:11:33 HuHui Exp $
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class NoConcurrentJob extends CommonJob {

}
