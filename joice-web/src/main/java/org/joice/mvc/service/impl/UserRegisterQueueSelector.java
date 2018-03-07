/**
 * Joice
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.service.impl;

import java.util.List;

import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.stereotype.Service;

/**
 * 
 * @author HuHui
 * @version $Id: UserRegisterQueueSelector.java, v 0.1 2018年1月11日 下午8:41:34 HuHui Exp $
 */
@Service("userRegisterQueueSelector")
public class UserRegisterQueueSelector implements MessageQueueSelector {

    @Override
    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
        long userId = (long) arg;
        long index = userId % mqs.size();
        return mqs.get((int) index);
    }

}
