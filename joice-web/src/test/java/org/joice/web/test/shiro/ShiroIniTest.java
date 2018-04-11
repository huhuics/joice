/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.web.test.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.joice.common.util.LogUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shiro测试
 * @author HuHui
 * @version $Id: ShiroIniTest.java, v 0.1 2018年4月11日 下午8:32:23 HuHui Exp $
 */
public class ShiroIniTest {

    private static final Logger logger      = LoggerFactory.getLogger(ShiroIniTest.class);

    private static final String CONFIG_FILE = "classpath:config/shiro.ini";

    private Subject             subject;

    private void login(AuthenticationToken token) {
        //1.获取SecurityManager工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(CONFIG_FILE);

        //2.得到SecurityManager实例并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //3. 得到Subject，并创建用户名/密码验证Token，即用户身份、凭证
        subject = SecurityUtils.getSubject();

        try {
            //4.登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            //5.身份验证失败
            LogUtil.info(logger, "身份验证失败");
        }

        Assert.assertEquals(true, subject.isAuthenticated());

    }

    /**
     * 测试登录
     */
    @Test
    public void testLogin() {
        AuthenticationToken token = new UsernamePasswordToken("zhang", "123");
        login(token);
    }

    /**
     * 测试角色
     */
    @Test
    public void testRole() {
        AuthenticationToken token = new UsernamePasswordToken("zhang", "123");
        login(token);

        subject.checkRoles("role1", "role2");
    }

    /**
     * 测试权限
     */
    @Test
    public void testPermission1() {
        AuthenticationToken token = new UsernamePasswordToken("zhang", "123");
        login(token);

        Assert.assertTrue(subject.isPermitted("system:user:select"));
    }

    @Test
    public void testPermission2() {
        AuthenticationToken token = new UsernamePasswordToken("hu", "123");
        login(token);

        Assert.assertTrue(subject.isPermitted("system:user:*"));
        Assert.assertTrue(subject.isPermitted("system:user:select"));

        //https://blog.csdn.net/swingpyzf/article/details/46342023
        //http://jinnianshilongnian.iteye.com/blog/2019547
    }

    @After
    public void logout() {
        //退出
        subject.logout();
    }

}
