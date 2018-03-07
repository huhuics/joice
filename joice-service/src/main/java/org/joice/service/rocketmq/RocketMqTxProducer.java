/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.rocketmq;

import javax.annotation.Resource;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 事务消息生产者
 * @author HuHui
 * @version $Id: RocketMqTxProducer.java, v 0.1 2017年9月8日 下午2:35:08 HuHui Exp $
 */
public class RocketMqTxProducer {

    private static final Logger          logger = LoggerFactory.getLogger(RocketMqTxProducer.class);

    private TransactionMQProducer        txMQProducer;

    private String                       namesrvAddr;

    private String                       txProducerGroup;

    private int                          checkThreadPoolMinSize;

    private int                          checkThreadPoolMaxSize;

    private int                          checkRequestHoldMax;

    @Resource
    private TransactionCheckListenerImpl transactionCheckListenerImpl;

    public void init() throws MQClientException {
        LogUtil.info(logger, "txMQProducer init!");

        txMQProducer = new TransactionMQProducer(txProducerGroup);

        txMQProducer.setNamesrvAddr(namesrvAddr);
        txMQProducer.setInstanceName(String.valueOf(System.currentTimeMillis()));

        //事务回查最小并发数
        txMQProducer.setCheckThreadPoolMinSize(checkThreadPoolMinSize);
        txMQProducer.setCheckThreadPoolMaxSize(checkThreadPoolMaxSize);

        //队列数
        txMQProducer.setCheckRequestHoldMax(checkRequestHoldMax);

        txMQProducer.setTransactionCheckListener(transactionCheckListenerImpl);

        txMQProducer.start();

        LogUtil.info(logger, "txMQProducer init successful!");
    }

    public void destory() {
        txMQProducer.shutdown();
        LogUtil.info(logger, "txMQProducer shutdown successful!");
    }

    public TransactionMQProducer getTxMQProducer() {
        return txMQProducer;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public void setTxProducerGroup(String txProducerGroup) {
        this.txProducerGroup = txProducerGroup;
    }

    public void setCheckThreadPoolMinSize(int checkThreadPoolMinSize) {
        this.checkThreadPoolMinSize = checkThreadPoolMinSize;
    }

    public void setCheckThreadPoolMaxSize(int checkThreadPoolMaxSize) {
        this.checkThreadPoolMaxSize = checkThreadPoolMaxSize;
    }

    public void setCheckRequestHoldMax(int checkRequestHoldMax) {
        this.checkRequestHoldMax = checkRequestHoldMax;
    }

}
