/**
 *
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.shiro.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.joice.common.util.LogUtil;
import org.joice.mvc.dao.SysRolesMapper;
import org.joice.mvc.dao.SysRolesPermissionsMapper;
import org.joice.mvc.dao.SysUsersRolesMapper;
import org.joice.mvc.dao.domain.SysRoles;
import org.joice.mvc.dao.domain.SysRolesPermissions;
import org.joice.mvc.shiro.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * 角色服务接口实现类
 * @author HuHui
 * @version $Id: RoleServiceImpl.java, v 0.1 2018年4月12日 下午4:22:31 HuHui Exp $
 */
@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger       logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Resource
    private SysRolesMapper            rolesMapper;

    @Resource
    private SysRolesPermissionsMapper rolesPermissionsMapper;

    @Resource
    private SysUsersRolesMapper       usersRolesMapper;

    @Override
    public SysRoles createRole(SysRoles role) {
        Long id = rolesMapper.insertWithKey(role);
        role.setId(id);
        return role;
    }

    @Override
    public void deleteRole(Long roleId) {

        //1.先删除用户-角色关联记录
        usersRolesMapper.deleteByRoleId(roleId);

        rolesMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public void correlationPermissions(Long roleId, Long... permissionIds) {
        if (ArrayUtils.isEmpty(permissionIds)) {
            return;
        }

        for (Long permissionId : permissionIds) {
            SysRolesPermissions record = new SysRolesPermissions();
            record.setRoleId(roleId);
            record.setPermissionId(permissionId);
            try {
                rolesPermissionsMapper.insert(record);
            } catch (DuplicateKeyException e) {
                //主键冲突
                LogUtil.error(e, logger, "主键冲突,roleId={0},permissionId={1}", roleId, permissionId);
                continue;
            }
        }
    }

    @Override
    public void uncorrelationPermissions(Long roleId, Long... permissionIds) {
        if (ArrayUtils.isEmpty(permissionIds)) {
            return;
        }

        for (Long permissionId : permissionIds) {
            rolesPermissionsMapper.deleteByPrimaryKey(roleId, permissionId);
        }
    }

}
