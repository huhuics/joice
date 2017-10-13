/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.script;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.joice.cache.util.CacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Spring EL表达式解析处理
 * @author HuHui
 * @version $Id: SpringELParser.java, v 0.1 2017年10月13日 下午2:59:42 HuHui Exp $
 */
public class SpringELParser extends AbstractScriptParser {

    private static final Logger           logger   = LoggerFactory.getLogger(SpringELParser.class);

    private final ExpressionParser        parser   = new SpelExpressionParser();

    private final Map<String, Expression> expCache = new ConcurrentHashMap<String, Expression>();

    private final Map<String, Method>     funcs    = new ConcurrentHashMap<String, Method>();

    private static Method                 hash     = null;

    private static Method                 empty    = null;

    static {
        try {
            hash = CacheUtil.class.getDeclaredMethod("getUniqueHashStr", new Class[] { Object.class });
            empty = CacheUtil.class.getDeclaredMethod("isEmpty", new Class[] { Object.class });
        } catch (NoSuchMethodException e) {
            logger.error("", e);
        } catch (SecurityException e) {
            logger.error("", e);
        }
    }

    @Override
    public void addFunction(String name, Method method) {
        funcs.put(name, method);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getELValue(String keyEL, Object[] arguments, Object retVal, boolean hasRetVal, Class<T> valueType) throws Exception {
        if (valueType.equals(String.class)) {
            if (keyEL.indexOf("#") == -1 && keyEL.indexOf("'") == -1) {//如果不是表达式则直接返回字符串
                return (T) keyEL;
            }
        }

        StandardEvaluationContext context = new StandardEvaluationContext();

        context.registerFunction(HASH, hash);
        context.registerFunction(EMPTY, empty);

        Iterator<Entry<String, Method>> it = funcs.entrySet().iterator();

        while (it.hasNext()) {
            Entry<String, Method> entry = it.next();
            context.registerFunction(entry.getKey(), entry.getValue());
        }
        context.setVariable(ARGS, arguments);

        if (hasRetVal) {
            context.setVariable(RET_VAL, retVal);
        }

        Expression expression = expCache.get(keyEL);

        if (expression == null) {
            expression = parser.parseExpression(keyEL);
            expCache.put(keyEL, expression);
        }

        return expression.getValue(context, valueType);
    }

}
