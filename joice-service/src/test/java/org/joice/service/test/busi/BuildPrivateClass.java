/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.test.busi;

import java.lang.reflect.Constructor;

import org.junit.Test;

/**
 * 通过反射实例化私有构造方法的类
 * @author HuHui
 * @version $Id: BuildPrivateClass.java, v 0.1 2017年11月9日 下午4:09:53 HuHui Exp $
 */
public class BuildPrivateClass {

    @Test
    public void test1() throws Exception {
        Class<?> clazz = Class.forName("org.joice.service.test.busi.Student");
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> con : constructors) {
            con.setAccessible(true);
            Student stu = (Student) con.newInstance(null);
            System.out.println(stu);
        }
    }

    @Test
    public void test2() {
        System.out.println(StudentEnum.instance);
        StudentEnum.instance.setAge(34);
        System.out.println(StudentEnum.instance);
    }

}
