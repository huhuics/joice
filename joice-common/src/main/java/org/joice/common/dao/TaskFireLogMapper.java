package org.joice.common.dao;

import org.joice.common.dao.domain.TaskFireLog;

public interface TaskFireLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TaskFireLog record);

    int insertSelective(TaskFireLog record);

    TaskFireLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskFireLog record);

    int updateByPrimaryKeyWithBLOBs(TaskFireLog record);

    int updateByPrimaryKey(TaskFireLog record);
}