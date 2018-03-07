/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.aspect;

/**
 * 
 * @author HuHui
 * @version $Id: HandleDataSource.java, v 0.1 2017年9月3日 下午5:03:14 HuHui Exp $
 */
public class HandleDataSource {

    /** 数据源名称 */
    private static final ThreadLocal<String> dataSourceHolder = new ThreadLocal<String>();

    public static void putDataSource(String dataSource) {
        dataSourceHolder.set(dataSource);
    }

    public static String getDataSource() {
        return dataSourceHolder.get();
    }

    public static void clear() {
        dataSourceHolder.remove();
    }

}
