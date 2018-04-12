/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.shiro.service;

import org.joice.mvc.dao.domain.SysRoles;

/**
 * 角色服务接口
 * @author HuHui
 * @version $Id: RoleService.java, v 0.1 2018年4月12日 下午4:12:20 HuHui Exp $
 */
public interface RoleService {

    /**
     * 增加角色
     */
    SysRoles createRole(SysRoles role);

    /**
     * 删除角色
     */
    void deleteRole(Long roleId);

    /**
     * 添加角色-权限之间关系
     */
    void correlationPermissions(Long roleId, Long... permissionIds);

    /**
     * 移除角色-权限之间关系
     */
    void uncorrelationPermissions(Long roleId, Long... permissionIds);

}
