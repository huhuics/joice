/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author HuHui
 * @version $Id: OsUtil.java, v 0.1 2017年9月29日 上午10:52:16 HuHui Exp $
 */
public class OsUtil {

    private static OsUtil  instance = new OsUtil();

    private static boolean isLinux;

    static {
        String os = System.getProperty("os.name");
        if (StringUtils.isNotEmpty(os) && (os.toUpperCase().indexOf("LINUX ") > -1)) {
            isLinux = true;
        } else {
            isLinux = false;
        }
    }

    public static OsUtil getInstance() {
        return instance;
    }

    public boolean isLinux() {
        return isLinux;
    }

}
