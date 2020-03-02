package com.timain.web.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timain.web.bus.pojo.Customer;
import com.timain.web.bus.service.CustomerService;
import com.timain.web.bus.vo.CustomerVO;
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
 * @date 2020/2/27 19:59
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 查询所有客户信息
     * @param customerVO
     * @return
     */
    @RequestMapping("loadAllCustomer")
    public DataGridView loadAllCustomer(CustomerVO customerVO) {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        IPage<Customer> page = new Page<>(customerVO.getPage(), customerVO.getLimit());
        queryWrapper.like(StringUtils.isNotBlank(customerVO.getCustomerName()), "customerName", customerVO.getCustomerName());
        queryWrapper.like(StringUtils.isNotBlank(customerVO.getTelephone()), "telephone", customerVO.getTelephone());
        queryWrapper.like(StringUtils.isNotBlank(customerVO.getConnectionPerson()), "connectionPerson", customerVO.getConnectionPerson());
        this.customerService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加客户
     * @param customerVO
     * @return
     */
    @RequestMapping("addCustomer")
    public ResultObj addCustomer(CustomerVO customerVO) {
        try {
            this.customerService.save(customerVO);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改客户
     * @param customerVO
     * @return
     */
    @RequestMapping("updateCustomer")
    public ResultObj updateCustomer(CustomerVO customerVO) {
        try {
            this.customerService.updateById(customerVO);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除客户
     * @param id
     * @return
     */
    @RequestMapping("delOneCustomer")
    public ResultObj deleteCustomer(Integer id) {
        try {
            this.customerService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除
     * @param customerVO
     * @return
     */
    @RequestMapping("delMoreCustomer")
    public ResultObj delMoreCustomer(CustomerVO customerVO) {
        try {
            Collection<Integer> idsList = new ArrayList<>();
            for (Integer id: customerVO.getIds()) {
                idsList.add(id);
            }
            this.customerService.removeByIds(idsList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 加载客户的下拉列表
     */
    @RequestMapping("loadAllCustomerToSelect")
    public DataGridView loadAllCustomerToSelect() {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constants.AVAILABLE_TRUE);
        List<Customer> list = this.customerService.list(queryWrapper);
        return new DataGridView(list);
    }
}
