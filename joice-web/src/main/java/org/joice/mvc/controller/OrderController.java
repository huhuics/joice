/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.controller;

import javax.annotation.Resource;

import org.joice.common.dao.domain.BizPayOrder;
import org.joice.common.util.LogUtil;
import org.joice.common.util.Money;
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
    public String doOrder(WebRequest request, ModelMap map) {
        try {
            BizPayOrder order = orderResolver(request);
            orderService.pay(order);
        } catch (Exception e) {
            LogUtil.error(e, logger, FAIL_MSG);
            return FAIL_MSG;
        }

        return SUCC_MSG;
    }

    /**
     * 组装订单实体对象
     * @param request
     * @return
     */
    private BizPayOrder orderResolver(WebRequest request) {
        BizPayOrder order = new BizPayOrder();
        order.setBuyerUserId(request.getParameter("buyerUserId"));
        order.setMerchantId(request.getParameter("merchantId"));
        order.setTradeNo(request.getParameter("tradeNo"));
        order.setTradeAmount(new Money(request.getParameter("tradeAmount")));
        order.setGoodsDetail(request.getParameter("goodsDetail"));

        return order;
    }

    /**
     * 随机生成id
     * @return
     */
    private String geneRandomId() {
        return (System.currentTimeMillis() + (long) (Math.random() * 10000000L)) + "";
    }

}
