/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.web.test.service;

import javax.annotation.Resource;

import org.joice.mvc.service.TestService;
import org.joice.web.test.base.BaseTest;
import org.junit.Test;

/**
 * 
 * @author HuHui
 * @version $Id: TestService.java, v 0.1 2018年3月22日 下午9:02:22 HuHui Exp $
 */
public class MyTestService extends BaseTest {

    @Resource
    private TestService testService;

    @Test
    public void test() {
        testService.test(1);
    }

}
