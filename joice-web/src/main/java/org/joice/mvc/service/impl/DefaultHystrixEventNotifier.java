/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;

/**
 * 
 * @author HuHui
 * @version $Id: DefaultHystrixEventNotifier.java, v 0.1 2018年3月21日 下午6:01:38 HuHui Exp $
 */
public class DefaultHystrixEventNotifier extends HystrixEventNotifier {

    private static final Logger logger = LoggerFactory.getLogger(DefaultHystrixEventNotifier.class);

    @Override
    public void markEvent(HystrixEventType eventType, HystrixCommandKey key) {
    }

    @Override
    public void markCommandExecution(HystrixCommandKey key, ExecutionIsolationStrategy isolationStrategy, int duration, List<HystrixEventType> eventsDuringExecution) {
    }

}
