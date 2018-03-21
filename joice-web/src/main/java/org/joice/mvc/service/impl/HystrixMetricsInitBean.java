/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.service.impl;

import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.hystrix.contrib.servopublisher.HystrixServoMetricsPublisher;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifierDefault;

/**
 * 
 * @author HuHui
 * @version $Id: HystrixMetricsInitBean.java, v 0.1 2018年3月21日 下午5:53:41 HuHui Exp $
 */
public class HystrixMetricsInitBean {

    private static final Logger logger = LoggerFactory.getLogger(HystrixMetricsInitBean.class);

    public void init() {
        LogUtil.info(logger, "HystrixMetrics init...");

        HystrixPlugins.getInstance().registerEventNotifier(HystrixEventNotifierDefault.getInstance());

        HystrixPlugins.getInstance().registerMetricsPublisher(HystrixServoMetricsPublisher.getInstance());
    }

}
