/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.test;

import org.apache.commons.lang3.StringUtils;
import org.joice.cache.ke.gene.SpringELParser;
import org.joice.cache.test.domain.Department;
import org.joice.cache.test.domain.Employee;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author HuHui
 * @version $Id: SpringELParserTest.java, v 0.1 2017年10月26日 下午9:08:39 HuHui Exp $
 */
public class SpringELParserTest {

    private SpringELParser elParser = new SpringELParser();

    @Test
    public void testGetELValue() throws Exception {
        Department detp = new Department(2L, "峨眉派");
        Employee emp = new Employee(1L, "灭绝师太", detp);

        String keySpEL = "'emplyee_setDept_'+#args[0].deptId";

        Object obj[] = { detp };
        String elValue = elParser.getELStringValue(keySpEL, obj);

        Assert.assertTrue(StringUtils.equals(elValue, "emplyee_setDept_2"));

    }

    @Test
    public void testGetELBooleanValue() {
        Department detp = new Department(2L, "峨眉派");
        Employee emp = new Employee(1L, "灭绝师太", detp);

        String keySpEL1 = "#args[0].deptId > 1";
        Object obj[] = { detp };

        Boolean b1 = elParser.getELBooleanValue(keySpEL1, obj);
        Assert.assertTrue(b1);

        String keySpEL2 = "#args[0].deptId > 2";
        Boolean b2 = elParser.getELBooleanValue(keySpEL2, obj);
        Assert.assertFalse(b2);
    }
}
