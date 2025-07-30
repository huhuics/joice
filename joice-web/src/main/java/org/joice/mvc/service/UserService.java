/**
 * 
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.service;

/**
 * 用户服务接口
 * @author HuHui
 * @version $Id: UserService.java, v 0.1 2018年1月11日 下午7:24:53 HuHui Exp $
 */
public interface UserService {

    /**
     * 用户注册
     * @param name 用户名
     */
    boolean register(String name);

}
