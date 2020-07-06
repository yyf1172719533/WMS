package com.timain.web.sys.controller;

import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.common.ResultObj;
import com.timain.web.sys.service.WorkFlowService;
import com.timain.web.sys.vo.WorkFlowVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

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

    /**
     * 部署流程
     * @param mf
     * @param workFlowVO
     * @return
     */
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

    /**
     * 查询流程部署信息
     * @param workFlowVO
     * @return
     */
    @RequestMapping("loadAllDeployment")
    public DataGridView loadAllDeployment(WorkFlowVO workFlowVO) {
        return this.workFlowService.queryAllDeployInfo(workFlowVO);
    }

    /**
     * 查询流程定义信息
     * @param workFlowVO
     * @return
     */
    @RequestMapping("loadAllProcessDefinition")
    public DataGridView loadAllProcessDefinition(WorkFlowVO workFlowVO) {
        return this.workFlowService.queryAllProcessDefinition(workFlowVO);
    }

    /**
     * 批量删除
     * @param workFlowVO
     * @return
     */
    @RequestMapping("batchDelete")
    public ResultObj batchDelete(WorkFlowVO workFlowVO) {
        try {
            String[] ids = workFlowVO.getIds();
            if (null!=ids && ids.length > 0) {
                for (String deploymentId : ids) {
                    this.workFlowService.deleteWorkFlow(deploymentId);
                }
            }
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 根据流程部署ID删除单个流程部署信息
     * @param id
     * @return
     */
    @RequestMapping("deleteOne")
    public ResultObj deleteOne(String id) {
        try {
            this.workFlowService.deleteWorkFlow(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 查看流程图
     * @param deploymentId
     * @param response
     */
    @RequestMapping("viewProcessImg")
    public void viewProcessImg(String deploymentId, HttpServletResponse response) {
        InputStream inputStream = this.workFlowService.queryProcessImg(deploymentId);
        try {
            BufferedImage image = ImageIO.read(inputStream);
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "JPEG", outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交请假申请
     * @param id
     * @return
     */
    @RequestMapping("startProcess")
    public ResultObj startProcess(String id) {
        if (StringUtils.isNotBlank(id)) {
            try {
                this.workFlowService.submitLeave(id);
                return ResultObj.SUBMIT_SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                return ResultObj.SUBMIT_ERROR;
            }
        }
        return ResultObj.SUBMIT_ERROR;
    }

    /**
     * 查询当前登录人的待办任务
     * @param workFlowVO
     * @return
     */
    @RequestMapping("loadCurrentUserTask")
    public DataGridView loadCurrentUserTask(WorkFlowVO workFlowVO) {
        return this.workFlowService.queryCurrentUserTask(workFlowVO);
    }

    /**
     * 根据任务ID查询批注信息
     * @param taskId
     * @return
     */
    @RequestMapping("loadAllCommentByTaskId")
    public DataGridView loadAllCommentByTaskId(String taskId) {
        if (StringUtils.isNotBlank(taskId)) {
            return this.workFlowService.queryCommentByTaskId(taskId);
        }
        throw new RuntimeException("任务ID为空");
    }

    /**
     * 完成待办任务
     * @param workFlowVO
     * @return
     */
    @RequestMapping("doTask")
    public ResultObj doTask(WorkFlowVO workFlowVO) {
        try {
            this.workFlowService.completeTask(workFlowVO);
            return ResultObj.SUBMIT_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.SUBMIT_ERROR;
        }
    }

    /**
     * 根据请假单ID查询批注信息
     * @return
     */
    @RequestMapping("loadAllCommentByLeaveBillId")
    public DataGridView loadAllCommentByLeaveBillId(String id) {
        if (StringUtils.isNotBlank(id)) {
            return this.workFlowService.queryCommentByLeaveBillId(id);
        }
        throw new RuntimeException("请假单ID为空!");
    }

    /**
     * 查询当前登录人的审批记录
     * @param workFlowVO
     * @return
     */
    @RequestMapping("loadAllHistory")
    public DataGridView loadAllHistory(WorkFlowVO workFlowVO) {
        return this.workFlowService.queryCurrentHistory(workFlowVO);
    }

    /**
     * 查询所有历史流程
     * @param workFlowVO
     * @return
     */
    @RequestMapping("loadAllWorkFlow")
    public DataGridView loadAllWorkFlow(WorkFlowVO workFlowVO) {
        return this.workFlowService.queryWorkFlow(workFlowVO);
    }
}
