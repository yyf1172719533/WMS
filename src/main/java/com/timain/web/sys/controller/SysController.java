package com.timain.web.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统控制器
 * @author yyf
 * @version 1.0
 * @date 2020/1/15 8:42
 */
@Controller
@RequestMapping("sys")
public class SysController {

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("toLogin")
    public String toLogin() {
        return "sys/index/login";
    }

    /**
     * 跳转到首页
     * @return
     */
    @RequestMapping("toIndex")
    public String toIndex() {
        return "sys/index/index";
    }

    /**
     * 跳转到工作台页面
     * @return
     */
    @RequestMapping("toDeskManage")
    public String toDeskManage() {
        return "sys/index/deskManage";
    }

    /**
     * 跳转到登录日志管理页面
     * @return
     */
    @RequestMapping("toLogInfo")
    public String toLogInfo() {
        return "sys/logInfo/LogInfo";
    }

    /**
     * 跳转到公告管理页面
     * @return
     */
    @RequestMapping("toNoticeInfo")
    public String toNoticeInfo() {
        return "sys/notice/NoticeInfo";
    }

    /**
     * 跳转到部门管理页面
     * @return
     */
    @RequestMapping("toDeptManager")
    public String toDeptManager() {
        return "sys/dept/deptManager";
    }

    /**
     * 跳转到部门管理-left
     * @return
     */
    @RequestMapping("toDeptLeft")
    public String toDeptLeft() {
        return "sys/dept/deptLeft";
    }

    /**
     * 跳转到部门管理-right
     * @return
     */
    @RequestMapping("toDeptRight")
    public String toDeptRight() {
        return "sys/dept/deptRight";
    }


    /**
     * 跳转到菜单管理页面
     * @return
     */
    @RequestMapping("toMenuManager")
    public String toMenuManager() {
        return "sys/menu/menuManager";
    }

    /**
     * 跳转到菜单管理-left
     * @return
     */
    @RequestMapping("toMenuLeft")
    public String toMenuLeft() {
        return "sys/menu/menuLeft";
    }

    /**
     * 跳转到菜单管理-right
     * @return
     */
    @RequestMapping("toMenuRight")
    public String toMenuRight() {
        return "sys/menu/menuRight";
    }

    /**
     * 跳转到权限管理页面
     * @return
     */
    @RequestMapping("toPermissionManager")
    public String toPermissionManager() {
        return "sys/permission/permissionManager";
    }

    /**
     * 跳转到权限管理-left
     * @return
     */
    @RequestMapping("toPermissionLeft")
    public String toPermissionLeft() {
        return "sys/permission/permissionLeft";
    }

    /**
     * 跳转到权限管理-right
     * @return
     */
    @RequestMapping("toPermissionRight")
    public String toPermissionRight() {
        return "sys/permission/permissionRight";
    }

    /**
     * 跳转到角色管理
     * @return
     */
    @RequestMapping("toRoleInfo")
    public String toRoleInfo() {
        return "sys/role/RoleInfo";
    }

    /**
     * 跳转到用户管理页面
     * @return
     */
    @RequestMapping("toUserManager")
    public String toUserManager() {
        return "sys/user/userManager";
    }

    /**
     * 跳转到缓存管理页面
     * @return
     */
    @RequestMapping("toCacheManager")
    public String toCacheManager() {
        return "sys/cache/cacheManager";
    }

    /**
     * 跳转到个人资料页面
     * @return
     */
    @RequestMapping("toUserInfo")
    public String toUserInfo() {
        return "sys/user/userInfo";
    }

    /**
     * 跳转到修改密码页面
     * @return
     */
    @RequestMapping("toChangePwd")
    public String toChangePwd() {
        return "sys/user/changePwd";
    }

    /**
     * 跳转到请假单管理页面
     * @return
     */
    @RequestMapping("toLeaveManager")
    public String toLeaveManager() {
        return "sys/leave/leaveManager";    
    }

    /**
     * 跳转到流程管理页面
     * @return
     */
    @RequestMapping("toWorkFlowManager")
    public String toWorkFlowManager() {
        return "sys/workFlow/workFlowManager";
    }
}
