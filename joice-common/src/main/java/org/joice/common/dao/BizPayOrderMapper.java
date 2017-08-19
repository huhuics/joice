package org.joice.common.dao;

import org.joice.common.dao.domain.BizPayOrder;

public interface BizPayOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BizPayOrder record);

    int insertSelective(BizPayOrder record);

    BizPayOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BizPayOrder record);

    int updateByPrimaryKey(BizPayOrder record);
}