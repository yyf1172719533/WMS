package com.timain.web.sys.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/17 8:49
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WorkFlowVO {

    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;
    
    private String deploymentName;

}
