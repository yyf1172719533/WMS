package com.timain.web.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.pojo.LeaveBill;
import com.timain.web.sys.vo.LeaveBillVO;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/6/30 15:45
 */
public interface LeaveBillService extends IService<LeaveBill> {
    
    DataGridView findLeaveInfo(LeaveBillVO leaveBillVO);
}
