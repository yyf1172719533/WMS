package com.timain.web.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timain.web.sys.pojo.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/2/18 11:43
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据角色ID删除sys_role_permission中的数据
     * @param id
     */
    void deleteRolePermissionByRID(Serializable id);

    /**
     * 根据角色ID删除sys_role_user中的数据
     * @param id
     */
    void deleteRoleUserByRID(Serializable id);

    /**
     * 根据角色ID查询菜单和权限ID
     * @param roleId
     * @return
     */
    List<Integer> queryRolePermissionIdsByRid(Integer roleId);

    /**
     * 保存分配给角色的权限数据
     * @param roleId
     * @param pId
     */
    void saveRolePermission(@Param("rid") Integer roleId, @Param("pid") Integer pId);

    /**
     * 根据用户ID删除用户角色中间表的数据
     * @param id
     */
    void deleteRoleUserByUID(Serializable id);

    /**
     * 根据用户ID查询用户所拥有的角色ID集合
     * @param id
     * @return
     */
    List<Integer> queryUserRoleIdsByUid(Integer id);
}
