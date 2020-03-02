package com.timain.web.bus.vo;

import com.timain.web.bus.pojo.Customer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/2/28 15:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerVO extends Customer {

    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;

    private Integer[] ids;
}
