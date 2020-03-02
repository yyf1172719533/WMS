package com.timain.web.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timain.web.sys.mapper.RoleMapper;
import com.timain.web.sys.pojo.Role;
import com.timain.web.sys.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/2/18 11:45
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public boolean removeById(Serializable id) {
        //根据角色ID删除sys_role_permission中的数据
        this.getBaseMapper().deleteRolePermissionByRID(id);
        //根据角色ID删除sys_role_user中的数据
        this.getBaseMapper().deleteRoleUserByRID(id);
        return super.removeById(id);
    }


    /**
     * 根据角色ID查询菜单和权限ID
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Integer> findRolePermissionIdsByRid(Integer roleId) {
        return this.getBaseMapper().queryRolePermissionIdsByRid(roleId);
    }

    /**
     * 保存分配角色的权限数据
     * @param rid
     * @param ids
     */
    @Override
    public void saveRolePermission(Integer rid, Integer[] ids) {
        //保存数据之前先删除
        this.getBaseMapper().deleteRolePermissionByRID(rid);
        if (null!=ids && ids.length>0) {
            for (Integer pId: ids) {
                this.getBaseMapper().saveRolePermission(rid, pId);
            }
        }
    }

    /**
     * 根据用户ID查询用户所拥有的角色ID集合
     * @param id
     * @return
     */
    @Override
    public List<Integer> findUserRoleIdsByUid(Integer id) {
        return this.getBaseMapper().queryUserRoleIdsByUid(id);
    }
}
