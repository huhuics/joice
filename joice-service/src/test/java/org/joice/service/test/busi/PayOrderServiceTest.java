/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.test.busi;

import javax.annotation.Resource;

import org.joice.common.dao.domain.BizPayOrder;
import org.joice.service.busi.PayOrderService;
import org.joice.service.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试订单服务类
 * @author HuHui
 * @version $Id: PayOrderServiceTest.java, v 0.1 2017年10月25日 下午12:48:48 HuHui Exp $
 */
public class PayOrderServiceTest extends BaseTest {

    @Resource
    private PayOrderService payOrderService;

    @Test
    public void testGetById() {
        Long id = 3L;
        Assert.assertNotNull(payOrderService);
        BizPayOrder order = payOrderService.getById(id);
        Assert.assertNotNull(order);
        Assert.assertTrue(order.getId() == id);
    }

}
