package com.timain.web.bus.vo;

import com.timain.web.bus.pojo.Sale;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/2/28 15:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SaleVO extends Sale {

    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}
