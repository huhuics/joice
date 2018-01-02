package org.joice.common.dao;

import org.joice.common.dao.domain.BizUserAccount;

public interface BizUserAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BizUserAccount record);

    int insertSelective(BizUserAccount record);

    BizUserAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BizUserAccount record);

    int updateByPrimaryKey(BizUserAccount record);
}