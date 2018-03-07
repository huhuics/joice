/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.support.scheduler.job;

import org.springframework.stereotype.Component;

/**
 * 只是一个测试类
 * @author HuHui
 * @version $Id: SayHi.java, v 0.1 2017年8月25日 下午8:41:34 HuHui Exp $
 */
@Component("sayHi")
public class SayHi {

    public void say() {
        System.out.println(" Hi from SayHi! ");
    }

    /**
     * 休眠一段时间
     * @throws InterruptedException 
     */
    public void sayAndSleep() throws InterruptedException {
        System.out.println(" Hi from sayAndSleep! ");
        Thread.sleep(5000);
    }
}
