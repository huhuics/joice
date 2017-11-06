package com.sunveee.joice.cache.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 存储Model基类
 * 
 * @author 51
 * @version $Id: BaseModel.java, v 0.1 2017年10月30日 下午2:29:15 51 Exp $
 */
public class BaseModel implements Serializable {
    /**  */
    private static final long serialVersionUID = -2603536268628564761L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
