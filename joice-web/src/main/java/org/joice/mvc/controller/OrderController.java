/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.math.NumberUtils;
import org.joice.common.dto.PayOrderRequest;
import org.joice.common.enums.TradeSceneEnum;
import org.joice.common.util.LogUtil;
import org.joice.mvc.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

/**
 * 用户下单
 * @author HuHui
 * @version $Id: OrderController.java, v 0.1 2018年1月2日 下午5:22:24 HuHui Exp $
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger   = LoggerFactory.getLogger(OrderController.class);

    private static final String ORDER    = "order";

    private static final String SUCC_MSG = "do order success";

    private static final String FAIL_MSG = "do order failed";

    @Resource
    private OrderService        orderService;

    @RequestMapping(value = "/toOrder", method = RequestMethod.GET)
    public String toOrder(WebRequest request, ModelMap map) {
        map.put("buyerUserId", "2088001");
        map.put("merchantId", "3066001");
        map.put("tradeNo", geneRandomId());
        map.put("tradeAmount", "93.6");
        map.put("goodsDetail", "华为mate10 pro 128g");

        return ORDER;
    }

    @ResponseBody
    @RequestMapping(value = "/doOrder", method = RequestMethod.POST)
    public String doOrder(WebRequest webRequest, ModelMap map) {
        try {
            PayOrderRequest orderRequest = orderRequestResolver(webRequest);
            orderService.pay(orderRequest);
        } catch (Exception e) {
            LogUtil.error(e, logger, FAIL_MSG);
            return FAIL_MSG;
        }

        return SUCC_MSG;
    }

    /**
     * 组装订单请求对象
     * @param webRequest
     * @return
     */
    private PayOrderRequest orderRequestResolver(WebRequest webRequest) {
        PayOrderRequest orderRequest = new PayOrderRequest();
        orderRequest.setBuyerUserId(webRequest.getParameter("buyerUserId"));
        orderRequest.setMerchantId(webRequest.getParameter("merchantId"));
        orderRequest.setTradeNo(webRequest.getParameter("tradeNo"));
        orderRequest.setTradeAmount(NumberUtils.toDouble(webRequest.getParameter("tradeAmount")));
        orderRequest.setGoodsDetail(webRequest.getParameter("goodsDetail"));
        orderRequest.setScene(TradeSceneEnum.bar_code.getCode());

        return orderRequest;
    }

    /**
     * 随机生成id
     * @return
     */
    private String geneRandomId() {
        return (System.currentTimeMillis() + (long) (Math.random() * 10000000L)) + "";
    }

}
