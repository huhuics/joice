/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.web.test.hystrix;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author HuHui
 * @version $Id: FallbackHelloWorldTest.java, v 0.1 2018年3月21日 下午3:16:31 HuHui Exp $
 */
public class FallbackHelloWorldTest {

    @Test
    public void testExecute() {
        Assert.assertEquals("fall back world", new FallbackHelloWorld("world").execute());
    }

}
