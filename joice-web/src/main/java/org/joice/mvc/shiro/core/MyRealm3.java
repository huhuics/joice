/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.shiro.core;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
import org.joice.mvc.dao.domain.SysUsers;

/**
 * 
 * @author HuHui
 * @version $Id: MyRealm1.java, v 0.1 2018年4月13日 上午11:06:09 HuHui Exp $
 */
public class MyRealm3 implements Realm {

    @Override
    public String getName() {
        return "c";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        SysUsers user = new SysUsers();
        user.setId(1L);
        user.setPassword("123");

        return new SimpleAuthenticationInfo(user, "123", getName());
    }

}
