package com.timain.web.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timain.web.sys.pojo.LeaveBill;
import com.timain.web.sys.vo.LeaveBillVO;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/6/30 15:44
 */
public interface LeaveBillMapper extends BaseMapper<LeaveBill> {
    
    void insertLeave(LeaveBillVO leaveBillVO);
    
}
