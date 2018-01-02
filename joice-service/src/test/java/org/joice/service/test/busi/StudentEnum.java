/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.test.busi;

/**
 * 枚举实现单例
 * @author HuHui
 * @version $Id: StudentEnum.java, v 0.1 2017年11月9日 下午4:39:25 HuHui Exp $
 */
public enum StudentEnum {

    instance("林冲", 32);

    private String name;

    private int    age;

    private StudentEnum(String name, int age) {
        System.out.println("StudentEnum 构造方法");
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "name:" + name + ", age:" + age;
    }

}
