package com.timain.web.sys.service;


import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.pojo.LeaveBill;
import com.timain.web.sys.vo.WorkFlowVO;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

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

    /**
     * 根据任务ID查询连线信息
     * @param taskId
     * @return
     */
    List<String> queryOutComesByTaskId(String taskId);

    /**
     * 根据任务ID查询批注信息
     * @param taskId
     * @return
     */
    DataGridView queryCommentByTaskId(String taskId);

    /**
     * 完成待办任务
     * @param workFlowVO
     */
    void completeTask(WorkFlowVO workFlowVO);

    /**
     * 根据任务ID查询流程部署ID
     * @param taskId
     * @return
     */
    String queryDeploymentIdByTaskId(String taskId);

    /**
     * 根据任务ID查询节点坐标
     * @param taskId
     * @return
     */
    Map<String, Object> queryCoordinateByTaskId(String taskId);

    /**
     * 根据请假单ID查询批注信息
     * @param id
     * @return
     */
    DataGridView queryCommentByLeaveBillId(String id);

    /**
     * 查询当前登录人的审批记录
     * @param workFlowVO
     * @return
     */
    DataGridView queryCurrentHistory(WorkFlowVO workFlowVO);

    /**
     * 查询所有历史流程
     * @param workFlowVO
     * @return
     */
    DataGridView queryWorkFlow(WorkFlowVO workFlowVO);
}
