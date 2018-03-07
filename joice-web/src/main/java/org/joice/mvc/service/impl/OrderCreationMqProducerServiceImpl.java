/**
 * Joice
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.service.impl;

import javax.annotation.Resource;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.joice.common.dto.PayOrderRequest;
import org.joice.common.util.LogUtil;
import org.joice.mvc.service.OrderCreationMqProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 创建订单消息生产者接口实现类
 * <p>向joice-service工程发送事务消息创建订单</p>
 * @author HuHui
 * @version $Id: OrderCreationMqProducerServiceImpl.java, v 0.1 2018年1月3日 上午10:43:42 HuHui Exp $
 */
public class OrderCreationMqProducerServiceImpl implements OrderCreationMqProducerService {

    private static final Logger      logger = LoggerFactory.getLogger(OrderCreationMqProducerServiceImpl.class);

    /** 事务消息生产者 */
    private TransactionMQProducer    txMQProducer;

    /** 事务回查监听器 */
    @Resource(name = "accountTxCheckListener")
    private TransactionCheckListener checkListener;

    /** 本地事务执行器 */
    @Resource(name = "accountUpdateTxExecuter")
    private LocalTransactionExecuter localTxExecuter;

    private String                   namesrvAddr;

    private String                   txProducerGroup;

    private String                   topic;

    private String                   tag;

    public void init() throws MQClientException {
        LogUtil.info(logger, "joice-web order creation txMQProducer init, namesrvAddr:{0}, txProducerGroup:{1}", namesrvAddr, txProducerGroup);

        txMQProducer = new TransactionMQProducer(txProducerGroup);
        txMQProducer.setNamesrvAddr(namesrvAddr);
        txMQProducer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        txMQProducer.setTransactionCheckListener(checkListener);

        txMQProducer.start();

        LogUtil.info(logger, "joice-web order creation txMQProducer init successful!");
    }

    @Override
    public boolean process(PayOrderRequest orderRequest) throws MQClientException {
        LogUtil.info(logger, "收到发送创建订单消息请求,orderRequest={0}", orderRequest);

        Message msg = createMessage(orderRequest);
        TransactionSendResult sendRet = txMQProducer.sendMessageInTransaction(msg, localTxExecuter, orderRequest);

        LogUtil.info(logger, "事务消息发送完成,sendRet={0}", sendRet);

        return sendRet.getSendStatus() == SendStatus.SEND_OK ? true : false;

    }

    public void destory() {
        txMQProducer.shutdown();
        LogUtil.info(logger, "joice-web order creation txMQProducer shutdown successful!");
    }

    private Message createMessage(PayOrderRequest orderRequest) {
        String orderJson = JSON.toJSONString(orderRequest);
        String key = String.valueOf(System.currentTimeMillis());
        Message msg = new Message(topic, tag, key, orderJson.getBytes());

        return msg;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getTxProducerGroup() {
        return txProducerGroup;
    }

    public void setTxProducerGroup(String txProducerGroup) {
        this.txProducerGroup = txProducerGroup;
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
