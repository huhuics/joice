package com.sunveee.template.ssm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunveee.template.ssm.dao.UserDao;
import com.sunveee.template.ssm.model.User;
import com.sunveee.template.ssm.service.UserService;
import com.sunveee.template.ssm.util.PageEntity;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    public User getUserById(int userId) {
        return this.userDao.selectById(userId);
    }

    @Override
    public List<User> getUserPage(int pageNo, int pageSize) {
        return this.userDao.selectUserPage(new PageEntity(pageNo, pageSize));
    }

    @Override
    public Integer getAllUserCount() {
        return this.userDao.countAll();
    }

}
