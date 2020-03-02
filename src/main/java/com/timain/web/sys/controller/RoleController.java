package com.timain.web.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timain.web.sys.common.Constants;
import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.common.ResultObj;
import com.timain.web.sys.common.TreeNode;
import com.timain.web.sys.pojo.Permission;
import com.timain.web.sys.pojo.Role;
import com.timain.web.sys.service.PermissionService;
import com.timain.web.sys.service.RoleService;
import com.timain.web.sys.vo.RoleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/2/18 11:51
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    
    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 分页条件查询角色信息
     * @param roleVO
     * @return
     */
    @RequestMapping("loadAllRole")
    public DataGridView loadAllRole(RoleVO roleVO) {
        IPage<Role> page = new Page<>(roleVO.getPage(), roleVO.getLimit());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(roleVO.getName()), "name", roleVO.getName());
        queryWrapper.like(StringUtils.isNotBlank(roleVO.getRemark()), "remark", roleVO.getRemark());
        queryWrapper.eq(null!=roleVO.getAvailable(),"available",roleVO.getAvailable());
        //queryWrapper.orderByDesc("createTime");
        this.roleService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加角色
     * @param roleVO
     * @return
     */
    @RequestMapping("addRole")
    public ResultObj addRole(RoleVO roleVO) {
        try {
            roleVO.setCreateTime(new Date());
            this.roleService.save(roleVO);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改角色
     * @param roleVO
     * @return
     */
    @RequestMapping("updateRole")
    public ResultObj updateRole(RoleVO roleVO) {
        try {
            this.roleService.updateById(roleVO);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 根据id删除单个角色
     * @param id
     * @return
     */
    @RequestMapping("delOneRole")
    public ResultObj delOneRole(Integer id) {
        try {
            this.roleService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 根据角色ID查询菜单和权限的json数据
     * @param roleId
     * @return
     */
    @RequestMapping("initPermissionByRoleId")
    public DataGridView initPermissionByRoleId(Integer roleId) {
        //查询所有可用的菜单和权限
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constants.AVAILABLE_TRUE);
        List<Permission> permissionList = permissionService.list(queryWrapper);
        //根据角色ID查询当前角色所拥有的菜单和权限ID
        List<Integer> ids = this.roleService.findRolePermissionIdsByRid(roleId);
        List<Permission> currentPermissions = null;
        if (ids.size()>0) {
            //根据查询出的id查询菜单和权限数据
            queryWrapper.in(ids.size()>0, "id",ids);
            currentPermissions = permissionService.list(queryWrapper);
        } else {
            currentPermissions = new ArrayList<>();
        }
        //构造TreeNode
        List<TreeNode> nodes = new ArrayList<>();
        for (Permission p1: permissionList) {
            String checkArr = "0";
            for (Permission p2: currentPermissions) {
                if (p1.getId()==p2.getId()) {
                    checkArr = "1";
                    break;
                }
            }
            Boolean spread = (p1.getOpen()== null || p1.getOpen()==1)?true:false;
            nodes.add(new TreeNode(p1.getId(), p1.getPId(), p1.getTitle(), spread, checkArr));
        }
        return new DataGridView(nodes);
    }

    /**
     * 保存角色和菜单权限之间的关系
     * @param rid
     * @param ids
     * @return
     */
    @RequestMapping("saveRolePermission")
    public ResultObj saveRolePermission(Integer rid, Integer[] ids) {
        try {
            this.roleService.saveRolePermission(rid,ids);
            return ResultObj.DISPATCH_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DISPATCH_ERROR;
        }
    }
    
}
