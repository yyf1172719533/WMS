package com.timain.web.bus.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品销售实体类
 * @author yyf
 * @version 1.0
 * @date 2020/3/1 13:20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_sales")
public class Sale implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer customerId;
    private String payType;
    private Date salesTime;
    private String operatePerson;
    private Integer number;//销售数量
    private String remark;
    private Double salePrice;//销售价格
    private Integer goodsId;

    @TableField(exist = false)
    private String customerName;
    @TableField(exist = false)
    private String goodsName;
    @TableField(exist = false)
    private String size;
}
