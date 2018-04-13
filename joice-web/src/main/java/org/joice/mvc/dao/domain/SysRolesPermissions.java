package org.joice.mvc.dao.domain;

import org.joice.common.base.BaseDomain;

public class SysRolesPermissions extends BaseDomain {
    /**  */
    private static final long serialVersionUID = 6295465314674081747L;

    private Long              roleId;

    private Long              permissionId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}