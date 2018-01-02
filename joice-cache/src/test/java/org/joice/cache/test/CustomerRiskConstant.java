package org.joice.cache.test;

public class CustomerRiskConstant {

    /**********************************接收支付宝商户风险交易推送通知字段名**************************************/
    /** 出口网关服务名 */
    public static final String SERVICE       = "service";

    /** 签名方式 */
    public static final String SIGN_TYPE     = "sign_type";

    /** 签名值 */
    public static final String SIGN          = "sign";

    /** 编码格式*/
    public static final String CHARSET       = "charset";

    /** 涉嫌风险的商户识别号 */
    public static final String SMID          = "smid";

    /** 中心pid */
    public static final String PID           = "pid";

    /** 涉嫌风险的中心商户外部编号 */
    public static final String EXTERNALID    = "externalId";

    /** 商户来源标识 */
    public static final String SOURCE_ID     = "sourceId";

    /** 涉嫌商户被投诉的外部交易号样例 */
    public static final String TRADE_NOS     = "tradeNos";

    /** 风险类型(目前只涉及欺诈) */
    public static final String RISKTYPE      = "risktype";

    /** 风险情况描述 */
    public static final String RISKLEVEL     = "risklevel";

    /** 商户风险交易推送至收单机构地址 */
    public static final String ACQ_RISK_URL  = "acqRiskURL";

    /**********************************发送至收单机构商户风险交易推送通知字段名**************************************/
    /** 收单机构识别码 */
    public static final String ACQUIRER_ID   = "acquirer_id";

    /** 付款渠道类型 */
    public static final String WALLET_TYPE   = "wallet_type";

    /** 涉嫌风险的子商户代码 */
    public static final String SUB_MER_ID    = "sub_mer_id";

    /** 涉嫌风险的商户外部编号*/
    public static final String EXTERNAL_ID   = "external_id";

    /** 子商户对应的收单机构PID */
    public static final String SOURCE        = "source";

    /** 涉嫌商户被投诉的商户订单号样例 */
    public static final String MER_TRADE_NOS = "mer_trade_nos";

    /** 涉嫌商户被投诉的商户订单号样例 */
    public static final String RISK_TYPE     = "risk_type";

    /** 风险情况描述 */
    public static final String RISK_LEVEL    = "risk_level";

    /** 备注 */
    public static final String REMARK        = "remark";

}
