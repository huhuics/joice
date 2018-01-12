/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.service.busi.impl;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.joice.common.util.LogUtil;
import org.joice.service.busi.UserRegisterAccountConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 接收joice-web的消息，消费account相关的消息
 * @author HuHui
 * @version $Id: UserRegisterAccountConsumerImpl.java, v 0.1 2018年1月12日 上午10:02:44 HuHui Exp $
 */
public class UserRegisterAccountConsumerImpl implements UserRegisterAccountConsumer {

    private static final Logger   logger = LoggerFactory.getLogger(UserRegisterAccountConsumerImpl.class);

    /** 消息消费者 */
    private DefaultMQPushConsumer consumer;

    private String                consumerGroup;

    private String                namesrvAddr;

    private String                topic;

    private String                accountTag;

    public void init() throws MQClientException {
        LogUtil.info(logger, "AccountConsumer init! topic={0}, accountTag={1}", topic, accountTag);
        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.subscribe(topic, accountTag);
        consumer.setNamesrvAddr(namesrvAddr);

        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                for (MessageExt msg : msgs) {
                    LogUtil.info(logger, "account consume msg:{0}", new String(msg.getBody()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //休眠是为了等Spring加载完
                    Thread.sleep(5000);
                    consumer.start();
                    LogUtil.info(logger, "UserRegisterAccountConsumer启动成功");
                } catch (Exception e) {
                    LogUtil.error(e, logger, "UserRegisterAccountConsumer启动失败");
                }
            }
        }).start();

    }

    public DefaultMQPushConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(DefaultMQPushConsumer consumer) {
        this.consumer = consumer;
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

    public String getAccountTag() {
        return accountTag;
    }

    public void setAccountTag(String accountTag) {
        this.accountTag = accountTag;
    }

}
