package com.timain.web.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.common.ResultObj;
import com.timain.web.sys.common.TreeNode;
import com.timain.web.sys.pojo.Dept;
import com.timain.web.sys.service.DeptService;
import com.timain.web.sys.vo.DeptVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/17 8:47
 */
@RestController
@RequestMapping("dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 加载部门管理页面左边的json树结构
     * @param deptVO
     * @return
     */
    @RequestMapping("loadDeptManagerLeftJson")
    public DataGridView loadDeptManagerLeftJson(DeptVO deptVO) {
        List<Dept> deptList = this.deptService.list();
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Dept dept: deptList) {
            Boolean spread = dept.getOpen()==1?true:false;
            treeNodes.add(new TreeNode(dept.getId(), dept.getPId(), dept.getTitle(),spread));
        }
        return new DataGridView(treeNodes);
    }

    /**
     * 分页条件查询部门信息
     * @param deptVO
     * @return
     */
    @RequestMapping("loadAllDept")
    public DataGridView loadAllDept(DeptVO deptVO) {
        IPage<Dept> page = new Page<>(deptVO.getPage(),deptVO.getLimit());
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(deptVO.getTitle()), "title", deptVO.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(deptVO.getAddress()), "address", deptVO.getAddress());
        queryWrapper.like(StringUtils.isNotBlank(deptVO.getRemark()), "remark", deptVO.getRemark());
        queryWrapper.eq(deptVO.getId()!=null, "id", deptVO.getId()).or().eq(deptVO.getId()!=null, "pId", deptVO.getId());
        this.deptService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加部门信息
     * @param deptVO
     * @return
     */
    @RequestMapping("addDept")
    public ResultObj addDept(DeptVO deptVO) {
        try {
            deptVO.getPId();
            deptVO.setCreateTime(new Date());
            this.deptService.save(deptVO);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改部门信息
     * @param deptVO
     * @return
     */
    @RequestMapping("updateDept")
    public ResultObj updateDept(DeptVO deptVO) {
        try {
            this.deptService.updateById(deptVO);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除部门信息
     * @param id
     * @return
     */
    @RequestMapping("deleteDept")
    public ResultObj deleteDept(Integer id) {
        try {
            this.deptService.removeById(id);
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

        QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("orderNum");
        IPage<Dept> page=new Page<>(1, 1);
        List<Dept> list = this.deptService.page(page, queryWrapper).getRecords();
        if(list.size()>0) {
            map.put("value", list.get(0).getOrderNum()+1);
        }else {
            map.put("value", 1);
        }
        return map;
    }

    /**
     * 判断该部门是否有下级部门
     * @param deptVO
     * @return
     */
    @RequestMapping("checkChildrenNode")
    public Boolean checkChildrenNode(DeptVO deptVO) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pId", deptVO.getId());
        List<Dept> deptList = this.deptService.list(queryWrapper);
        if (deptList.size() > 0) {
            return true;
        }
        return false;
    }
}
