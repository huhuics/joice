/**
 *
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 交易状态枚举
 * @author HuHui
 * @version $Id: TradeStatusEnmu.java, v 0.1 2017年8月19日 下午5:04:26 HuHui Exp $
 */
public enum TradeStatusEnmu {

    trade_processing("trade_processing", "交易中"),

    trade_success("trade_success", "交易成功"),

    trade_failed("trade_failed", "交易失败"),

    ;

    /** 枚举代码 */
    private String code;

    /** 枚举值 */
    private String desc;

    private TradeStatusEnmu(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**

     * 根据代码获取枚举，如果code对应的枚举不存在，则返回null

     * @param code 枚举代码

     * @return     对应的枚举对象

     */
    public static TradeStatusEnmu getByCode(String code) {
        for (TradeStatusEnmu eachValue : TradeStatusEnmu.values()) {
            if (StringUtils.equals(code, eachValue.getCode())) {
                return eachValue;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
