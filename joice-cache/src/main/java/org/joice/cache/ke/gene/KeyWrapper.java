/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.ke.gene;

import org.joice.cache.to.BaseTO;

/**
 * key封装类
 * @author HuHui
 * @version $Id: KeyWrapper.java, v 0.1 2017年10月26日 下午3:28:24 HuHui Exp $
 */
public class KeyWrapper extends BaseTO {

    /**  */
    private static final long serialVersionUID = -230289853255492429L;

    /** 目标类名 */
    private String            targetName;

    /** 方法名 */
    private String            methodName;

    /** 方法参数 */
    private Object            params[];

    public KeyWrapper() {
    }

    public KeyWrapper(String targetName, String methodName, Object[] params) {
        this.targetName = targetName;
        this.methodName = methodName;
        this.params = params;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

}
