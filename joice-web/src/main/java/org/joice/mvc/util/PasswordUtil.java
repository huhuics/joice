/**
 *
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.util;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.joice.mvc.dao.domain.SysUsers;

/**
 * 密码MD5加密
 * @author HuHui
 * @version $Id: PasswordUtil.java, v 0.1 2018年4月12日 下午5:29:03 HuHui Exp $
 */
public class PasswordUtil {

    private static RandomNumberGenerator random         = new SecureRandomNumberGenerator();

    private static final String          algorithmName  = "md5";

    private static final int             hashIterations = 2;

    public static void encryptPassword(SysUsers user) {
        user.setSalt(random.nextBytes().toHex());

        String encryptedPassword = new SimpleHash(algorithmName, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), hashIterations).toHex();

        user.setPassword(encryptedPassword);
    }

}
