/**
 * 
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.quartz.job;

import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 简单的任务
 * @author HuHui
 * @version $Id: SimpleJob.java, v 0.1 2017年8月24日 下午3:45:58 HuHui Exp $
 */
@Component("simpleJob")
public class SimpleJob {

    private static final Logger logger = LoggerFactory.getLogger(SimpleJob.class);

    public void print() {
        LogUtil.info(logger, "log from SimpleJob");
    }

}
