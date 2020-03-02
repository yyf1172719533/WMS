package com.timain.web.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timain.web.bus.pojo.Customer;
import com.timain.web.bus.pojo.Goods;
import com.timain.web.bus.pojo.Sale;
import com.timain.web.bus.service.CustomerService;
import com.timain.web.bus.service.GoodsService;
import com.timain.web.bus.service.SaleService;
import com.timain.web.bus.vo.SaleVO;
import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/3/1 15:27
 */
@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 加载所有销售商品信息
     * @param saleVO
     * @return
     */
    @RequestMapping("loadAllsale")
    public DataGridView loadAllsale(SaleVO saleVO) {
        QueryWrapper<Sale> queryWrapper = new QueryWrapper<>();
        IPage<Sale> page = new Page<>(saleVO.getPage(), saleVO.getLimit());
        queryWrapper.eq(saleVO.getCustomerId()!=null && saleVO.getCustomerId()!=0, "customerId", saleVO.getCustomerId());
        queryWrapper.eq(saleVO.getGoodsId()!=null && saleVO.getGoodsId()!=0, "goodsId", saleVO.getGoodsId());
        queryWrapper.ge(saleVO.getStartTime()!=null, "salesTime", saleVO.getStartTime());
        queryWrapper.le(saleVO.getEndTime()!=null, "salesTime", saleVO.getEndTime());
        queryWrapper.orderByDesc("salesTime");
        this.saleService.page(page, queryWrapper);
        for (Sale sale : page.getRecords()) {
            Customer customer = this.customerService.getById(sale.getCustomerId());
            if (null!=customer) {
                sale.setCustomerName(customer.getCustomerName());
            }
            Goods goods = this.goodsService.getById(sale.getGoodsId());
            if (null!=goods) {
                sale.setGoodsName(goods.getGoodsName());
                sale.setSize(goods.getSize());
            }
        }
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加销售信息
     * @param saleVO
     * @return
     */
    @RequestMapping("addSale")
    public ResultObj addSale(SaleVO saleVO) {
        try {
            this.saleService.save(saleVO);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改销售信息
     * @param saleVO
     * @return
     */
    @RequestMapping("updateSale")
    public ResultObj updateSale(SaleVO saleVO) {
        try {
            this.saleService.updateById(saleVO);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除销售信息
     * @param id
     * @return
     */
    @RequestMapping("deleteSale")
    public ResultObj deleteSale(Integer id) {
        try {
            this.saleService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
