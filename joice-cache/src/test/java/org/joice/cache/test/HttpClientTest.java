/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.joice.common.util.LogUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author HuHui
 * @version $Id: HttpClientTest.java, v 0.1 2017年11月14日 上午10:16:34 HuHui Exp $
 */
public class HttpClientTest {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientTest.class);

    @Test
    public void testSCustomerRiskSend() throws Exception {
        String acuUrl = "http://168.33.131.53:8080/mPay/customerRisk/receive";
        Map<String, String> paraMap = new TreeMap<String, String>();
        paraMap.put(CustomerRiskConstant.CHARSET, "GBK");
        paraMap.put(CustomerRiskConstant.EXTERNALID, "1088001000215105621");
        paraMap.put(CustomerRiskConstant.PID, "2088421726612692");
        paraMap.put(CustomerRiskConstant.RISKLEVEL, "商户欺诈基本成立");
        paraMap.put(CustomerRiskConstant.RISKTYPE, "欺诈");
        paraMap.put(CustomerRiskConstant.SERVICE, "alipay.adatabus.risk.end.push");
        paraMap
            .put(
                CustomerRiskConstant.SIGN,
                "n/4ZcDsF15/juj0DIncv3LHVxgd1TwkvlYZP3kuDqqvOyJqWBkmeXrVi8xh+DKjblG2A418R2u+chU42XbDdSv5wk9OSFt6m7qbO82xvBO6Xjk+iLKzU+MxP92TjA4T+hjQC2eUu8Rmfl6Ehoj+f8BTfwFVXmJgok5MckcUORa4=");
        paraMap.put(CustomerRiskConstant.SIGN_TYPE, "RSA");
        paraMap.put(CustomerRiskConstant.SMID, "2088821753413150");
        paraMap.put(CustomerRiskConstant.SOURCE_ID, "2088421726612692");
        paraMap.put(CustomerRiskConstant.TRADE_NOS, "out_trade_no_1510563703502");

        List<NameValuePair> paraList = buildPostParaList(paraMap);

        String response = httpClientPost(acuUrl, paraList);

        LogUtil.info(logger, "response={0}", response);

    }

    /**
     * 将Map中的参数转换成NamValuePair对，并封装成List
     */
    private List<NameValuePair> buildPostParaList(Map<String, String> paraMap) {

        List<NameValuePair> pairList = new ArrayList<NameValuePair>(paraMap.size());

        for (String key : paraMap.keySet()) {
            NameValuePair nvPair = new NameValuePair(key, paraMap.get(key));
            pairList.add(nvPair);
        }

        return pairList;
    }

    private static String httpClientPost(String url, List<NameValuePair> params) {
        String result = "";
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        postMethod.getParams().setContentCharset("utf-8");
        try {
            NameValuePair[] pair = new NameValuePair[params.size()];
            for (int i = 0; i < params.size(); i++) {
                pair[i] = params.get(i);
            }
            postMethod.addParameters(pair);
            client.executeMethod(postMethod);
            result = postMethod.getResponseBodyAsString();
        } catch (Exception e) {
            LogUtil.error(e, logger, "Http调用发送异常, url={0}", url);
        } finally {
            postMethod.releaseConnection();
        }

        return result;
    }

}
