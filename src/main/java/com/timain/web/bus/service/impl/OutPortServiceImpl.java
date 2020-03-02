package com.timain.web.bus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timain.web.bus.mapper.GoodsInportMapper;
import com.timain.web.bus.mapper.GoodsMapper;
import com.timain.web.bus.mapper.OutPortMapper;
import com.timain.web.bus.pojo.Goods;
import com.timain.web.bus.pojo.GoodsInport;
import com.timain.web.bus.pojo.OutPort;
import com.timain.web.bus.service.OutPortService;
import com.timain.web.sys.pojo.User;
import com.timain.web.sys.utils.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/2/29 23:00
 */
@Service
public class OutPortServiceImpl extends ServiceImpl<OutPortMapper, OutPort> implements OutPortService {

    @Autowired
    private GoodsInportMapper goodsInportMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 商品退货
     *
     * @param id     进货ID
     * @param number 退货数量
     * @param remark 退货备注
     */
    @Override
    public void addOutPort(Integer id, Integer number, String remark) {
        //根据进货ID查询进货信息
        GoodsInport goodsInport = this.goodsInportMapper.selectById(id);
        //根据商品ID查询商品信息
        Goods goods = this.goodsMapper.selectById(goodsInport.getGoodsId());
        //退货减少库存
        goods.setNumber(goods.getNumber() - number);
        OutPort outPort = new OutPort();
        //退货数量
        outPort.setNumber(number);
        //商品ID
        outPort.setGoodsId(goods.getId());
        //供应商ID
        outPort.setProviderId(goodsInport.getProviderId());
        //退货时间
        outPort.setOutPutTime(new Date());
        //退货备注
        outPort.setRemark(remark);
        //操作员
        User user = (User) LoginUtils.getSession().getAttribute("user");
        outPort.setOperatePerson(user.getName());
        //支付类型
        outPort.setPayType(goodsInport.getPayType());
        //退货价格
        outPort.setOutPortPrice(goodsInport.getInportPrice());
        this.getBaseMapper().insert(outPort);
        this.goodsMapper.updateById(goods);
        this.goodsInportMapper.deleteById(id);
    }
}
