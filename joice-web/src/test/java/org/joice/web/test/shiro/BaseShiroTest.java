/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.web.test.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.joice.web.test.base.BaseTest;
import org.junit.After;

/**
 * 
 * @author HuHui
 * @version $Id: BaseShiroTest.java, v 0.1 2018年4月13日 上午9:12:11 HuHui Exp $
 */
public class BaseShiroTest extends BaseTest {

    protected void login(String configFile, String userId, String password) {
        //1.获取SecurityManager工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);

        //2.将SecurityManager实例绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //3.得到主体
        Subject subject = SecurityUtils.getSubject();

        //4.创建token
        AuthenticationToken token = new UsernamePasswordToken(userId, password);

        //5.登录，即身份验证
        subject.login(token);
    }

    protected Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    @After
    public void tearDown() {
        ThreadContext.unbindSubject();
    }
}
