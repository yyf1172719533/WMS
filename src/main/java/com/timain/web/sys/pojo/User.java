package com.timain.web.sys.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @author yyf
 * @version 1.0
 * @date 2020/1/14 20:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String loginName;
    private String address;
    private Integer sex;
    private String remark;
    private String pwd;
    private Integer deptId;//部门ID
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date hireDate;
    private Integer mgr;
    private Integer available;
    private Integer orderNum;
    private Integer type;//用户类型
    private String imgPath;//头像地址
    private String salt;

    @TableField(exist = false)
    private String leaderName;//领导名称

    @TableField(exist = false)
    private String deptName;//部门名称
}
