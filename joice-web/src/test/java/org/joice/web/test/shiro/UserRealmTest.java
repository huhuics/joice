/**
 *
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.web.test.shiro;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author HuHui
 * @version $Id: UserRealmTest.java, v 0.1 2018年4月13日 上午9:08:46 HuHui Exp $
 */
public class UserRealmTest extends BaseShiroTest {

    private static final String INI_FILE = "classpath:config/shiro-userrealm.ini";

    @Test
    public void testLoginSuccess() {
        login(INI_FILE, "1", "123");
        Assert.assertTrue(getSubject().isAuthenticated());
    }

    @Test(expected = UnknownAccountException.class)
    public void testLoginFailedWithUnknownUsername() {
        login(INI_FILE, "11", "1232");
    }

    @Test(expected = IncorrectCredentialsException.class)
    public void testLoginFailedWithIncorrectCredentials() {
        login(INI_FILE, "1", "12332");
    }

    @Test(expected = LockedAccountException.class)
    public void testLoginFailedWithLockedAccount() {
        login(INI_FILE, "5", "123");
    }

    @Test
    public void testHasRole() {
        login(INI_FILE, "1", "123");
        Assert.assertTrue(getSubject().hasRole("programmer"));
    }

    @Test
    public void testHasNoRole() {
        login(INI_FILE, "1", "123");
        Assert.assertFalse(getSubject().hasRole("PM"));
    }

    @Test
    public void testHasPermission() {
        login(INI_FILE, "1", "123");
        Assert.assertTrue(getSubject().isPermitted("select"));
    }

    @Test
    public void testHasNoPermission() {
        login(INI_FILE, "1", "123");
        Assert.assertFalse(getSubject().isPermitted("delete"));
    }

}
