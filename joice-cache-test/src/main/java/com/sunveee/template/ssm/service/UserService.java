package com.sunveee.template.ssm.service;

import java.util.List;

import com.sunveee.template.ssm.model.User;

public interface UserService {
    public User getUserById(int userId);

    public List<User> getUserPage(int pageNo, int pageSize);

    public Integer getAllUserCount();
}
