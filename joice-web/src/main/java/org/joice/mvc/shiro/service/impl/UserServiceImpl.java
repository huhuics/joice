/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.shiro.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.joice.mvc.dao.SysPermissionsMapper;
import org.joice.mvc.dao.SysRolesMapper;
import org.joice.mvc.dao.SysUsersMapper;
import org.joice.mvc.dao.SysUsersRolesMapper;
import org.joice.mvc.dao.domain.SysPermissions;
import org.joice.mvc.dao.domain.SysRoles;
import org.joice.mvc.dao.domain.SysUsers;
import org.joice.mvc.dao.domain.SysUsersRoles;
import org.joice.mvc.shiro.service.UserService;
import org.joice.mvc.util.PasswordUtil;
import org.springframework.stereotype.Service;

/**
 * 用户服务接口实现类
 * @author HuHui
 * @version $Id: UserServiceImpl.java, v 0.1 2018年4月12日 下午5:06:39 HuHui Exp $
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private SysUsersMapper       usersMapper;

    @Resource
    private SysUsersRolesMapper  usersRolesMapper;

    @Resource
    private SysRolesMapper       rolesMapper;

    @Resource
    private SysPermissionsMapper permissionsMapper;

    @Override
    public SysUsers createUser(SysUsers user) {
        PasswordUtil.encryptPassword(user);
        Long id = usersMapper.insertWithKey(user);
        user.setId(id);
        return user;
    }

    @Override
    public void updatePassword(Long userId, String newPassword) {
        SysUsers user = usersMapper.selectByPrimaryKey(userId);
        user.setPassword(newPassword);
        PasswordUtil.encryptPassword(user);
        usersMapper.updateByPrimaryKey(user);
    }

    @Override
    public void correlationRoles(Long userId, Long... roleIds) {
        if (ArrayUtils.isEmpty(roleIds)) {
            return;
        }

        for (Long roleId : roleIds) {
            SysUsersRoles record = new SysUsersRoles();
            record.setUserId(userId);
            record.setRoleId(roleId);
            usersRolesMapper.insert(record);
        }
    }

    @Override
    public void uncorrelationRoles(Long userId, Long... roleIds) {
        if (ArrayUtils.isEmpty(roleIds)) {
            return;
        }

        for (Long roleId : roleIds) {
            usersRolesMapper.deleteByPrimaryKey(userId, roleId);
        }
    }

    @Override
    public SysUsers findById(Long userId) {
        return usersMapper.selectByPrimaryKey(userId);
    }

    @Override
    public Set<SysRoles> findRoles(Long userId) {
        List<SysRoles> roles = rolesMapper.selectByUserId(userId);
        return new HashSet<SysRoles>(roles);
    }

    @Override
    public Set<SysPermissions> findPermissions(Long userId) {
        List<SysPermissions> permissions = permissionsMapper.selectByUserId(userId);
        return new HashSet<SysPermissions>(permissions);
    }

}
