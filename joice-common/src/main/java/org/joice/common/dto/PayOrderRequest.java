/**
 * Joice
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.common.dto;

import org.joice.common.base.BaseDomain;

/**
 * 
 * @author HuHui
 * @version $Id: PayOrderRequest.java, v 0.1 2018年1月3日 下午7:54:46 HuHui Exp $
 */
public class PayOrderRequest extends BaseDomain {

    /**  */
    private static final long serialVersionUID = -8899828991971786012L;

    private String            buyerUserId;

    private String            merchantId;

    private String            tradeNo;

    private double            tradeAmount;

    private String            scene;

    private String            goodsDetail;

    private String            notifyUrl;

    public String getBuyerUserId() {
        return buyerUserId;
    }

    public void setBuyerUserId(String buyerUserId) {
        this.buyerUserId = buyerUserId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public double getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(double tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

}
