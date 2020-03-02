package com.timain.web.bus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timain.web.bus.mapper.GoodsMapper;
import com.timain.web.bus.mapper.SaleMapper;
import com.timain.web.bus.pojo.Goods;
import com.timain.web.bus.pojo.Sale;
import com.timain.web.bus.service.SaleService;
import com.timain.web.sys.pojo.User;
import com.timain.web.sys.utils.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/3/1 15:28
 */
@Service
public class SaleServiceImpl extends ServiceImpl<SaleMapper, Sale> implements SaleService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public boolean save(Sale entity) {
        Goods goods = this.goodsMapper.selectById(entity.getGoodsId());
        goods.setNumber(goods.getNumber() - entity.getNumber());
        entity.setSalesTime(new Date());
        User user = (User) LoginUtils.getSession().getAttribute("user");
        entity.setOperatePerson(user.getName());
        this.goodsMapper.updateById(goods);
        this.getBaseMapper().insert(entity);
        return super.save(entity);
    }

    @Override
    public boolean updateById(Sale entity) {
        Goods goods = this.goodsMapper.selectById(entity.getGoodsId());
        Sale sale = this.getBaseMapper().selectById(entity.getId());
        goods.setNumber(goods.getNumber() + sale.getNumber() - entity.getNumber());
        this.goodsMapper.updateById(goods);
        this.getBaseMapper().updateById(entity);
        return super.updateById(entity);
    }
}
