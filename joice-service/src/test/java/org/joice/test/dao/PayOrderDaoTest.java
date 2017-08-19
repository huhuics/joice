/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.test.dao;

import javax.annotation.Resource;

import org.joice.common.dao.BizPayOrderMapper;
import org.joice.common.dao.domain.BizPayOrder;
import org.joice.common.enums.TradeSceneEnum;
import org.joice.common.enums.TradeStatusEnmu;
import org.joice.common.util.Money;
import org.joice.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试BizPayOrderMapper
 * @author HuHui
 * @version $Id: PayOrderDaoTest.java, v 0.1 2017年8月19日 下午4:54:59 HuHui Exp $
 */
public class PayOrderDaoTest extends BaseTest {

    @Resource
    private BizPayOrderMapper bizPayOrderMapper;

    @Test
    public void testInsert() {
        BizPayOrder record = new BizPayOrder();

        record.setBuyerUserId("1");
        record.setMerchantId("001");
        record.setTradeNo("0001");
        record.setTradeAmount(new Money(12.3));
        record.setTradeStatus(TradeStatusEnmu.trade_success.getCode());
        record.setScene(TradeSceneEnum.bar_code.getCode());
        record.setGoodsDetail("mate9 64G");

        int ret = bizPayOrderMapper.insert(record);

        Assert.assertTrue(ret == 1);
    }

}
