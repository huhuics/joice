/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.service;

import org.joice.common.dao.domain.BizPayOrder;

/**
 * 订单服务接口
 * @author HuHui
 * @version $Id: OrderService.java, v 0.1 2018年1月2日 下午7:32:28 HuHui Exp $
 */
public interface OrderService {

    /**
     * 用户下单
     * @param order 订单实体对象
     * @return      true表示下单成功
     */
    boolean pay(BizPayOrder order);

}
