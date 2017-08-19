/**
 * Beijing Jiaotong University
 * Copyright (c) 1896-2016 All Rights Reserved.
 */
package org.joice.common.util;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 断言工具类
 * @author HuHui
 * @version $Id: AssertUtil.java, v 0.1 2016年5月21日 下午8:49:15 HuHui Exp $
 */
public class AssertUtil {

    private static final String DEFAULT_ERR_MESSAGE = "对象为null";

    /**
     * 断言表达式的值为true，否则抛MechException
     * 
     * @param expValue 断言表达式
     * @param errMsg 异常描述
     */
    public static void assertTrue(boolean expValue, String errMsg) {

        if (!expValue) {
            throw new RuntimeException(errMsg);
        }
    }

    /**
     * 断言表达式的值为false，否则抛RuntimeException
     * 
     * @param expValue 断言表达式
     * @param errMsg 异常描述
     */
    public static void assertFalse(boolean expValue, String errMsg) {

        if (expValue) {

            throw new RuntimeException(errMsg);
        }
    }

    /**
     * 断言两个对象相等，否则抛RuntimeException
     * 
     * @param obj1
     * @param obj2
     * @param errMsg 异常描述
     */
    public static void assertEquals(Object obj1, Object obj2, String errMsg) {

        if (obj1 == null) {

            assertNull(obj2, errMsg);
            return;
        }

        if (!obj1.equals(obj2)) {

            throw new RuntimeException(errMsg);
        }
    }

    /**
     * 断言两个对象不等，否则抛RuntimeException
     * 
     * @param obj1
     * @param obj2
     * @param errMsg 异常描述
     */
    public static void assertNotEquals(Object obj1, Object obj2, String errMsg) {

        if (obj1 == null) {

            assertNotNull(obj2, errMsg);
            return;
        }

        if (obj1.equals(obj2)) {

            throw new RuntimeException(errMsg);
        }
    }

    /**
     * 断言对象至少等于容器中的一个
     * 
     * @param obj1
     * @param objects
     * @param errMsg 异常描述
     */
    public static void assertEqualsAny(Object obj1, Object[] objects, String errMsg) {

        if (null == objects) {

            throw new RuntimeException(errMsg);
        }

        for (Object obj2 : objects) {

            if (obj1 == null) {

                if (obj2 == null) {
                    return;
                }

                continue;
            }

            if (obj1.equals(obj2)) {

                return;
            }
        }

        throw new RuntimeException(errMsg);
    }

    /**
     * 断言两个对象相同，否则抛RuntimeException
     * 
     * @param obj1
     * @param obj2
     * @param errMsg 异常描述
     */
    public static void assertIs(Object obj1, Object obj2, String errMsg) {

        if (obj1 != obj2) {

            throw new RuntimeException(errMsg);
        }
    }

    /**
     * 断言两个对象不同，否则抛RuntimeException
     * 
     * @param obj1
     * @param obj2
     * @param errMsg 异常描述
     */
    public static void assertIsNot(Object obj1, Object obj2, String errMsg) {

        if (obj1 == obj2) {

            throw new RuntimeException(errMsg);
        }
    }

    /**
     * 断言对象在容器中
     * 
     * @param obj1
     * @param objs
     * @param errMsg 异常描述
     */
    public static void assertIn(Object obj1, Object[] objs, String errMsg) {

        if (null == objs) {

            throw new RuntimeException(errMsg);
        }

        for (Object obj : objs) {

            if (obj == obj1) {

                return;
            }
        }

        throw new RuntimeException(errMsg);
    }

    /**
     * 断言对象为空，否则抛RuntimeException
     * 
     * @param str 断言字符串
     * @param errMsg 异常描述
     */
    public static void assertEmpty(String str, String errMsg) {

        if (StringUtils.isNotEmpty(str)) {

            throw new RuntimeException(errMsg);
        }
    }

    /**
     * 断言对象为非空，否则抛RuntimeException
     * 
     * @param str 断言字符串
     * @param errMsg 异常描述
     */
    public static void assertNotEmpty(String str, String errMsg) {

        if (StringUtils.isEmpty(str)) {

            throw new RuntimeException(errMsg);
        }
    }

    /**
     * 断言对象为空，否则抛RuntimeException
     * 
     * @param str 断言字符串
     * @param errMsg 异常描述
     */
    public static void assertBlank(String str, String errMsg) {

        if (StringUtils.isNotBlank(str)) {

            throw new RuntimeException(errMsg);
        }
    }

    /**
     * 断言对象为非空，否则抛RuntimeException
     * 
     * @param str 断言字符串
     * @param errMsg 异常描述
     */
    public static void assertNotBlank(String str, String errMsg) {

        if (StringUtils.isBlank(str)) {

            throw new RuntimeException(errMsg);
        }
    }

    /**
     * 断言对象为null，否则抛RuntimeException
     * 
     * @param object 断言对象
     * @param errMsg 异常描述
     */
    public static void assertNull(Object object, String errMsg) {

        if (object != null) {

            throw new RuntimeException(errMsg);
        }
    }

    /**
     * 断言对象非null，否则抛RuntimeException
     * 
     * @param object 断言对象
     * @param errMsg 异常描述
     */
    public static void assertNotNull(Object object, String errMsg) {

        if (null == object) {

            throw new RuntimeException(errMsg);
        }
    }

    /**
     * 断言对象非null，否则抛RuntimeException
     * 
     * @param object 断言对象
     */
    public static void assertNotNull(Object object) {

        if (null == object) {

            throw new RuntimeException(DEFAULT_ERR_MESSAGE);
        }
    }

    /**
     * 断言集合不为空或null，否则抛RuntimeException
     * 
     * @param collection 断言集合
     * @param errMsg 异常描述
     */
    @SuppressWarnings("rawtypes")
    public static void assertNotBlank(Collection collection, String errMsg) {

        if (CollectionUtils.isEmpty(collection)) {
            throw new RuntimeException(errMsg);
        }
    }
}
