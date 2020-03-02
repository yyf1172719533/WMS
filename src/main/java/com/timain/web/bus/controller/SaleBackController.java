package com.timain.web.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timain.web.bus.pojo.*;
import com.timain.web.bus.service.CustomerService;
import com.timain.web.bus.service.GoodsService;
import com.timain.web.bus.service.SaleBackService;
import com.timain.web.bus.vo.SaleBackVO;
import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/3/1 18:07
 */
@RestController
@RequestMapping("/saleback")
public class SaleBackController {

    @Autowired
    private SaleBackService saleBackService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CustomerService customerService;

    /**
     * 加载所有取消销售信息
     * @param saleBackVO
     * @return
     */
    @RequestMapping("loadAllSaleBack")
    public DataGridView loadAllSaleBack(SaleBackVO saleBackVO) {
        QueryWrapper<SaleBack> queryWrapper = new QueryWrapper<>();
        IPage<SaleBack> page = new Page<>(saleBackVO.getPage(), saleBackVO.getLimit());
        queryWrapper.eq(saleBackVO.getCustomerId()!=null && saleBackVO.getCustomerId()!=0, "customerId", saleBackVO.getCustomerId());
        queryWrapper.eq(saleBackVO.getGoodsId()!=null && saleBackVO.getGoodsId()!=0, "goodsId", saleBackVO.getGoodsId());
        queryWrapper.ge(saleBackVO.getStartTime()!=null, "salesBackTime", saleBackVO.getStartTime());
        queryWrapper.le(saleBackVO.getEndTime()!=null, "salesBackTime", saleBackVO.getEndTime());
        this.saleBackService.page(page, queryWrapper);
        for (SaleBack saleBack: page.getRecords()) {
            Goods goods = this.goodsService.getById(saleBack.getGoodsId());
            if (null!=goods) {
                saleBack.setGoodsName(goods.getGoodsName());
                saleBack.setSize(goods.getSize());
            }
            Customer customer = this.customerService.getById(saleBack.getCustomerId());
            if (null!=customer) {
                saleBack.setCustomerName(customer.getCustomerName());
            }
        }
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加取消销售信息
     * @param id
     * @param number
     * @param remark
     * @return
     */
    @RequestMapping("addSaleBack")
    public ResultObj addSaleBack(Integer id, Integer number, String remark) {
        try {
            this.saleBackService.addSaleBack(id, number, remark);
            return ResultObj.BACK_GOODS_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.BACK_GOODS_ERROR;
        }
    }
}
