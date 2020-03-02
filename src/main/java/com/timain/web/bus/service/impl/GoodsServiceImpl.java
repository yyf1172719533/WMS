package com.timain.web.bus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timain.web.bus.mapper.GoodsMapper;
import com.timain.web.bus.pojo.Goods;
import com.timain.web.bus.service.GoodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/2/28 19:29
 */
@Service
@Transactional
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Override
    public boolean save(Goods entity) {
        return super.save(entity);
    }

    @Override
    public Goods getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean updateById(Goods entity) {
        return super.updateById(entity);
    }
}
