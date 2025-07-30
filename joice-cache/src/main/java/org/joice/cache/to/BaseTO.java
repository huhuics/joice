/**
 * 
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.to;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 基础TO
 * @author HuHui
 * @version $Id: BaseTO.java, v 0.1 2017年10月18日 下午4:46:36 HuHui Exp $
 */
public class BaseTO implements Serializable {

    /**  */
    private static final long serialVersionUID = -126413746522220746L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
