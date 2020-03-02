package com.timain.web.sys.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 处理登录时获得的session
 * @author yyf
 * @version 1.0
 * @date 2020/1/15 9:15
 */
public class LoginUtils {

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request;
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }
}
