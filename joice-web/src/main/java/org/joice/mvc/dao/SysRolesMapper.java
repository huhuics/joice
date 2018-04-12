package org.joice.mvc.dao;

import java.util.List;

import org.joice.mvc.dao.domain.SysRoles;

public interface SysRolesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRoles record);

    int insertSelective(SysRoles record);

    SysRoles selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoles record);

    int updateByPrimaryKey(SysRoles record);

    Long insertWithKey(SysRoles record);

    List<SysRoles> selectByUserId(Long userId);
}