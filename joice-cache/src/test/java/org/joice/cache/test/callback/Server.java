/**
 * 
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.test.callback;

/**
 * 模拟服务端
 * @author HuHui
 * @version $Id: Server.java, v 0.1 2017年11月7日 上午10:14:54 HuHui Exp $
 */
public class Server {

    public void process(String msg, Callback callback) {
        System.out.println("服务端收到消息:" + msg + ", 开始处理");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("服务端处理完成,并准备回调客户端");
        callback.callback("200");
    }

    public static void main(String[] args) {
        Server server = new Server();
        Client client = new Client(server);
        client.send();
    }

}
