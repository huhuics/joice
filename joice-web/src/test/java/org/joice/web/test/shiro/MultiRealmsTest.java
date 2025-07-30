/**
 *
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.web.test.shiro;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.joice.common.util.LogUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author HuHui
 * @version $Id: MultiRealmsTest.java, v 0.1 2018年4月13日 上午11:17:29 HuHui Exp $
 */
public class MultiRealmsTest extends BaseShiroTest {

    private static final String INI_FILE = "classpath:config/shiro-multirealms.ini";

    @Test
    public void test() {
        //由于realm里面没有验证身份，所以此处所有身份都会校验成功
        login(INI_FILE, "1", "123");
        Subject subject = getSubject();

        Object primaryPrin1 = subject.getPrincipal();

        //获取primary principal
        PrincipalCollection prins = subject.getPrincipals();

        Object primaryPrin2 = prins.getPrimaryPrincipal();

        Assert.assertEquals(primaryPrin1, primaryPrin2);

        Set<String> realmNames = prins.getRealmNames();
        LogUtil.info(logger, "realm names={0}", realmNames);

        List<Object> asList = prins.asList();
        LogUtil.info(logger, "as list={0}", asList);

        Collection users = prins.fromRealm("c");
        LogUtil.info(logger, "users={0}", users);

    }

}
