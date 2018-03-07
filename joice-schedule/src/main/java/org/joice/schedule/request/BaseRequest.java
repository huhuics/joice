/**
 * Joice
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.schedule.request;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 基础请求类
 * @author HuHui
 * @version $Id: BaseRequest.java, v 0.1 2018年1月25日 下午2:52:07 HuHui Exp $
 */
public abstract class BaseRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 4442964349712843053L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * 参数校验
     */
    public abstract void validate();

}
