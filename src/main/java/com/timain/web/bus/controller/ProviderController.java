package com.timain.web.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timain.web.bus.pojo.Provider;
import com.timain.web.bus.service.ProviderService;
import com.timain.web.bus.vo.ProviderVO;
import com.timain.web.sys.common.Constants;
import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.common.ResultObj;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/2/28 18:01
 */
@RestController
@RequestMapping("/provider")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    /**
     * 查询所有供应商信息
     * @param providerVO
     * @return
     */
    @RequestMapping("loadAllProvider")
    public DataGridView loadAllProvider(ProviderVO providerVO) {
        QueryWrapper<Provider> queryWrapper = new QueryWrapper<>();
        IPage<Provider> page = new Page<>(providerVO.getPage(), providerVO.getLimit());
        queryWrapper.like(StringUtils.isNotBlank(providerVO.getProviderName()), "providerName", providerVO.getProviderName());
        queryWrapper.like(StringUtils.isNotBlank(providerVO.getTelephone()), "telephone", providerVO.getTelephone());
        queryWrapper.like(StringUtils.isNotBlank(providerVO.getConnectionPerson()), "connectionPerson", providerVO.getConnectionPerson());
        this.providerService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加供应商
     * @param providerVO
     * @return
     */
    @RequestMapping("addProvider")
    public ResultObj addProvider(ProviderVO providerVO) {
        try {
            this.providerService.save(providerVO);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改供应商
     * @param providerVO
     * @return
     */
    @RequestMapping("updateProvider")
    public ResultObj updateProvider(ProviderVO providerVO) {
        try {
            this.providerService.updateById(providerVO);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除供应商
     * @param id
     * @return
     */
    @RequestMapping("delOneProvider")
    public ResultObj deleteProvider(Integer id) {
        try {
            this.providerService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除
     * @param providerVO
     * @return
     */
    @RequestMapping("delMoreProvider")
    public ResultObj delMoreProvider(ProviderVO providerVO) {
        try {
            Collection<Integer> idsList = new ArrayList<>();
            for (Integer id: providerVO.getIds()) {
                idsList.add(id);
            }
            this.providerService.removeByIds(idsList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 加载条件查询中供应商的下拉列表
     * @return
     */
    @RequestMapping("loadAllProviderToSelect")
    public DataGridView loadAllProviderToSelect() {
        QueryWrapper<Provider> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constants.AVAILABLE_TRUE);
        List<Provider> providerList = this.providerService.list(queryWrapper);
        return new DataGridView(providerList);
    }
}
