/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.test;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.joice.cache.test.domain.Department;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试SoftReference
 * @author HuHui
 * @version $Id: SoftReferenceTest.java, v 0.1 2017年10月11日 下午7:51:28 HuHui Exp $
 */
public class SoftReferenceTest {

    @Test
    public void testSoftRef() throws Exception {
        Department dept = new Department(1L, "水泊梁山");

        SoftReference<Department> deptSoftRef = new SoftReference<Department>(dept);

        dept = null;

        int i = 0;
        while (deptSoftRef.get() != null) {
            System.out.println("deptSoftRef.get()=" + deptSoftRef.get() + ", i=" + i);
            if (i % 10 == 0) {
                System.gc();
                System.out.println("gc invoked");
            }
            ++i;
            Thread.sleep(500);
        }

        System.out.println("dept对象被jvm清理");

    }

    @Test
    public void testMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
        String val = map.putIfAbsent("key1", "val1");
        System.out.println("val=" + val);
        Assert.assertTrue(StringUtils.equals(val, "val1"));
        String val2 = map.putIfAbsent("key1", "val1");
        Assert.assertTrue(StringUtils.equals(val2, "val1"));
        System.out.println("val2=" + val2);
    }
}
