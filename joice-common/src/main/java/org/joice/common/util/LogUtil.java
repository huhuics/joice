/**
 * Beijing Jiaotong University
 * Copyright (c) 1896-2016 All Rights Reserved.
 */
package org.joice.common.util;

import java.text.MessageFormat;

import org.slf4j.Logger;

/**
 * 日志工具类
 * @author HuHui
 * @version $Id: LogUtil.java, v 0.1 2016年6月1日 下午9:15:08 HuHui Exp $
 */
public class LogUtil {

    /**
     * 输出error level的log信息
     * @param logger   日志记录器
     * @param message  log信息, 如:<code>xxx,xxx...</code>
     */
    public static void error(Logger logger, String message) {
        logger.error(message);
    }

    /**
     * 输出error level的log信息
     * @param logger  日志记录器
     * @param message log信息, 如:<code>xxx,xxx...</code>
     * @param params  log格式化参数,数组length与message参数化个数相同, 如:<code>Object[]  object=new Object[]{"xxx","xxx"}</code>
     */
    public static void error(Logger logger, String message, Object... params) {
        logger.error(format(message, params));
    }

    /**
     * 输出error level的log信息
     * @param throwable  异常对象
     * @param message    log信息
     * @param logger     日志记录器
     */
    public static void error(Throwable throwable, Logger logger, String message) {
        logger.error(message, throwable);
    }

    /**
     * 输出error level的log信息
     * @param throwable 异常对象
     * @param logger    日志记录器
     * @param message   log信息
     * @param params    log格式化参数,数组length与message参数化个数相同, 如:<code>Object[]  object=new Object[]{"xxx","xxx"}</code>
     */
    public static void error(Throwable throwable, Logger logger, String message, Object... params) {
        logger.error(format(message, params), throwable);
    }

    /**
     * 输出warn level的log信息
     * @param logger   日志记录器
     * @param message  log信息
     */
    public static void warn(Logger logger, String message) {
        logger.warn(message);
    }

    /**
     * 输出warn level的log信息
     * @param logger   日志记录器
     * @param message  log信息
     * @param params   log格式化参数,数组length与message参数化个数相同, 如:<code>Object[]  object=new Object[]{"xxx","xxx"}</code>
     */
    public static void warn(Logger logger, String message, Object... params) {
        logger.warn(format(message, params));
    }

    /**
     * 输出info level的log信息
     * @param logger  日志记录器
     * @param message log信息
     */
    public static void info(Logger logger, String message) {
        logger.info(message);
    }

    /**
     * 输出info level的log信息
     * @param logger  日志记录器
     * @param message log信息
     * @param params  log格式化参数,数组length与message参数化个数相同, 如:<code>Object[]  object=new Object[]{"xxx","xxx"}</code>
     */
    public static void info(Logger logger, String message, Object... params) {
        logger.info(format(message, params));
    }

    /**
     * 输出debug level的log信息
     * @param logger  日志记录器
     * @param message log信息
     */
    public static void debug(Logger logger, String message) {
        logger.debug(message);
    }

    /**
     * 输出debug level的log信息
     * @param logger  日志记录器
     * @param message log信息
     * @param params  log格式化参数,数组length与message参数化个数相同, 如:<code>Object[]  object=new Object[]{"xxx","xxx"}</code>
     */
    public static void debug(Logger logger, String message, Object... params) {
        logger.debug(format(message, params));
    }

    /**
     * 日志信息参数格式化
     * @param message log信息,如:<code>xxx{0},xxx{1}...</code>
     * @param params  log格式化参数,数组length与message参数化个数相同, 如:<code>Object[]  object=new Object[]{"xxx","xxx"}</code>
     * @return message 日志
     */
    private static String format(String message, Object... params) {
        if (params != null && params.length != 0) {
            return MessageFormat.format(message, params);
        }
        return message;
    }

}
