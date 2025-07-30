/**
 * 
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.aspect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 数据源选择类
 * @author HuHui
 * @version $Id: ChooseDataSource.java, v 0.1 2017年9月3日 下午5:00:55 HuHui Exp $
 */
public class ChooseDataSource extends AbstractRoutingDataSource {

    /** 存放方法类别. key是数据源类别,value是方法集合 */
    public static Map<String, List<String>> METHODTYPE = new HashMap<String, List<String>>();

    /**
     * 获取数据源名称
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return HandleDataSource.getDataSource();
    }

    /**
     * 通过set方法注入map实例
     * 设置方法名前缀对应的数据源
     */
    public void setMethodType(Map<String, String> map) {
        for (String key : map.keySet()) {
            List<String> v = new ArrayList<String>();
            String[] types = map.get(key).split(",");
            for (String type : types) {
                if (StringUtils.isNotBlank(type)) {
                    v.add(type);
                }
            }
            METHODTYPE.put(key, v);
        }
    }

}
