package com.timain.web.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timain.web.sys.common.Constants;
import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.common.ResultObj;
import com.timain.web.sys.common.TreeNode;
import com.timain.web.sys.pojo.Permission;
import com.timain.web.sys.service.PermissionService;
import com.timain.web.sys.vo.PermissionVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/15 10:59
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {
    
    @Autowired
    private PermissionService permissionService;


    /******    权限管理开始   ******/


    /**
     * 加载权限管理页面左边的json树结构
     * @param permissionVO
     * @return
     */
    @RequestMapping("loadPermissionManagerLeftJson")
    public DataGridView loadPermissionManagerLeftJson(PermissionVO permissionVO) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constants.TYPE_MENU);
        List<Permission> permissionList = this.permissionService.list(queryWrapper);
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Permission permission: permissionList) {
            Boolean spread = permission.getOpen()==1?true:false;
            treeNodes.add(new TreeNode(permission.getId(), permission.getPId(), permission.getTitle(),spread));
        }
        return new DataGridView(treeNodes);
    }

    /**
     * 分页条件查询权限信息
     * @param permissionVO
     * @return
     */
    @RequestMapping("loadAllPermission")
    public DataGridView loadAllPermissionPermission(PermissionVO permissionVO) {
        IPage<Permission> page = new Page<>(permissionVO.getPage(),permissionVO.getLimit());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constants.TYPE_PERMISSION);
        queryWrapper.like(StringUtils.isNotBlank(permissionVO.getTitle()), "title", permissionVO.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(permissionVO.getPerCode()), "perCode", permissionVO.getPerCode());
        queryWrapper.eq(permissionVO.getId()!=null, "pId", permissionVO.getId());
        this.permissionService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加权限信息
     * @param permissionVO
     * @return
     */
    @RequestMapping("addPermission")
    public ResultObj addPermission(PermissionVO permissionVO) {
        try {
            permissionVO.setType(Constants.TYPE_PERMISSION);
            this.permissionService.save(permissionVO);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改权限信息
     * @param permissionVO
     * @return
     */
    @RequestMapping("updatePermission")
    public ResultObj updatePermission(PermissionVO permissionVO) {
        try {
            this.permissionService.updateById(permissionVO);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除权限信息
     * @param id
     * @return
     */
    @RequestMapping("deletePermission")
    public ResultObj deletePermission(Integer id) {
        try {
            this.permissionService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 加载最大的排序码
     * @return
     */
    @RequestMapping("getOrderNum")
    public Map<String,Object> getOrderNum(){
        Map<String, Object> map=new HashMap<String, Object>();
        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("orderNum");
        IPage<Permission> page=new Page<>(1, 1);
        List<Permission> list = this.permissionService.page(page, queryWrapper).getRecords();
        if(list.size()>0) {
            map.put("value", list.get(0).getOrderNum()+1);
        }else {
            map.put("value", 1);
        }
        return map;
    }

    /**
     * 判断该权限是否有下级权限
     * @param permissionVO
     * @return
     */
    @RequestMapping("checkChildrenNode")
    public Boolean checkChildrenNode(PermissionVO permissionVO) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pId", permissionVO.getId());
        List<Permission> permissionList = this.permissionService.list(queryWrapper);
        if (permissionList.size() > 0) {
            return true;
        }
        return false;
    }


    /******    权限管理结束   ******/
}
