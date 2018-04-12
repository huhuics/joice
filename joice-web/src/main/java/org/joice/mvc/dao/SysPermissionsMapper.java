package org.joice.mvc.dao;

import java.util.List;

import org.joice.mvc.dao.domain.SysPermissions;

public interface SysPermissionsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysPermissions record);

    int insertSelective(SysPermissions record);

    SysPermissions selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPermissions record);

    int updateByPrimaryKey(SysPermissions record);

    /**
     * 插入记录，并返回主键
     */
    Long insertWithKey(SysPermissions record);

    List<String> selectByUserId(Long userId);
}