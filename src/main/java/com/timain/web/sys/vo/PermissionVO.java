package com.timain.web.sys.vo;

import com.timain.web.sys.pojo.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/15 11:09
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class PermissionVO extends Permission {

    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;
}
