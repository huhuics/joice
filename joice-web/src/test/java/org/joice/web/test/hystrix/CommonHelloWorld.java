/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.web.test.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * 
 * @author HuHui
 * @version $Id: CommonHelloWorld.java, v 0.1 2018年3月21日 上午11:24:40 HuHui Exp $
 */
public class CommonHelloWorld extends HystrixCommand<String> {

    private final String name;

    protected CommonHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("example group"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return "Hello " + name;
    }

}
