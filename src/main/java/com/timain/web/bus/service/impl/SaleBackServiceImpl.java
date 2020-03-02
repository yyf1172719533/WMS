package com.timain.web.bus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timain.web.bus.mapper.GoodsMapper;
import com.timain.web.bus.mapper.SaleBackMapper;
import com.timain.web.bus.mapper.SaleMapper;
import com.timain.web.bus.pojo.*;
import com.timain.web.bus.service.SaleBackService;
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
public class SaleBackServiceImpl extends ServiceImpl<SaleBackMapper, SaleBack> implements SaleBackService {

    @Autowired
    private SaleMapper saleMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 添加取消销售信息
     *
     * @param id
     * @param number
     * @param remark
     */
    @Override
    public void addSaleBack(Integer id, Integer number, String remark) {
        //根据销售ID查询销售信息
        Sale sale = this.saleMapper.selectById(id);
        //根据商品ID查询商品信息
        Goods goods = this.goodsMapper.selectById(sale.getGoodsId());
        //取消销售增加库存
        goods.setNumber(goods.getNumber() + number);
        SaleBack saleBack = new SaleBack();
        //取消销售数量
        saleBack.setNumber(number);
        //商品ID
        saleBack.setGoodsId(goods.getId());
        //客户ID
        saleBack.setCustomerId(sale.getCustomerId());
        //取消销售时间
        saleBack.setSalesBackTime(new Date());
        //取消销售备注
        saleBack.setRemark(remark);
        //操作员
        User user = (User) LoginUtils.getSession().getAttribute("user");
        saleBack.setOperatePerson(user.getName());
        //支付类型
        saleBack.setPayType(sale.getPayType());
        //取消销售价格
        saleBack.setSaleBackPrice(sale.getSalePrice());
        this.getBaseMapper().insert(saleBack);
        this.goodsMapper.updateById(goods);
        this.saleMapper.deleteById(id);
    }
}
