package org.joice.mvc.dao;

import org.apache.ibatis.annotations.Param;
import org.joice.mvc.dao.domain.SysUsersRoles;

public interface SysUsersRolesMapper {
    int deleteByPrimaryKey(@Param("userId") Long userId, @Param("roleId") Long roleId);

    int insert(SysUsersRoles record);

    int insertSelective(SysUsersRoles record);

    int deleteByRoleId(Long roleId);
}