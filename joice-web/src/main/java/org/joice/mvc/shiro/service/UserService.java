/**
 *
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.shiro.service;

import java.util.Set;

import org.joice.mvc.dao.domain.SysUsers;

/**
 * 用户服务接口
 * @author HuHui
 * @version $Id: UserService.java, v 0.1 2018年4月12日 下午4:59:37 HuHui Exp $
 */
public interface UserService {

    /**
     * 创建用户
     */
    SysUsers createUser(SysUsers user);

    /**
     * 修改密码
     */
    void updatePassword(Long userId, String newPassword);

    /**
     * 添加用户-角色关系
     */
    void correlationRoles(Long userId, Long... roleIds);

    /**
     * 移除用户-角色关系
     */
    void uncorrelationRoles(Long userId, Long... roleIds);

    /**
     * 根据用户Id查找用户
     */
    SysUsers findById(Long userId);

    /**
     * 根据用户Id查找其角色
     */
    Set<String> findRoles(Long userId);

    /**
     * 根据用户Id查找其权限
     */
    Set<String> findPermissions(Long userId);

}
