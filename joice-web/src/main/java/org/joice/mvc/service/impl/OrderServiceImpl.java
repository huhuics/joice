/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.joice.common.constant.Constants;
import org.joice.common.dao.BizUserAccountMapper;
import org.joice.common.dao.domain.BizPayOrder;
import org.joice.common.dao.domain.BizUserAccount;
import org.joice.common.util.AssertUtil;
import org.joice.common.util.LogUtil;
import org.joice.mvc.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 订单服务接口实现类，用户下单分为两步：
 * <ul>
 *  <li>第一步：扣减用户余额</li>
 *  <li>第二步：插入订单记录</li>
 * </ul>
 * <p>第一步操作由本地执行，第二步由joice-service系统执行。这两步操作必须是同一个事务，
 * 但由于这两个操作分别在两个子系统中完成，此处使用事务消息实现</p>
 * @author HuHui
 * @version $Id: OrderServiceImpl.java, v 0.1 2018年1月2日 下午7:32:45 HuHui Exp $
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger  logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Resource
    private BizUserAccountMapper bizUserAccountMapper;

    @Override
    public boolean pay(BizPayOrder order) {

        LogUtil.info(logger, "收到支付请求,userId={0},amount={1}", order.getBuyerUserId(), order.getTradeAmount());

        //1.查询账户
        BizUserAccount userAccount = bizUserAccountMapper.selectByUserId(order.getBuyerUserId());
        AssertUtil.assertNotNull(userAccount, "查询账户为空");

        //2.扣减用户账户金额
        int ret = bizUserAccountMapper.updateBalance(assembleParaMap(order));

        return ret > 0 ? true : false;
    }

    /**
     * 组装参数Map
     */
    private Map<String, Object> assembleParaMap(BizPayOrder order) {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        //金额转化为负数，表示扣款
        paraMap.put(Constants.MODIDIED_BALANCE, -order.getTradeAmount().getCent());
        paraMap.put(Constants.USER_ID, order.getBuyerUserId());

        return paraMap;
    }
}
