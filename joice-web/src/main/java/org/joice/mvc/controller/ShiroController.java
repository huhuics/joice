/**
 *
 * Copyright (c) 1995-2018 All Rights Reserved.
 */
package org.joice.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.joice.common.util.LogUtil;
import org.joice.mvc.dao.domain.SysUsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author HuHui
 * @version $Id: ShiroController.java, v 0.1 2018年4月13日 下午6:59:21 HuHui Exp $
 */
@Controller
public class ShiroController {

    private static final Logger logger = LoggerFactory.getLogger(ShiroController.class);

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String login() {
        LogUtil.info(logger, "用户进入登录页面");
        return "login";
    }

    @PostMapping(value = "/doLogin")
    public String doLogin(SysUsers user, HttpServletRequest request, ModelMap map) {
        LogUtil.info(logger, "收到用户登录请求,user={0}", user);

        UsernamePasswordToken token = new UsernamePasswordToken(user.getId() + "", user.getPassword());

        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);
            if (subject.isAuthenticated()) {
                LogUtil.info(logger, "登录成功");
                map.addAttribute("user", user);

                //获取保存的URL
                SavedRequest savedRequest = WebUtils.getSavedRequest(request);
                if (savedRequest == null || savedRequest.getRequestUrl() == null) {
                    return "loginSuc";
                } else {
                    return "forward:" + savedRequest.getRequestUrl();
                }

            }
        } catch (IncorrectCredentialsException e) {
            map.addAttribute("message", "密码错误");
        } catch (LockedAccountException e) {
            map.addAttribute("message", "账户被锁定");
            return "账户被锁定";
        } catch (UnknownAccountException e) {
            map.addAttribute("message", "账户不存在");
        } catch (Exception e) {
            LogUtil.error(e, logger, "登录异常");
            map.addAttribute("message", "登录失败");
            return "登录失败";
        }

        return "loginFailed";
    }

}
