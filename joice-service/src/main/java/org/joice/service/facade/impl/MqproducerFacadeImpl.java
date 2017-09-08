/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.facade.impl;

import javax.annotation.Resource;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.joice.facade.api.MqProducerFacade;
import org.joice.service.rocketmq.RocketMqProducer;
import org.joice.service.rocketmq.RocketMqTxProducer;
import org.springframework.stereotype.Service;

/**
 * MqProducerFacade实现类
 * @author HuHui
 * @version $Id: MqproducerFacadeImpl.java, v 0.1 2017年9月6日 上午11:30:01 HuHui Exp $
 */
@Service("mqProducerFacade")
public class MqproducerFacadeImpl implements MqProducerFacade {

    @Resource
    private RocketMqProducer   rocketMqProducer;

    @Resource
    private RocketMqTxProducer rocketMqTxProducer;

    @Override
    public String send(Message message) throws Exception {
        SendResult result = rocketMqProducer.getDefaultMQProducer().send(message);
        return result.toString();
    }

    @Override
    public String sendInTx(Message message) throws Exception {
        //TODO
        SendResult result = rocketMqTxProducer.getTxMQProducer().sendMessageInTransaction(message, null, null);
        return result.toString();
    }

    @Override
    public void shutdown() {
        rocketMqProducer.getDefaultMQProducer().shutdown();
    }

}
