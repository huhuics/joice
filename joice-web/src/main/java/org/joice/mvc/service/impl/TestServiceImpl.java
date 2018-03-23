/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.service.impl;

import javax.annotation.Resource;

import org.joice.mvc.service.MerchantService;
import org.joice.mvc.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author HuHui
 * @version $Id: TestServiceImpl.java, v 0.1 2018年3月22日 下午9:01:05 HuHui Exp $
 */
@Service
public class TestServiceImpl implements TestService {

    private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @Resource
    private MerchantService     merchantService;

    @Override
    public void test(int i) {
        merchantService.query(i);
    }

}
