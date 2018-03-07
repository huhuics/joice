/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.util;

/**
 * 操作系统类型判断工具类
 * @author HuHui
 * @version $Id: OSUtil.java, v 0.1 2017年10月20日 上午9:27:57 HuHui Exp $
 */
public class OSUtil {

    private static final String os = System.getProperty("os.name").toLowerCase();

    public static boolean isLinux() {
        return os.indexOf("linux") >= 0;
    }

    public static boolean isWindows() {
        return os.indexOf("windows") >= 0;
    }

}
