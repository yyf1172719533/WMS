package com.timain.web.bus.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 客户实体类
 * @author yyf
 * @version 1.0
 * @date 2020/2/27 19:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_customer")
public class Customer implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String customerName;//客户名称
    private String zip;//邮编
    private String address;//地址
    private String telephone;//公司电话
    private String connectionPerson;//联系人
    private String phone;//联系人电话
    private String bank;//开户银行
    private String account;//银行账号
    private String email;//邮箱
    private String fax;//传真
    private Integer available;//是否有效 1-有效 0-无效

}
