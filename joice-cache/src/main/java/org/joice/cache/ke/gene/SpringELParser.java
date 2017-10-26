/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.ke.gene;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Spring EL表达式解析器
 * @author HuHui
 * @version $Id: SpringELParser.java, v 0.1 2017年10月26日 下午8:54:56 HuHui Exp $
 */
public class SpringELParser {

    private static final Logger                         logger   = LoggerFactory.getLogger(SpringELParser.class);

    private final ConcurrentHashMap<String, Expression> expCache = new ConcurrentHashMap<String, Expression>();

    private final ExpressionParser                      parser   = new SpelExpressionParser();

    private static final String                         ARGS     = "args";

    @SuppressWarnings("unchecked")
    public <T> T getELValue(String keySpEL, Object[] args, Class<T> classType) {
        if (classType == String.class) {
            if (keySpEL.indexOf("#") == -1 && keySpEL.indexOf("'") == -1) {
                return (T) keySpEL; //如果不是EL表达式则返回原字符串
            }
        }

        StandardEvaluationContext context = new StandardEvaluationContext();

        context.setVariable(ARGS, args);

        Expression expression = expCache.get(keySpEL);

        if (expression == null) {
            expression = parser.parseExpression(keySpEL);
            expCache.put(keySpEL, expression);
        }

        return expression.getValue(context, classType);

    }

    public String getELStringValue(String keySpEL, Object[] args) {

        return getELValue(keySpEL, args, String.class);

    }

    public Boolean getELBooleanValue(String keySpEL, Object[] args) {
        return getELValue(keySpEL, args, Boolean.class);
    }

}
