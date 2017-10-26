/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.test;

import java.lang.reflect.Method;

import org.joice.cache.ke.gene.HashCodeKeyGenerator;
import org.joice.cache.test.domain.Employee;
import org.joice.common.util.LogUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author HuHui
 * @version $Id: KeyGeneratorTest.java, v 0.1 2017年10月26日 下午3:35:01 HuHui Exp $
 */
public class KeyGeneratorTest {

    private static final Logger logger = LoggerFactory.getLogger(KeyGeneratorTest.class);

    @Test
    public void testHashCodeKey() {
        HashCodeKeyGenerator keyGene = new HashCodeKeyGenerator();
        Employee emp = new Employee(1L, "鲁智深", null);
        Method[] methods = emp.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Integer code = keyGene.getKey(emp.getClass(), method, null);
            LogUtil.info(logger, "className:{0},{1}:{2}", emp.getClass().getName(), method.getName(), code);
        }
    }
}
