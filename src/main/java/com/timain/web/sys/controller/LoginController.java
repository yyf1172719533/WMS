package com.timain.web.sys.controller;

import com.timain.web.sys.common.ActiverUser;
import com.timain.web.sys.common.ResultObj;
import com.timain.web.sys.pojo.LogInfo;
import com.timain.web.sys.service.LogInfoService;
import com.timain.web.sys.utils.LoginUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/15 8:57
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LogInfoService logInfoService;

    @RequestMapping("login")
    public ResultObj login(String loginname, String pwd) {
        Subject subject = SecurityUtils.getSubject();
        //根据登录名和密码获取token
        AuthenticationToken token = new UsernamePasswordToken(loginname,pwd);
        try {
            subject.login(token);
            ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
            LoginUtils.getSession().setAttribute("user",activerUser.getUser());

            //记录用户登录日志信息
            LogInfo info = new LogInfo();
            info.setLoginIp(LoginUtils.getRequest().getRemoteAddr());
            info.setLoginName(activerUser.getUser().getName() + "-" + activerUser.getUser().getLoginName());
            info.setLoginTime(new Date());
            logInfoService.save(info);
            return ResultObj.LOGIN_SUCCESS;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResultObj.LOGIN_ERROR_NAME_OR_PWD;
        }
    }
}
