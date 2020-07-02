package com.timain.web.sys.pojo.act;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/7/2 18:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActRuTask implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private String assignee;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
