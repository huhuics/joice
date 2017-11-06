package com.sunveee.joice.cache.util;

import org.apache.commons.lang3.StringUtils;
import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunveee.joice.cache.model.Key;

public class KeyGenetrator {

    private static final Logger logger           = LoggerFactory.getLogger(KeyGenetrator.class);

    private static final String DOUBLE_UNDERLINE = "__";

    /**
     * 创建Key<br>
     * 对某方法的调用以"类全限定名__方法名__所选参数值"的形式创建keyStr
     * 
     * @param classCanonicalName 全限定类名
     * @param methodName 方法名
     * @param args 方法传入参数
     * @param argRange
     * @return
     */
    public static Key generateKey(String classCanonicalName, String methodName, Object[] args, int[] argRange) {
        StringBuilder keyStr = new StringBuilder();

        keyStr.append(classCanonicalName);
        keyStr.append(DOUBLE_UNDERLINE);
        keyStr.append(methodName);

        if (null != argRange && argRange.length > 0) {
            try {

                for (int argIndex : argRange) {
                    keyStr.append(DOUBLE_UNDERLINE);
                    keyStr.append(args[argIndex]);
                }

            } catch (Exception e) {
                LogUtil.error(e, logger, "使用参数创建key时发生异常:keyStr={0},argRange={1}", keyStr, StringUtils.join(argRange, ','));
                throw new RuntimeException("使用参数创建key时发生异常");
            }
        }
        return new Key(keyStr.toString());

    }

}
