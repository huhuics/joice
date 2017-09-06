/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.test.mq;

import javax.annotation.Resource;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.joice.common.util.LogUtil;
import org.joice.service.rocketmq.RocketMqProducer;
import org.joice.service.test.BaseTest;
import org.junit.Test;

/**
 * 测试RocketMQ
 * @author HuHui
 * @version $Id: RocketMqTest.java, v 0.1 2017年9月6日 上午10:51:24 HuHui Exp $
 */
public class RocketMqTest extends BaseTest {

    @Resource
    private RocketMqProducer rocketMqProducer;

    @Test
    public void testPush() throws Exception {
        String topic = "joice-ms";
        String tag = "orderMsg";
        String content = "这个一条测试订单消息";

        Message msg = new Message(topic, tag, String.valueOf(System.currentTimeMillis()), content.getBytes());
        SendResult sendResult = rocketMqProducer.getDefaultMQProducer().send(msg);

        LogUtil.info(logger, "id:{0}, sendStatus{1}", sendResult.getMsgId(), sendResult.getSendStatus());
    }
}
