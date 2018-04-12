package org.joice.mvc.dao;

import java.util.Map;

import org.joice.mvc.dao.domain.BizUserAccount;

public interface BizUserAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BizUserAccount record);

    int insertSelective(BizUserAccount record);

    BizUserAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BizUserAccount record);

    int updateByPrimaryKey(BizUserAccount record);

    /**
     * 通过用户号查询
     */
    BizUserAccount selectByUserId(String userId);

    /**
     * <p>修改用户余额，参数包含两个：modifiedBalance和userId</p>
     * 1.增加余额：modifiedBalance参数为正</br>
     * 2.减少余额：modifiedBalance参数为负
     */
    int updateBalance(Map<String, Object> paraMap);

}