package com.timain.web.bus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 业务控制器
 * @author yyf
 * @version 1.0
 * @date 2020/2/27 19:59
 */
@Controller
@RequestMapping("/bus")
public class BusController {

    /**
     * 跳转到客户管理页面
     * @return
     */
    @RequestMapping("toCustomerManager")
    public String toCustomerManager() {
        return "bus/customer/customerManager";
    }

    /**
     * 跳转到供应商管理页面
     * @return
     */
    @RequestMapping("toProviderManager")
    public String toProviderManager() {
        return "bus/provider/providerManager";
    }

    /**
     * 跳转到商品管理页面
     * @return
     */
    @RequestMapping("toGoodsManager")
    public String toGoodsManager() {
        return "bus/goods/goodsManager";
    }

    /**
     * 跳转到商品进货页面
     * @return
     */
    @RequestMapping("toInportManager")
    public String toInportManager() {
        return "bus/goodsInport/inportManager";
    }

    /**
     * 跳转到商品退货页面
     * @return
     */
    @RequestMapping("toOutportManager")
    public String toOutportManager() {
        return "bus/outport/outPortManager";
    }

    /**
     * 跳转到商品销售页面
     * @return
     */
    @RequestMapping("toSaleManager")
    public String toSaleManager() {
        return "bus/sale/saleManager";
    }

    /**
     * 跳转到销售退货页面
     * @return
     */
    @RequestMapping("toSaleBackManager")
    public String toSaleBackManager() {
        return "bus/saleback/saleBackManager";
    }

}
