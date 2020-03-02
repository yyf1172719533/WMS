package com.timain.web.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.timain.web.sys.pojo.Role;

import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/2/18 11:44
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据角色ID查询菜单和权限ID
     * @param roleId
     * @return
     */
    List<Integer> findRolePermissionIdsByRid(Integer roleId);

    /**
     * 保存分配角色的权限
     * @param rid
     * @param ids
     */
    void saveRolePermission(Integer rid, Integer[] ids);

    /**
     * 根据用户ID查询用户所拥有的角色ID
     * @param id
     * @return
     */
    List<Integer> findUserRoleIdsByUid(Integer id);
}
