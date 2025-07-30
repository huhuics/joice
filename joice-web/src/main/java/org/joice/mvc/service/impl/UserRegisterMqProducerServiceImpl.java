/**
 * 
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.service.impl;

import javax.annotation.Resource;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.joice.common.util.LogUtil;
import org.joice.mvc.service.UserRegisterMqProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户注册消息发送接口实现类
 * <p>用户注册向两个系统发送消息：1.邮件系统  2.账户系统</p>
 * @author HuHui
 * @version $Id: UserRegisterMqProducerServiceImpl.java, v 0.1 2018年1月11日 下午8:01:28 HuHui Exp $
 */
public class UserRegisterMqProducerServiceImpl implements UserRegisterMqProducerService {

    private static final Logger  logger = LoggerFactory.getLogger(UserRegisterMqProducerServiceImpl.class);

    private DefaultMQProducer    defaultMQProducer;

    @Resource(name = "userRegisterQueueSelector")
    private MessageQueueSelector queueSelector;

    private String               namesrvAddr;

    private String               producerGroup;

    private String               topic;

    private String               emailTag;

    private String               accountTag;

    public void init() throws MQClientException {

        LogUtil.info(logger, "defaultMQProducer init,producerGroup={0}", producerGroup);

        defaultMQProducer = new DefaultMQProducer(producerGroup);
        defaultMQProducer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQProducer.setNamesrvAddr(namesrvAddr);

        defaultMQProducer.start();

        LogUtil.info(logger, "defaultMQProducer start successful!");

    }

    public void destory() {
        defaultMQProducer.shutdown();
        LogUtil.info(logger, "defaultMQProducer shutdown successful!");
    }

    @Override
    public void process(long userId) throws Exception {

        defaultMQProducer.send(createEmailMessage(userId), queueSelector, userId);
        LogUtil.info(logger, "发送email消息成功,userId={0}", userId);

        defaultMQProducer.send(createAccountMessage(userId), queueSelector, userId);
        LogUtil.info(logger, "发送account消息成功,userId={0}", userId);

    }

    private Message createEmailMessage(long userId) {
        return new Message(topic, emailTag, ("create email, userId: " + String.valueOf(userId)).getBytes());
    }

    private Message createAccountMessage(long userId) {
        return new Message(topic, accountTag, ("create account, userId: " + String.valueOf(userId)).getBytes());
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getProducerGroup() {
        return producerGroup;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getEmailTag() {
        return emailTag;
    }

    public void setEmailTag(String emailTag) {
        this.emailTag = emailTag;
    }

    public String getAccountTag() {
        return accountTag;
    }

    public void setAccountTag(String accountTag) {
        this.accountTag = accountTag;
    }

}
