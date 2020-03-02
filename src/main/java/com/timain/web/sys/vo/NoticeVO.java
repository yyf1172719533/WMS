package com.timain.web.sys.vo;

import com.timain.web.sys.pojo.Notice;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/15 15:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NoticeVO extends Notice {

    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;

    /**批量删除ID**/
    private Integer[] ids;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
