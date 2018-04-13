/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.shiro.core;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.joice.mvc.dao.domain.SysUsers;
import org.joice.mvc.shiro.service.UserService;
import org.joice.mvc.util.SpringContextUtil;

/**
 * 
 * @author HuHui
 * @version $Id: UserRealm.java, v 0.1 2018年4月12日 下午9:33:03 HuHui Exp $
 */
public class UserRealm extends AuthorizingRealm {

    private UserService userService = (UserService) SpringContextUtil.getContext().getBean("sysUserService");

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        long userId = (long) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorInfo = new SimpleAuthorizationInfo();
        authorInfo.setRoles(userService.findRoles(userId));
        authorInfo.setStringPermissions(userService.findPermissions(userId));

        return authorInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        long userId = Long.parseLong((String) token.getPrincipal());

        SysUsers user = userService.findById(userId);

        if (user == null) {
            throw new UnknownAccountException("用户号不存在");
        }

        if (Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException("用户被锁定");
        }

        SimpleAuthenticationInfo authenInfo = new SimpleAuthenticationInfo(user.getId(), //用户ID
            user.getPassword(), //密码
            ByteSource.Util.bytes(user.getSalt()), //salt
            getName());

        return authenInfo;
    }

}
