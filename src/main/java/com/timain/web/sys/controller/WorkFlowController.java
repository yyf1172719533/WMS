package com.timain.web.sys.controller;

import com.timain.web.sys.common.ResultObj;
import com.timain.web.sys.service.WorkFlowService;
import com.timain.web.sys.vo.WorkFlowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/7/1 16:33
 */
@RequestMapping("workFlow")
@RestController
public class WorkFlowController {
    
    @Autowired
    private WorkFlowService workFlowService;
    
    @RequestMapping("addWorkFlow")
    public ResultObj addWorkFlow(MultipartFile mf, WorkFlowVO workFlowVO) {
        try {
            this.workFlowService.addWorkFlow(mf.getInputStream(), workFlowVO.getDeploymentName());
            return ResultObj.ADD_PROCESS_SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return ResultObj.ADD_PROCESS_ERROR;
        }
    }
}
