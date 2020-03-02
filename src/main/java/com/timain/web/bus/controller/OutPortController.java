package com.timain.web.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timain.web.bus.pojo.Goods;
import com.timain.web.bus.pojo.OutPort;
import com.timain.web.bus.pojo.Provider;
import com.timain.web.bus.service.GoodsService;
import com.timain.web.bus.service.OutPortService;
import com.timain.web.bus.service.ProviderService;
import com.timain.web.bus.vo.OutPortVO;
import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/2/29 22:59
 */
@RestController
@RequestMapping("/outport")
public class OutPortController {

    @Autowired
    private OutPortService outPortService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ProviderService providerService;

    /**
     * 商品退货
     * @param id
     * @param number
     * @param remark
     * @return
     */
    @RequestMapping("addOutPort")
    public ResultObj addOutPort(Integer id, Integer number, String remark) {
        try {
            this.outPortService.addOutPort(id, number, remark);
            return ResultObj.BACK_GOODS_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.BACK_GOODS_ERROR;
        }
    }

    /**
     * 查询所有退货信息
     * @param outPortVO
     * @return
     */
    @RequestMapping("loadAllGoodsOutport")
    public DataGridView loadAllGoodsOutport(OutPortVO outPortVO) {
        QueryWrapper<OutPort> queryWrapper = new QueryWrapper<>();
        IPage<OutPort> page = new Page<>(outPortVO.getPage(), outPortVO.getLimit());
        queryWrapper.eq(outPortVO.getProviderId()!=null && outPortVO.getProviderId()!=0, "providerId", outPortVO.getProviderId());
        queryWrapper.eq(outPortVO.getGoodsId()!=null && outPortVO.getGoodsId()!=0, "goodsId", outPortVO.getGoodsId());
        queryWrapper.ge(outPortVO.getStartTime()!=null, "outPutTime", outPortVO.getStartTime());
        queryWrapper.le(outPortVO.getEndTime()!=null, "outPutTime", outPortVO.getEndTime());
        this.outPortService.page(page, queryWrapper);
        for (OutPort outPort: page.getRecords()) {
            Goods goods = this.goodsService.getById(outPort.getGoodsId());
            if (null!=goods) {
                outPort.setGoodsName(goods.getGoodsName());
                outPort.setSize(goods.getSize());
            }
            Provider provider = this.providerService.getById(outPort.getProviderId());
            if (null!=provider) {
                outPort.setProviderName(provider.getProviderName());
            }
        }
        return new DataGridView(page.getTotal(), page.getRecords());
    }
}
