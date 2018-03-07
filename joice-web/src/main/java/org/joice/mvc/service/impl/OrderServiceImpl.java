/**
 * Joice
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.service.impl;

import javax.annotation.Resource;

import org.apache.rocketmq.client.exception.MQClientException;
import org.joice.common.dao.BizUserAccountMapper;
import org.joice.common.dao.domain.BizUserAccount;
import org.joice.common.dto.PayOrderRequest;
import org.joice.common.util.AssertUtil;
import org.joice.common.util.LogUtil;
import org.joice.mvc.service.OrderCreationMqProducerService;
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

    private static final Logger            logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Resource
    private BizUserAccountMapper           bizUserAccountMapper;

    @Resource
    private OrderCreationMqProducerService orderCreationMqProducerService;

    @Override
    public boolean pay(PayOrderRequest orderRequest) {

        LogUtil.info(logger, "收到支付请求,userId={0},amount={1}", orderRequest.getBuyerUserId(), orderRequest.getTradeAmount());

        //1.查询账户
        BizUserAccount userAccount = bizUserAccountMapper.selectByUserId(orderRequest.getBuyerUserId());
        AssertUtil.assertNotNull(userAccount, "查询账户为空");

        //2.发送事务消息创建订单
        try {
            return orderCreationMqProducerService.process(orderRequest);
        } catch (MQClientException e) {
            throw new RuntimeException("事务消息发送失败", e);
        }
    }

}
