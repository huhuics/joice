package com.sunveee.template.ssm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sunveee.joice.cache.annotation.Cacheable;
import com.sunveee.template.ssm.dao.UserDao;
import com.sunveee.template.ssm.model.User;
import com.sunveee.template.ssm.service.UserService;
import com.sunveee.template.ssm.util.PageEntity;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserDao             userDao;

    @Cacheable(argRange = { 0 }, expireTime = 3)
    @Override
    public User getUserById(int userId) {
        LogUtil.info(logger, "数据库查询User,查询条件: userId={0}", userId);
        return this.userDao.selectById(userId);
    }

    @Cacheable(argRange = { 0, 1 }, expireTime = 3)
    @Override
    public List<User> getUserPage(int pageNo, int pageSize) {
        LogUtil.info(logger, "数据库查询UserList,查询条件: pageNo={0},pageSize={1}", pageNo, pageSize);
        return this.userDao.selectUserPage(new PageEntity(pageNo, pageSize));
    }

    @Cacheable(expireTime = 3)
    @Override
    public Integer getAllUserCount() {
        LogUtil.info(logger, "数据库查询User总数");
        return this.userDao.countAll();
    }

}
