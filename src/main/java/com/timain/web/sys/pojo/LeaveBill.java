package com.timain.web.sys.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/6/30 15:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_leavebill")
public class LeaveBill implements Serializable {

    private static final long serialVersionUID=1L;
    
    private String id;
    private String title;
    private String content;
    private Double days;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date leaveTime;
    private String state;   //0-未提交 1-审批中 2-审批完成 3-已放弃
    private Integer userId;
    @TableField(exist = false)
    private String userName;
}
