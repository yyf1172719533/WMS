package com.timain.web.sys.controller;

import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.common.ResultObj;
import com.timain.web.sys.service.LeaveBillService;
import com.timain.web.sys.vo.LeaveBillVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author yyf
 * @version 1.0
 * @date 2020/6/30 16:04
 */
@RestController
@RequestMapping("leave")
public class LeaveBillController {
    
    @Autowired
    private LeaveBillService leaveBillService;
    
    @RequestMapping("loadAllLeave")
    public DataGridView loadAllLeave(LeaveBillVO leaveBillVO) {
        return this.leaveBillService.findLeaveInfo(leaveBillVO);
    }
    
    @RequestMapping("addLeave")
    public ResultObj addLeave(LeaveBillVO leaveBillVO) {
        try {
            leaveBillService.addOrUpdateLeave(leaveBillVO);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }
    
    @RequestMapping("updateLeave")
    public ResultObj updateLeave(LeaveBillVO leaveBillVO) {
        try {
            leaveBillService.addOrUpdateLeave(leaveBillVO);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
    
    @RequestMapping("delOneLeave")
    public ResultObj delOneLeave(String id) {
        try {
            leaveBillService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
