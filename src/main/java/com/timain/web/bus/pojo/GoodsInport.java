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
 * 商品进货实体类
 * @author yyf
 * @version 1.0
 * @date 2020/2/29 19:06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_inport")
public class GoodsInport implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String payType;//支付类型
    private Date inportTime;//进货时间
    private String operatePerson;//操作员
    private Integer number;//进货数量
    private String remark;//备注
    private Double inportPrice;//进货价格
    private Integer providerId;
    private Integer goodsId;

    @TableField(exist = false)
    private String providerName;
    @TableField(exist = false)
    private String goodsName;
    @TableField(exist = false)
    private String size;
}
