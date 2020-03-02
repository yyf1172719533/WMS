package com.timain.web.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timain.web.bus.pojo.Goods;
import com.timain.web.bus.pojo.Provider;
import com.timain.web.bus.service.GoodsService;
import com.timain.web.bus.service.ProviderService;
import com.timain.web.bus.vo.GoodsVO;
import com.timain.web.sys.common.Constants;
import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.common.ResultObj;
import com.timain.web.sys.utils.UADFileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/2/28 19:27
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ProviderService providerService;

    /**
     * 查询所有商品信息
     * @param goodsVO
     * @return
     */
    @RequestMapping("loadAllGoods")
    public DataGridView loadAllGoods(GoodsVO goodsVO) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        IPage<Goods> page = new Page<>(goodsVO.getPage(), goodsVO.getLimit());
        queryWrapper.eq(goodsVO.getProviderId()!=null&&goodsVO.getProviderId()!=0, "providerId", goodsVO.getProviderId());
        queryWrapper.like(StringUtils.isNotBlank(goodsVO.getGoodsName()), "goodsName", goodsVO.getGoodsName());
        queryWrapper.like(StringUtils.isNotBlank(goodsVO.getProductCode()), "productCode", goodsVO.getProductCode());
        queryWrapper.like(StringUtils.isNotBlank(goodsVO.getPromitCode()), "promitCode", goodsVO.getPromitCode());
        queryWrapper.like(StringUtils.isNotBlank(goodsVO.getDescription()), "description", goodsVO.getDescription());
        queryWrapper.like(StringUtils.isNotBlank(goodsVO.getSize()), "size", goodsVO.getSize());
        this.goodsService.page(page, queryWrapper);
        for (Goods goods : page.getRecords()) {
            Provider provider = this.providerService.getById(goods.getProviderId());
            if (null!=provider) {
                goods.setProviderName(provider.getProviderName());
            }
        }
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加商品
     * @param goodsVO
     * @return
     */
    @RequestMapping("addGoods")
    public ResultObj addGoods(GoodsVO goodsVO) {
        try {
            if (null!=goodsVO.getGoodsImg() && goodsVO.getGoodsImg().endsWith("_temp")) {
                String newName = UADFileUtils.changeFileName(goodsVO.getGoodsImg());
                goodsVO.setGoodsImg(newName);
            }
            this.goodsService.save(goodsVO);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改商品
     * @param goodsVO
     * @return
     */
    @RequestMapping("updateGoods")
    public ResultObj updateGoods(GoodsVO goodsVO) {
        try {
            //判断是否是默认图片
            if (!(goodsVO.getGoodsImg().equals(Constants.DEFAULT_GOODS_IMG) && null!=goodsVO.getGoodsImg())) {
                //判断是否是后缀为_temp的图片
                if (goodsVO.getGoodsImg().endsWith("_temp")) {
                    //更换文件名
                    String newName = UADFileUtils.changeFileName(goodsVO.getGoodsImg());
                    goodsVO.setGoodsImg(newName);
                    //根据ID查询该商品的原图片并同时删除原图片
                    String oldPath = this.goodsService.getById(goodsVO.getId()).getGoodsImg();
                    UADFileUtils.removeByPath(oldPath);
                }
            }
            this.goodsService.updateById(goodsVO);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除商品
     * @param id
     * @param goodsImg
     * @return
     */
    @RequestMapping("deleteGoods")
    public ResultObj deleteGoods(Integer id, String goodsImg) {
        try {
            UADFileUtils.removeByPath(goodsImg);
            this.goodsService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 加载查询条件中商品选择的下拉列表
     * @return
     */
    @RequestMapping("loadAllGoodsToSelect")
    public DataGridView loadAllGoodsToSelect() {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constants.AVAILABLE_TRUE);
        List<Goods> goodsList = this.goodsService.list(queryWrapper);
        return new DataGridView(goodsList);
    }

    /**
     * 根据供应商ID查询所有商品
     * @param providerId
     * @return
     */
    @RequestMapping("loadGoodsByProviderId")
    public DataGridView loadGoodsByProviderId(Integer providerId) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constants.AVAILABLE_TRUE);
        queryWrapper.eq(providerId!=null && providerId!=0, "providerId", providerId);
        List<Goods> list = this.goodsService.list(queryWrapper);
        return new DataGridView(list);
    }
}
