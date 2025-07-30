/**
 *
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.shiro.service;

import org.joice.mvc.dao.domain.SysPermissions;

/**
 * 权限服务接口
 * @author HuHui
 * @version $Id: PermissionService.java, v 0.1 2018年4月12日 下午3:59:52 HuHui Exp $
 */
public interface PermissionService {

    /**
     * 创建权限
     */
    SysPermissions createPermission(SysPermissions permission);

    /**
     * 删除权限
     */
    void deletePermission(Long permissionId);

}
