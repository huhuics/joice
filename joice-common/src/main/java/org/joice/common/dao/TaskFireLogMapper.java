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

    /**
     * 插入记录并获取主键
     * 获取到的主键已保存在id字段中,该方法返回1
     */
    Long insertAndGetId(TaskFireLog record);

}