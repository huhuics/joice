/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.controller;

import java.util.Random;

import javax.annotation.Resource;

import org.joice.common.util.LogUtil;
import org.joice.mvc.service.MerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author HuHui
 * @version $Id: HystrixController.java, v 0.1 2018年3月21日 下午6:07:29 HuHui Exp $
 */
@RestController
public class HystrixController {

    private static final Logger logger = LoggerFactory.getLogger(HystrixController.class);

    @Resource
    private MerchantService     merchantService;

    private Random              random = new Random();

    @GetMapping("hystrix/metrics")
    public String metrics(ModelMap map) {
        LogUtil.info(logger, "HystrixController.metrics收到请求");

        merchantService.query(random.nextInt(10));

        LogUtil.info(logger, "HystrixController.metrics返回请求");

        return "HystrixController.metrics";
    }

}
