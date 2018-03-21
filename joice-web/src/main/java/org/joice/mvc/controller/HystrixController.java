/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.controller;

import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * 
 * @author HuHui
 * @version $Id: HystrixController.java, v 0.1 2018年3月21日 下午6:07:29 HuHui Exp $
 */
@RestController
public class HystrixController {

    private static final Logger logger = LoggerFactory.getLogger(HystrixController.class);

    @GetMapping("hystrix/metrics")
    @HystrixCommand(groupKey = "hystrix.metrics", //
            commandKey = "metrics", //
            fallbackMethod = "metricsFallback", //
            commandProperties = { //
                                  @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "400"), //指定多久超时，单位毫秒。超时进fallback
                                  @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //判断熔断的最少请求数，默认是10；只有在一个统计窗口内处理的请求数量达到这个阈值，才会进行熔断与否的判断
                                  @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10") }) //判断熔断的阈值，默认值50，表示在一个统计窗口内有50%的请求处理失败，会触发熔断)
    public String metrics(ModelMap map) {
        LogUtil.info(logger, "HystrixController.metrics收到请求");

        throw new RuntimeException("模拟抛出一个异常");

        //        return "HystrixController.metrics";
    }

    public void metricsFallback() {
        LogUtil.info(logger, "metrics处理失败,进入fall back");
    }

}
