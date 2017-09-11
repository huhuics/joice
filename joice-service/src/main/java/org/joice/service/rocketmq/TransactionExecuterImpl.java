/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.rocketmq;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;
import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 事务执行器
 * @author HuHui
 * @version $Id: TransactionExecuterImpl.java, v 0.1 2017年9月11日 下午5:25:29 HuHui Exp $
 */
@Service
public class TransactionExecuterImpl implements LocalTransactionExecuter {

    private static final Logger logger           = LoggerFactory.getLogger(TransactionExecuterImpl.class);

    private AtomicInteger       transactionIndex = new AtomicInteger(1);

    @Override
    public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {
        int value = transactionIndex.incrementAndGet();

        LogUtil.info(logger, "TransactionExecuterImpl, value:{0}", value);

        if (value == 0) {
            throw new RuntimeException("Could not find DB");
        } else if ((value % 5) == 0) {
            return LocalTransactionState.ROLLBACK_MESSAGE;
        } else if ((value % 4) == 0) {
            return LocalTransactionState.COMMIT_MESSAGE;
        }

        return LocalTransactionState.UNKNOW;
    }
}
