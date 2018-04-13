package org.joice.mvc.dao.domain;

import org.joice.common.base.BaseDomain;

public class SysUsersRoles extends BaseDomain {
    /**  */
    private static final long serialVersionUID = -4523168384095643154L;

    private Long              userId;

    private Long              roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}