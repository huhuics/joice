/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.web.test.service;

import java.util.Random;

import javax.annotation.Resource;

import org.joice.mvc.service.MerchantService;
import org.joice.web.test.base.BaseTest;
import org.junit.Test;

/**
 * 
 * @author HuHui
 * @version $Id: MerchantServiceTest.java, v 0.1 2018年3月22日 下午7:33:56 HuHui Exp $
 */
public class MerchantServiceTest extends BaseTest {

    @Resource
    private MerchantService merchantService;

    @Test
    public void test() {
        merchantService.query(new Random().nextInt(10));
    }

}
