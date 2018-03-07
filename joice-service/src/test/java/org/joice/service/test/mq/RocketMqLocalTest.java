/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.test.mq;

import java.util.Date;
import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.joice.common.util.LogUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author HuHui
 * @version $Id: RocketMqLocalTest.java, v 0.1 2017年9月7日 下午2:20:33 HuHui Exp $
 */
public class RocketMqLocalTest {

    private static final Logger logger = LoggerFactory.getLogger(RocketMqLocalTest.class);

    @Test
    public void testProducer() {
        DefaultMQProducer producer = new DefaultMQProducer("Producer");
        producer.setNamesrvAddr("168.33.131.164:9876");
        try {
            producer.start();
            Message msg = new Message("PushTopic", "push", "1", "Just for push1.".getBytes());
            SendResult result = producer.send(msg);
            LogUtil.info(logger, "sendResult1:{0},{1}", result.getMsgId(), result.getSendStatus());

            msg = new Message("PushTopic", "push", "2", "Just for push2.".getBytes());
            result = producer.send(msg);
            LogUtil.info(logger, "sendResult2:{0},{1}", result.getMsgId(), result.getSendStatus());

            msg = new Message("PushTopic", "pull", "1", "Just for pull.".getBytes());
            result = producer.send(msg);
            LogUtil.info(logger, "sendResult3:{0},{1}", result.getMsgId(), result.getSendStatus());
        } catch (Exception e) {
            LogUtil.error(e, logger, "");
        } finally {
            producer.shutdown();
        }
    }

    @Test
    public void testConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("PushConsumer");
        consumer.setNamesrvAddr("168.33.131.164:9876");
        try {
            consumer.subscribe("PushTopic", "push");
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    for (Message msg : msgs) {
                        LogUtil.info(logger, "msg:{0}", msg);
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        } catch (Exception e) {
            LogUtil.error(e, logger, "");
        }
    }

    @Test
    public void testTimestamp() {
        Date date = new Date(1504767263601L);
        LogUtil.info(logger, "1504767263601:{0}", date);
    }

}
