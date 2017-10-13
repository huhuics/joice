/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * 
 * @author HuHui
 * @version $Id: CacheUtil.java, v 0.1 2017年10月13日 下午2:46:26 HuHui Exp $
 */
public class CacheUtil {

    private static final String SPLIT_STR = "_";

    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return ((String) obj).length() == 0;
        }
        Class cl = obj.getClass();
        if (cl.isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        return false;
    }

    /**
     * 生成字符串的HashCode
     */
    private static int getHashCode(String buf) {
        int hash = 5381;
        int len = buf.length();

        while (len-- > 0) {
            hash = ((hash << 5) + hash) + buf.charAt(len); /* hash * 33 + c */
        }
        return hash;
    }

    /**
     * 将Object对象转换为唯一的Hash字符串
     */
    public static String getUniqueHashStr(Object obj) {
        return getMiscHashCode(BeanUtil.toString(obj));
    }

    /**
     * 通过混合Hash算法,将长字符串转为短字符串(字符串长度小于等于20时不作处理)
     */
    private static String getMiscHashCode(String str) {
        if (isEmpty(str)) {
            return "";
        }
        if (str.length() <= 20) {
            return str;
        }
        StringBuilder tmp = new StringBuilder();
        tmp.append(str.hashCode()).append(SPLIT_STR).append(getHashCode(str));

        int mid = str.length() / 2;
        String str1 = str.substring(0, mid);
        String str2 = str.substring(mid);

        tmp.append(SPLIT_STR).append(str1.hashCode());
        tmp.append(SPLIT_STR).append(str2.hashCode());

        return tmp.toString();
    }
}
