/**
 *
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.shiro.service.impl;

import javax.annotation.Resource;

import org.joice.mvc.dao.SysPermissionsMapper;
import org.joice.mvc.dao.domain.SysPermissions;
import org.joice.mvc.shiro.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * 
 * @author HuHui
 * @version $Id: PermissionServiceImpl.java, v 0.1 2018年4月12日 下午4:02:29 HuHui Exp $
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private SysPermissionsMapper permissionsMapper;

    @Override
    public SysPermissions createPermission(SysPermissions permission) {
        Long id = permissionsMapper.insertWithKey(permission);
        permission.setId(id);
        return permission;
    }

    public void deletePermission(Long permissionId) {
        permissionsMapper.deleteByPrimaryKey(permissionId);
    }

}
