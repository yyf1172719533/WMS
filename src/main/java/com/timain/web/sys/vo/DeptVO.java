package com.timain.web.sys.vo;

import com.timain.web.sys.pojo.Dept;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/17 8:49
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeptVO extends Dept {

    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;

}
