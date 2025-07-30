/**
 * 
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.common.message.MessageExt;
import org.joice.common.constant.Constants;
import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 回查账户修改事务是否成功执行
 * @author HuHui
 * @version $Id: AccountTxCheckListener.java, v 0.1 2018年1月3日 上午10:59:25 HuHui Exp $
 */
@Service("accountTxCheckListener")
public class AccountTxCheckListener implements TransactionCheckListener {

    private static final Logger logger = LoggerFactory.getLogger(AccountTxCheckListener.class);

    @Override
    public LocalTransactionState checkLocalTransactionState(MessageExt msgExt) {

        LogUtil.info(logger, "收到本地事务回查请求");

        String isTxSuccess = msgExt.getProperty(Constants.IS_LOCAL_TX_SUCCESS);

        LogUtil.info(logger, "msgExt={0}", msgExt);

        if (StringUtils.equals(isTxSuccess, Constants.TX_SUCCESS)) {

            LogUtil.info(logger, "本地事务回查结果:提交成功");

            return LocalTransactionState.COMMIT_MESSAGE;
        }

        LogUtil.info(logger, "本地事务回查结果:事务回滚");

        return LocalTransactionState.ROLLBACK_MESSAGE;
    }

}
