package com.timain.web.sys.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 左边导航栏对象
 * @author yyf
 * @version 1.0
 * @date 2020/1/15 10:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer pId;
    private String type;//权限类型
    private String title;
    private String perCode;//权限编码
    private String icon;
    private String href;
    private String target;
    private Integer open;
    private Integer orderNum;
    private Integer available;//状态  0-不可用  1-可用
}
