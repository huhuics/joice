package com.sunveee.template.ssm.dao;

import java.util.List;

import com.sunveee.template.ssm.model.User;
import com.sunveee.template.ssm.util.PageEntity;

public interface UserDao {
    public User selectById(Integer id);

    public int deleteById(Integer id);

    public List<User> selectUserPage(PageEntity pageEntity);

    public Integer countAll();

}
