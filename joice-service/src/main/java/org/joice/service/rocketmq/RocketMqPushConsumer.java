/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.rocketmq;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Push类型消费者
 * @author HuHui
 * @version $Id: RocketMqPushConsumer.java, v 0.1 2017年9月6日 上午9:37:45 HuHui Exp $
 */
public class RocketMqPushConsumer {

    private static final Logger   logger = LoggerFactory.getLogger(RocketMqPushConsumer.class);

    private DefaultMQPushConsumer consumer;

    private String                consumerGroup;

    private String                nameServerAddr;

    private String                topic;

    private String                tag    = "*";

    public void init() throws MQClientException {
        LogUtil.info(logger, "defaultMQPushConsumer init! nameServerAddr={0},topic={1},tag={2}", nameServerAddr, topic, tag);

        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.subscribe(topic, tag);
        consumer.setNamesrvAddr(nameServerAddr);

        //程序第一次启动从消息队列头取数据
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                LogUtil.info(logger, "consume message");
                for (MessageExt msg : msgs) {
                    try {
                        //消费消息
                        LogUtil.info(logger, "message:{0}, content={1}", msg, new String(msg.getBody()));
                    } catch (Exception e) {
                        LogUtil.error(e, logger, "消费消息出错!");
                        if (msg.getReconsumeTimes() <= 3) {//重复消费3次
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
                    //休眠是为等待Spring加载完
                    Thread.sleep(5000);
                    consumer.start();
                    LogUtil.info(logger, "defaultMQPushConsumer start successful!");
                } catch (MQClientException e) {
                    LogUtil.error(e, logger, "defaultMQPushConsumer start failed");
                } catch (InterruptedException e) {
                    LogUtil.error(e, logger, "");
                }
            }
        }).start();

    }

    public void destory() {
        consumer.shutdown();
        LogUtil.info(logger, "defaultMQPushConsumer destoried success");
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public void setNameServerAddr(String nameServerAddr) {
        this.nameServerAddr = nameServerAddr;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
