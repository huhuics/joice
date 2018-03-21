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
 * @version $Id: FallbackHelloWorld.java, v 0.1 2018年3月21日 上午11:44:08 HuHui Exp $
 */
public class FallbackHelloWorld extends HystrixCommand<String> {

    private final String name;

    public FallbackHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("example group"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        throw new RuntimeException("运行抛异常");
    }

    @Override
    protected String getFallback() {
        return "fall back " + name;
    }

}
