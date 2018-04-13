/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.web.test.shiro;

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
        login(INI_FILE, "1", "1223");

        Assert.assertFalse(getSubject().isAuthenticated());
    }

}
