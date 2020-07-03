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
 * @date 2020/7/3 10:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActComment implements Serializable {

    private static final long serialVersionUID=1L;
    
    private String userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;
    private String message;
    private String fullMessage;
}
