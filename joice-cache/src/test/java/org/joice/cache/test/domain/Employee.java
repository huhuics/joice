/**
 * 
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.test.domain;

import org.joice.cache.to.BaseTO;

/**
 * 测试对象
 * @author HuHui
 * @version $Id: Employee.java, v 0.1 2017年10月10日 下午5:00:37 HuHui Exp $
 */
public class Employee extends BaseTO {

    /**  */
    private static final long serialVersionUID = -4620016204472694934L;

    private long              id;

    private String            name;

    private Department        dept;

    public Employee(long id, String name, Department dept) {
        this.id = id;
        this.name = name;
        this.dept = dept;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDept() {
        return dept;
    }

    public Department getDept(Long id) {
        return dept;
    }

    public Department getDept(Department dep) {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

}
