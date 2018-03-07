/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.test.dao;

import javax.annotation.Resource;

import org.joice.common.dao.BizPayOrderMapper;
import org.joice.common.dao.domain.BizPayOrder;
import org.joice.common.enums.TradeSceneEnum;
import org.joice.common.enums.TradeStatusEnmu;
import org.joice.common.util.LogUtil;
import org.joice.common.util.Money;
import org.joice.service.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;

import com.alibaba.druid.filter.config.ConfigTools;

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

        record.setBuyerUserId("6");
        record.setMerchantId("006");
        record.setTradeNo("tradeNo" + geneRandomId());
        record.setTradeAmount(new Money(12.3));
        record.setTradeStatus(TradeStatusEnmu.trade_success.getCode());
        record.setScene(TradeSceneEnum.bar_code.getCode());
        record.setGoodsDetail("mate9 64G");

        int ret = bizPayOrderMapper.insert(record);

        Assert.assertTrue(ret == 1);
    }

    @Test
    public void testQueryById() {
        Long id = 3L;
        BizPayOrder order = bizPayOrderMapper.selectByPrimaryKey(id);
        LogUtil.info(logger, "order : {0}", order);
        Assert.assertNotNull(order);
        Assert.assertTrue(order.getId() == id);
    }

    @Test
    public void testEncrypt() throws Exception {
        String password = "huhui";
        String[] arr = ConfigTools.genKeyPair(512);
        LogUtil.info(logger, "password:{0}", ConfigTools.encrypt(password));
    }

}
