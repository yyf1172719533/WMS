package com.timain.web.bus.vo;

import com.timain.web.bus.pojo.Goods;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/2/28 15:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GoodsVO extends Goods {

    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;

}
