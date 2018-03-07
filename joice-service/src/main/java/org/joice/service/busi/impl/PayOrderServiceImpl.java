/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.busi.impl;

import javax.annotation.Resource;

import org.joice.cache.annotation.CacheDel;
import org.joice.cache.annotation.CacheDelItem;
import org.joice.cache.annotation.Cacheable;
import org.joice.common.dao.BizPayOrderMapper;
import org.joice.common.dao.domain.BizPayOrder;
import org.joice.common.util.LogUtil;
import org.joice.service.busi.PayOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 订单服务接口实现类
 * @author HuHui
 * @version $Id: PayOrderServiceImpl.java, v 0.1 2017年10月25日 下午12:45:17 HuHui Exp $
 */
@Service
public class PayOrderServiceImpl implements PayOrderService {

    private static final Logger logger = LoggerFactory.getLogger(PayOrderService.class);

    @Resource
    private BizPayOrderMapper   bizPayOrderMapper;

    @Override
    @Cacheable(sync = true)
    public BizPayOrder getById(Long id) {
        LogUtil.info(logger, "收到订单查询请求,id={0}", id);
        BizPayOrder order = bizPayOrderMapper.selectByPrimaryKey(id);
        LogUtil.info(logger, "订单查询结果,order={0}", order);
        return order;
    }

    @Override
    @Cacheable(key = "'payOrderService_getById_'+#args[0].id", condition = "#args[0].id>3")
    public BizPayOrder getById(BizPayOrder order) {
        Long id = order.getId();
        BizPayOrder ret = bizPayOrderMapper.selectByPrimaryKey(id);
        LogUtil.info(logger, "订单查询结果,order={0}", ret);
        return ret;
    }

    @Override
    @CacheDel(items = { @CacheDelItem(key = "'payOrderService_getById_'+#args[0].id") }, condition = "#retVal == true")
    public boolean updateOrder(BizPayOrder order) {
        return bizPayOrderMapper.updateByPrimaryKey(order) == 1;
    }

    @Override
    public int insert(BizPayOrder order) {

        LogUtil.info(logger, "收到订单插入请求,order={0}", order);

        return bizPayOrderMapper.insertSelective(order);
    }
}
