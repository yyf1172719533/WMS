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
 * 商品退货实体类
 * @author yyf
 * @version 1.0
 * @date 2020/2/29 22:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_outport")
public class OutPort implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer providerId;
    private String payType;
    private Date outPutTime;//退货时间
    private String operatePerson;//操作员
    private Double outPortPrice;//退货价格
    private Integer number;//退货数量
    private String remark;
    private Integer goodsId;

    @TableField(exist = false)
    private String size;
    @TableField(exist = false)
    private String providerName;
    @TableField(exist = false)
    private String goodsName;
}
