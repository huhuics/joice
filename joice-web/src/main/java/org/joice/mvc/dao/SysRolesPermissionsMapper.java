package org.joice.mvc.dao;

import org.apache.ibatis.annotations.Param;
import org.joice.mvc.dao.domain.SysRolesPermissions;

public interface SysRolesPermissionsMapper {
    int deleteByPrimaryKey(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    int insert(SysRolesPermissions record);

    int insertSelective(SysRolesPermissions record);
}