/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.web.test.hystrix;

import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author HuHui
 * @version $Id: CommonHelloWorldTest.java, v 0.1 2018年3月21日 上午11:28:11 HuHui Exp $
 */
public class CommonHelloWorldTest {

    @Test
    public void testExecute() {
        Assert.assertEquals("Hello world", new CommonHelloWorld("world").execute());
        Assert.assertEquals("Hello bob", new CommonHelloWorld("bob").execute());
    }

    @Test
    public void testAsyn() throws Exception {
        Future<String> fWorld = new CommonHelloWorld("world").queue();
        Assert.assertEquals("Hello world", fWorld.get());

        Future<String> fBob = new CommonHelloWorld("bob").queue();
        Assert.assertEquals("Hello bob", fBob.get());
    }

}
