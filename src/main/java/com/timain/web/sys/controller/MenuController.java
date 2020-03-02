package com.timain.web.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timain.web.sys.common.Constants;
import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.common.ResultObj;
import com.timain.web.sys.common.TreeNode;
import com.timain.web.sys.pojo.Dept;
import com.timain.web.sys.pojo.Permission;
import com.timain.web.sys.pojo.User;
import com.timain.web.sys.service.PermissionService;
import com.timain.web.sys.service.RoleService;
import com.timain.web.sys.utils.LoginUtils;
import com.timain.web.sys.utils.TreeNodeUtils;
import com.timain.web.sys.vo.DeptVO;
import com.timain.web.sys.vo.PermissionVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 菜单管理控制器
 * @author yyf
 * @version 1.0
 * @date 2020/1/15 11:03
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    /**
     * 加载左边树结构
     * @param permissionVO
     * @return
     */
    @RequestMapping("loadIndexLeftMenuJson")
    public DataGridView loadIndexLeftMenuJson(PermissionVO permissionVO) {
        //查询所有可用菜单
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constants.TYPE_MENU);
        queryWrapper.eq("available", Constants.AVAILABLE_TRUE);
        //从session中获取user对象
        User user = (User) LoginUtils.getSession().getAttribute("user");
        List<Permission> permissionList = null;
        if (user.getType()==Constants.USER_TYPE_SUPER) {
            permissionList = permissionService.list(queryWrapper);
        } else {
            //根据用户ID查询角色ID集合
            List<Integer> ids = roleService.findUserRoleIdsByUid(user.getId());
            //声明权限ID
            Set<Integer> pids = new HashSet<>();
            for (Integer rid: ids) {
                List<Integer> permissionIds = roleService.findRolePermissionIdsByRid(rid);
                pids.addAll(permissionIds);
            }
            if (pids.size()>0) {
                queryWrapper.in("id", pids);
                permissionList = permissionService.list(queryWrapper);
            } else {
                permissionList = new ArrayList<>();
            }
        }
        //将从数据库查询出来的所有菜单add到树形结构中
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Permission p: permissionList) {
            Integer id = p.getId();
            Integer pId = p.getPId();
            String title = p.getTitle();
            String icon = p.getIcon();
            String href = p.getHref();
            Boolean spread = p.getOpen()==Constants.open_true?true:false;
            treeNodes.add(new TreeNode(id, pId, title, icon, href, spread));
        }
        List<TreeNode> nodes = TreeNodeUtils.build(treeNodes, 1);
        return new DataGridView(nodes);
    }
    
    /******    菜单管理开始   ******/


    /**
     * 加载菜单管理页面左边的json树结构
     * @param permissionVO
     * @return
     */
    @RequestMapping("loadMenuManagerLeftJson")
    public DataGridView loadMenuManagerLeftJson(PermissionVO permissionVO) {
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
     * 分页条件查询菜单信息
     * @param permissionVO
     * @return
     */
    @RequestMapping("loadAllMenu")
    public DataGridView loadAllMenuMenu(PermissionVO permissionVO) {
        IPage<Permission> page = new Page<>(permissionVO.getPage(),permissionVO.getLimit());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(permissionVO.getTitle()), "title", permissionVO.getTitle());
        queryWrapper.eq(permissionVO.getId()!=null, "id", permissionVO.getId()).or().eq(permissionVO.getId()!=null, "pId", permissionVO.getId());
        queryWrapper.eq("type", Constants.TYPE_MENU);
        this.permissionService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加菜单信息
     * @param permissionVO
     * @return
     */
    @RequestMapping("addMenu")
    public ResultObj addMenu(PermissionVO permissionVO) {
        try {
            permissionVO.setType(Constants.TYPE_MENU);
            this.permissionService.save(permissionVO);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改菜单信息
     * @param permissionVO
     * @return
     */
    @RequestMapping("updateMenu")
    public ResultObj updateMenu(PermissionVO permissionVO) {
        try {
            this.permissionService.updateById(permissionVO);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除菜单信息
     * @param id
     * @return
     */
    @RequestMapping("deleteMenu")
    public ResultObj deleteMenu(Integer id) {
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
     * 判断该菜单是否有下级菜单
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
    /******    菜单管理结束   ******/
}
