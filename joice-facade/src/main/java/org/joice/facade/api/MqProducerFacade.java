/**
 *
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.facade.api;

import org.apache.rocketmq.common.message.Message;

/**
 * MQ门面
 * @author HuHui
 * @version $Id: MqProducerFacade.java, v 0.1 2017年9月6日 上午11:25:13 HuHui Exp $
 */
public interface MqProducerFacade {

    /**
     * 发送普通Message
     */
    String send(Message message) throws Exception;

    /**
     * 发送事务消息
     */
    String sendInTx(Message message) throws Exception;

    /**
     * 发送顺序消息
     */
    String sendInOrder(Message message, int orderId) throws Exception;

    /**
     * 关闭producer
     */
    void shutdown();

}
