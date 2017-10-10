/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author HuHui
 * @version $Id: CloneTest.java, v 0.1 2017年10月10日 上午11:06:29 HuHui Exp $
 */
public class CloneTest {

    class A implements Cloneable {
        private int    age;

        private String name;

        public A() {
        }

        public A(int age, String name) {
            super();
            this.age = age;
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        @Override
        public String toString() {
            return name + ", " + age;
        }

    }

    private A a;

    private A aClone;

    @Before
    public void before() throws Exception {
        a = new A(5, "a");
        aClone = (A) a.clone();
    }

    @Test
    public void testClone() throws Exception {
        boolean b = (aClone.getClass() == a.getClass());
        Assert.assertTrue(b);

    }

    @Test
    public void testEquals() {
        boolean b = (aClone.equals(a));
        Assert.assertTrue(b);
    }

    @Test
    public void testEquals2() {
        Assert.assertTrue(a != aClone);
    }

    @Test
    public void testClone2() {
        System.out.println(a + " | " + aClone);
        aClone.setAge(10);
        aClone.setName("aClone");
        System.out.println(a + " | " + aClone);
    }
}
