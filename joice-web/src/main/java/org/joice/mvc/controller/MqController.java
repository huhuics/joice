/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.mvc.controller;

import javax.annotation.Resource;

import org.apache.rocketmq.common.message.Message;
import org.joice.common.util.LogUtil;
import org.joice.facade.api.MqProducerFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MQ Controller
 * @author HuHui
 * @version $Id: MqController.java, v 0.1 2017年9月6日 上午11:32:58 HuHui Exp $
 */
@RestController
public class MqController {

    private static final Logger logger = LoggerFactory.getLogger(MqController.class);

    @Resource
    private MqProducerFacade    mqProducerFacade;

    @GetMapping("mq/produce")
    public String produce(ModelMap modelMap) throws Exception {
        LogUtil.info(logger, "收到发送Message 请求");
        Message msg = createMessage();
        String sendResult = mqProducerFacade.send(msg);
        LogUtil.info(logger, "sendResult:{0}", sendResult);

        return sendResult;
    }

    private Message createMessage() {
        String topic = "joice-ms";
        String tag = "orderMsg";
        String content = "这个一条测试订单消息";

        Message msg = new Message(topic, tag, String.valueOf(System.currentTimeMillis()), content.getBytes());

        return msg;
    }

}
