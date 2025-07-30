/**
 * 
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.rocketmq;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.common.message.MessageExt;
import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 当RocketMQ发现prepared消息时，会根据这个Listener实现的策略来决定是否提交消息发送请求
 * @author HuHui
 * @version $Id: TransactionCheckListenerImpl.java, v 0.1 2017年9月11日 下午6:09:45 HuHui Exp $
 */
@Service
public class TransactionCheckListenerImpl implements TransactionCheckListener {

    private static final Logger logger           = LoggerFactory.getLogger(TransactionCheckListenerImpl.class);

    private AtomicInteger       transactionIndex = new AtomicInteger(0);

    @Override
    public LocalTransactionState checkLocalTransactionState(MessageExt msg) {

        LogUtil.info(logger, "server checking TrMsg: {0}", msg.toString());

        int value = transactionIndex.getAndIncrement();
        if ((value % 6) == 0) {
            throw new RuntimeException("Counld not find DB");
        } else if ((value % 5) == 0) {
            return LocalTransactionState.ROLLBACK_MESSAGE;
        } else if ((value % 4) == 0) {
            return LocalTransactionState.COMMIT_MESSAGE;
        }

        return LocalTransactionState.UNKNOW;
    }

}
