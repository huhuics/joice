/**
 * 
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rocket MQ生产者
 * @author HuHui
 * @version $Id: RocketMqProducer.java, v 0.1 2017年9月6日 上午9:18:36 HuHui Exp $
 */
public class RocketMqProducer {

    private static final Logger logger = LoggerFactory.getLogger(RocketMqProducer.class);

    private DefaultMQProducer   defaultMQProducer;

    private String              producerGroup;

    private String              nameServerAddr;

    /**
     * 初始化方法
     * @throws MQClientException 
     */
    public void init() throws MQClientException {
        LogUtil.info(logger, "defaultMQProducer init! producerGroup:{0}, nameServerAddr:{1}", producerGroup, nameServerAddr);

        defaultMQProducer = new DefaultMQProducer(producerGroup);
        defaultMQProducer.setNamesrvAddr(nameServerAddr);
        defaultMQProducer.setInstanceName(String.valueOf(System.currentTimeMillis()));

        defaultMQProducer.start();

        LogUtil.info(logger, "defaultMQProducer start successful!");
    }

    public void destory() {
        defaultMQProducer.shutdown();
        LogUtil.info(logger, "defaultMQProducer shutdown successful!");
    }

    public DefaultMQProducer getDefaultMQProducer() {
        return defaultMQProducer;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

    public void setNameServerAddr(String nameServerAddr) {
        this.nameServerAddr = nameServerAddr;
    }

}
