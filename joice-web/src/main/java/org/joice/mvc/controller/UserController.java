/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.controller;

import javax.annotation.Resource;

import org.joice.common.util.LogUtil;
import org.joice.mvc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

/**
 * 用户注册
 * @author HuHui
 * @version $Id: UserController.java, v 0.1 2018年1月12日 上午11:14:56 HuHui Exp $
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger   = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService         userService;

    private static final String SUCC_MSG = "do user register success";

    private static final String FAIL_MSG = "do user register failed";

    @ResponseBody
    @RequestMapping(value = "/doRegister", method = RequestMethod.GET)
    public String doRegister(WebRequest webRequest, ModelMap map) {
        try {
            String userName = "userName" + System.currentTimeMillis();
            userService.register(userName);

            LogUtil.info(logger, "用户注册请求完成");
        } catch (Exception e) {
            LogUtil.error(e, logger, FAIL_MSG);
            return FAIL_MSG;
        }

        return SUCC_MSG;
    }

}
