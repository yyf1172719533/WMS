package com.timain.web.sys.service;


import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.pojo.LeaveBill;
import com.timain.web.sys.vo.WorkFlowVO;

import java.io.InputStream;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/7/1 14:34
 */
public interface WorkFlowService {

    /**
     * 添加流程部署
     * @param inputStream
     * @param deploymentName
     */
    void addWorkFlow(InputStream inputStream, String deploymentName);

    /**
     * 查询流程部署信息
     * @param workFlowVO
     * @return
     */
    DataGridView queryAllDeployInfo(WorkFlowVO workFlowVO);

    /**
     * 查询流程定义信息
     * @param workFlowVO
     * @return
     */
    DataGridView queryAllProcessDefinition(WorkFlowVO workFlowVO);

    /**
     * 删除流程部署
     * @param deploymentId
     */
    void deleteWorkFlow(String deploymentId);

    /**
     * 查看流程图
     * @param deploymentId
     * @return
     */
    InputStream queryProcessImg(String deploymentId);

    /**
     * 提交请假申请
     * @param id
     */
    void submitLeave(String id);

    /**
     * 查询当前登录人的待办任务
     * @param workFlowVO
     * @return
     */
    DataGridView queryCurrentUserTask(WorkFlowVO workFlowVO);

    /**
     * 根据任务ID查询请假单信息
     * @param taskId
     * @return
     */
    LeaveBill queryLeaveBillByTaskId(String taskId);
}
