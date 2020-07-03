package com.timain.web.sys.listener;

import com.timain.web.sys.pojo.User;
import com.timain.web.sys.service.UserService;
import com.timain.web.sys.utils.LoginUtils;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/7/3 14:17
 */
public class TaskListenerImpl implements TaskListener {

    private static final long serialVersionUID = 1L;
    
    @Override
    public void notify(DelegateTask delegateTask) {
        //获取当前登录用户
        User user = (User) LoginUtils.getSession().getAttribute("user");
        //获取当前用户领导ID
        Integer mgr = user.getMgr();
        HttpServletRequest request = LoginUtils.getRequest();
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        UserService userService = applicationContext.getBean(UserService.class);
        //查询领导信息
        User leaderUser = userService.getById(mgr);
        //设置办理人
        delegateTask.setAssignee(leaderUser.getName());
    }
}
