package com.timain.web.bus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timain.web.bus.mapper.GoodsInportMapper;
import com.timain.web.bus.mapper.GoodsMapper;
import com.timain.web.bus.pojo.Goods;
import com.timain.web.bus.pojo.GoodsInport;
import com.timain.web.bus.service.GoodsInportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/2/29 19:19
 */
@Service
@Transactional
public class GoodsInportServiceImpl extends ServiceImpl<GoodsInportMapper, GoodsInport> implements GoodsInportService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public boolean save(GoodsInport entity) {
        Goods goods = goodsMapper.selectById(entity.getGoodsId());
        goods.setNumber(goods.getNumber() + entity.getNumber());
        goodsMapper.updateById(goods);
        return super.save(entity);
    }

    @Override
    public boolean updateById(GoodsInport entity) {
        //根据进货ID查询进货信息
        GoodsInport inport = this.baseMapper.selectById(entity.getId());
        //根据商品ID查询商品信息
        Goods goods = goodsMapper.selectById(entity.getGoodsId());
        //计算修改后的商品库存量
        goods.setNumber(goods.getNumber() - inport.getNumber() + entity.getNumber());
        goodsMapper.updateById(goods);
        return super.updateById(entity);
    }
}
