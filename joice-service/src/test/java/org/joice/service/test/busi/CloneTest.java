/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.test.busi;

import java.math.BigDecimal;

/**
 * 
 * @author HuHui
 * @version $Id: CloneTest.java, v 0.1 2017年11月11日 上午11:38:24 HuHui Exp $
 */
public class CloneTest implements Cloneable {

    public static void main(String[] args) throws CloneNotSupportedException {
        CloneTest ct = new CloneTest();
        ct.clone();

        BigDecimal d1 = new BigDecimal(10.5);
        BigDecimal d2 = new BigDecimal(10.50);

        System.out.println(d1.equals(d2));
        System.out.println(d1.compareTo(d2));
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        System.out.println("clone from CloneTest");
        return super.clone();
    }

}
