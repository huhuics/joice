/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.service.busi.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.joice.common.dao.domain.BizPayOrder;
import org.joice.common.dto.PayOrderRequest;
import org.joice.common.enums.TradeStatusEnmu;
import org.joice.common.util.LogUtil;
import org.joice.common.util.Money;
import org.joice.service.busi.PayOrderMqConsumer;
import org.joice.service.busi.PayOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.json.JSON;

/**
 * 订单消息消费者接口实现类
 * <p>接收来自joice-web的消息，持久化一条订单记录</p>
 * @author HuHui
 * @version $Id: PayOrderMqConsumerImpl.java, v 0.1 2018年1月3日 下午2:33:33 HuHui Exp $
 */
public class PayOrderMqConsumerImpl implements PayOrderMqConsumer {

    private static final Logger   logger = LoggerFactory.getLogger(PayOrderMqConsumerImpl.class);

    /** 消息消费者 */
    private DefaultMQPushConsumer consumer;

    /** 订单服务接口 */
    @Resource
    private PayOrderService       payOrderService;

    private String                consumerGroup;

    private String                namesrvAddr;

    private String                topic;

    private String                tag;

    public void init() throws MQClientException {
        LogUtil.info(logger, "PayOrderMqConsumer init! namesrvAddr={0}, topic={1}, tag={2}", namesrvAddr, topic, tag);
        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.subscribe(topic, tag);
        consumer.setNamesrvAddr(namesrvAddr);

        //程序首次启动从消息队列头读数据
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                LogUtil.info(logger, "PayOrderMqConsumer开始消费");
                for (MessageExt msg : msgs) {
                    try {
                        PayOrderRequest orderRequest = JSON.parse(new String(msg.getBody()), PayOrderRequest.class);

                        BizPayOrder order = convert2Domain(orderRequest);

                        payOrderService.insert(order);
                        LogUtil.info(logger, "消息消费成功,order={0}", order);
                    } catch (Exception e) {
                        LogUtil.error(e, logger, "消息消费出错,msg={0}", msg);
                        if (msg.getReconsumeTimes() <= 3) {
                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        } else {
                            //TODO 消息消费失败
                        }
                    }
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //休眠是为了等Spring加载完
                    Thread.sleep(5000);
                    consumer.start();
                    LogUtil.info(logger, "PayOrderMqConsumer启动成功");
                } catch (Exception e) {
                    LogUtil.error(e, logger, "PayOrderMqConsumer启动失败");
                }
            }
        }).start();

    }

    private BizPayOrder convert2Domain(PayOrderRequest orderRequest) {
        BizPayOrder order = new BizPayOrder();
        order.setBuyerUserId(orderRequest.getBuyerUserId());
        order.setMerchantId(orderRequest.getMerchantId());
        order.setTradeNo(orderRequest.getTradeNo());
        order.setTradeAmount(new Money(orderRequest.getTradeAmount()));
        order.setTradeStatus(TradeStatusEnmu.trade_success.getCode());
        order.setScene(orderRequest.getScene());
        order.setGoodsDetail(orderRequest.getGoodsDetail());
        order.setNotifyUrl(orderRequest.getNotifyUrl());

        return order;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
