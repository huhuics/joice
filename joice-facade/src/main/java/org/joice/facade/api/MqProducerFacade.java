/**
 * 深圳金融电子结算中心
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
     * 发送Message
     */
    String send(Message message) throws Exception;

}
