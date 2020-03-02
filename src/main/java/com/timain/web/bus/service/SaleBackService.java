package com.timain.web.bus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.timain.web.bus.pojo.SaleBack;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/2/28 19:28
 */
public interface SaleBackService extends IService<SaleBack> {

    /**
     * 添加取消销售信息
     * @param id
     * @param number
     * @param remark
     */
    void addSaleBack(Integer id, Integer number, String remark);
}
