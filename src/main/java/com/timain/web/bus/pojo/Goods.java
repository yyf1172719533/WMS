package com.timain.web.bus.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品实体类
 * @author yyf
 * @version 1.0
 * @date 2020/2/28 19:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_goods")
public class Goods implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String goodsName;//商品名称
    private String producePlace;//产地
    private String size;//规格
    private String goodsPackage;//包装
    private String productCode;//生产批号
    private String promitCode;//批准文号
    private String description;//商品描述
    private Double price;//销售价格
    private Integer number;//库存量
    private Integer dangerNum;//库存预警值
    private String goodsImg;//商品图片
    private Integer available;//是否有效 1-有效 0-无效
    private Integer providerId;//供应商ID

    @TableField(exist = false)
    private String providerName;//供应商名称
}
