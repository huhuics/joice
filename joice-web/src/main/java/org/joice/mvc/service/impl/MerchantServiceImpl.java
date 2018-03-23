/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.service.impl;

import org.joice.common.util.LogUtil;
import org.joice.mvc.service.MerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * 
 * @author HuHui
 * @version $Id: MerchantServiceImpl.java, v 0.1 2018年3月22日 下午7:00:57 HuHui Exp $
 */
@Service
public class MerchantServiceImpl implements MerchantService {

    private static final Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);

    @Override
    @HystrixCommand(fallbackMethod = "doFallback", //
            commandProperties = { //
                                  @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100") })
    public void query(int id) {
        LogUtil.info(logger, "收到查询商户请求,id={0}", id);

        try {
            Thread.sleep(130);
        } catch (InterruptedException e) {
        }
    }

    public void doFallback(int id) {
        LogUtil.info(logger, "查询商户请求fall back, id={0}", id);
    }

}
