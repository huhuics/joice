/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.test.callback;

/**
 * 客户端
 * 模拟客户端向服务端发送请求,服务端处理5s后通知客户端处理状态
 * @author HuHui
 * @version $Id: Client.java, v 0.1 2017年11月7日 上午10:14:00 HuHui Exp $
 */
public class Client implements Callback {

    private Server server;

    private String msg = "Hello Callback";

    public Client(Server server) {
        this.server = server;
    }

    public void send() {
        System.out.println("客户端开始发送消息到服务端");
        server.process(msg, this);
        System.out.println("客户端发送完成");
    }

    @Override
    public void callback(String status) {
        System.out.println("客户端收到服务端处理结果:" + status);
    }

}
