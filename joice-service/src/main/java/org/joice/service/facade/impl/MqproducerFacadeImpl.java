/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.facade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.joice.facade.api.MqProducerFacade;
import org.joice.service.rocketmq.RocketMqProducer;
import org.joice.service.rocketmq.RocketMqTxProducer;
import org.joice.service.rocketmq.TransactionExecuterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * MqProducerFacade实现类
 * @author HuHui
 * @version $Id: MqproducerFacadeImpl.java, v 0.1 2017年9月6日 上午11:30:01 HuHui Exp $
 */
@Service("mqProducerFacade")
public class MqproducerFacadeImpl implements MqProducerFacade {

    private static final Logger     logger = LoggerFactory.getLogger(MqproducerFacadeImpl.class);

    @Resource
    private RocketMqProducer        rocketMqProducer;

    @Resource
    private RocketMqTxProducer      rocketMqTxProducer;

    @Resource
    private TransactionExecuterImpl transactionExecuterImpl;

    @Override
    public String send(Message message) throws Exception {
        SendResult result = rocketMqProducer.getDefaultMQProducer().send(message);
        return result.toString();
    }

    @Override
    public String sendInTx(Message message) throws Exception {
        SendResult result = rocketMqTxProducer.getTxMQProducer().sendMessageInTransaction(message, transactionExecuterImpl, "MqproducerFacadeImpl.sendInTx");
        return result.toString();
    }

    @Override
    public String sendInOrder(Message message, int orderId) throws Exception {
        //RocketMQ通过MessageQueueSelector中实现的算法来确定消息发送到哪一个队列上
        //RocketMQ默认提供了两种MessageQueueSelector实现：随机/Hash
        //此处根据业务实现自己的MessageQueueSelector：订单号相同的消息会被先后发送到通过一个队列中
        SendResult result = rocketMqProducer.getDefaultMQProducer().send(message, new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                int id = (int) arg;
                int index = id % mqs.size();
                return mqs.get(index);
            }
        }, orderId);
        return result.toString();
    }

    @Override
    public void shutdown() {
        rocketMqProducer.getDefaultMQProducer().shutdown();
    }

}
