package com.timain.web.bus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.timain.web.bus.pojo.OutPort;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/2/28 19:28
 */
public interface OutPortService extends IService<OutPort> {

    /**
     * 商品退货
     * @param id  进货ID
     * @param number  退货数量
     * @param remark  退货备注
     */
    void addOutPort(Integer id, Integer number, String remark);
}
