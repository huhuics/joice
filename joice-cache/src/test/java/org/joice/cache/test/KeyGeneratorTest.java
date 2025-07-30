/**
 * 
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.test;

import java.lang.reflect.Method;

import org.joice.cache.ke.gene.HashCodeKeyGenerator;
import org.joice.cache.test.domain.Department;
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

    @Test
    public void testHashCodeKey2() throws Exception {
        HashCodeKeyGenerator keyGene = new HashCodeKeyGenerator();
        Employee emp = new Employee(1L, "武松", new Department(1L, "步兵"));

        Department qDept1 = new Department(2L, "华山派");
        Department qDept2 = new Department(2L, "华山派");

        Method method1 = emp.getClass().getMethod("getDept", Department.class);
        Integer code1 = keyGene.getKey(emp.getClass(), method1, qDept1);
        Integer code2 = keyGene.getKey(emp.getClass(), method1, qDept2);
        LogUtil.info(logger, "code1:{0},code2:{1}", code1, code2);

        Method method2 = emp.getClass().getMethod("getDept", Long.class);
        code1 = keyGene.getKey(emp.getClass(), method2, 2L);
        code2 = keyGene.getKey(emp.getClass(), method2, 3L);
        LogUtil.info(logger, "code1:{0},code2:{1}", code1, code2);

    }
}
