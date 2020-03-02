package com.timain.web.sys.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.timain.web.sys.common.ActiverUser;
import com.timain.web.sys.common.Constants;
import com.timain.web.sys.pojo.Permission;
import com.timain.web.sys.pojo.User;
import com.timain.web.sys.service.PermissionService;
import com.timain.web.sys.service.RoleService;
import com.timain.web.sys.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/14 20:57
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private PermissionService permissionService;

    @Autowired
    @Lazy
    private RoleService roleService;

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        ActiverUser activerUser = (ActiverUser) principalCollection.getPrimaryPrincipal();
        User user = activerUser.getUser();
        List<String> permissions = activerUser.getPermissions();
        if (user.getType()==Constants.USER_TYPE_SUPER) {
            authorizationInfo.addStringPermission("*:*");
        } else {
            if (null!=permissions && permissions.size()>0) {
                authorizationInfo.addStringPermissions(permissions);
            }
        }
        return authorizationInfo;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("loginName",token.getPrincipal().toString());
        User user = userService.getOne(wrapper);
        if (null!=user) {
            ActiverUser activerUser = new ActiverUser();

            activerUser.setUser(user);

            //关联权限
            QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("type", Constants.TYPE_PERMISSION);
            queryWrapper.eq("available", Constants.AVAILABLE_TRUE);

            List<Integer> rids = roleService.findUserRoleIdsByUid(user.getId());
            Set<Integer> pids = new HashSet<>();
            for (Integer rid: rids) {
                List<Integer> pid = roleService.findRolePermissionIdsByRid(rid);
                pids.addAll(pid);
            }
            List<Permission> list = new ArrayList<>();
            if (pids.size()>0) {
                queryWrapper.in("id", pids);
                list = permissionService.list(queryWrapper);
            }
            List<String> percodes = new ArrayList<>();
            for (Permission permission: list) {
                percodes.add(permission.getPerCode());
            }
            //放进来
            activerUser.setPermissions(percodes);

            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activerUser, user.getPwd(), credentialsSalt, this.getName());
            return info;
        }
        return null;
    }
}
