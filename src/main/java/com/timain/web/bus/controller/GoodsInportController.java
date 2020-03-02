package com.timain.web.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timain.web.bus.pojo.Goods;
import com.timain.web.bus.pojo.GoodsInport;
import com.timain.web.bus.pojo.Provider;
import com.timain.web.bus.service.GoodsInportService;
import com.timain.web.bus.service.GoodsService;
import com.timain.web.bus.service.ProviderService;
import com.timain.web.bus.vo.GoodsInportVO;
import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.common.ResultObj;
import com.timain.web.sys.pojo.User;
import com.timain.web.sys.utils.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/2/29 19:15
 */
@RestController
@RequestMapping("/inport")
public class GoodsInportController {

    @Autowired
    private GoodsInportService goodsInportService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 查询所有的商品进货信息
     * @param inportVO
     * @return
     */
    @RequestMapping("loadAllGoodsInport")
    public DataGridView loadAllGoodsInport(GoodsInportVO inportVO) {
        QueryWrapper<GoodsInport> queryWrapper = new QueryWrapper<>();
        IPage<GoodsInport> page = new Page<>(inportVO.getPage(), inportVO.getLimit());
        queryWrapper.eq(inportVO.getProviderId()!=null && inportVO.getProviderId()!=0, "providerId", inportVO.getProviderId());
        queryWrapper.eq(inportVO.getGoodsId()!=null && inportVO.getGoodsId()!=0, "goodsId", inportVO.getGoodsId());
        queryWrapper.ge(inportVO.getStartTime()!=null, "inportTime", inportVO.getStartTime());
        queryWrapper.le(inportVO.getEndTime()!=null, "inportTime", inportVO.getEndTime());
        queryWrapper.orderByDesc("inportTime");
        this.goodsInportService.page(page, queryWrapper);
        for (GoodsInport goodsInport: page.getRecords()) {
            Provider provider = this.providerService.getById(goodsInport.getProviderId());
            if (null!=provider) {
                goodsInport.setProviderName(provider.getProviderName());
            }
            Goods goods = this.goodsService.getById(goodsInport.getGoodsId());
            if (null!=goods) {
                goodsInport.setGoodsName(goods.getGoodsName());
                goodsInport.setSize(goods.getSize());
            }
        }
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加商品进货信息
     * @param inportVO
     * @return
     */
    @RequestMapping("addGoodsInport")
    public ResultObj addGoodsInport(GoodsInportVO inportVO) {
        try {
            inportVO.setInportTime(new Date());
            User user = (User) LoginUtils.getSession().getAttribute("user");
            inportVO.setOperatePerson(user.getName());
            this.goodsInportService.save(inportVO);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改进货信息
     * @param inportVO
     * @return
     */
    @RequestMapping("updateGoodsInport")
    public ResultObj updateGoodsInport(GoodsInportVO inportVO) {
        try {
            this.goodsInportService.updateById(inportVO);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除进货信息
     * @param id
     * @return
     */
    @RequestMapping("deleteGoodsInport")
    public ResultObj deleteGoodsInport(Integer id) {
        try {
            this.goodsInportService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
