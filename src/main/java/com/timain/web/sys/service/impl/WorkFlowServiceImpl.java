package com.timain.web.sys.service.impl;

import com.timain.web.sys.common.Constants;
import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.mapper.LeaveBillMapper;
import com.timain.web.sys.pojo.LeaveBill;
import com.timain.web.sys.pojo.User;
import com.timain.web.sys.pojo.act.ActComment;
import com.timain.web.sys.pojo.act.ActProcessDefinition;
import com.timain.web.sys.pojo.act.ActReDeployment;
import com.timain.web.sys.pojo.act.ActRuTask;
import com.timain.web.sys.service.WorkFlowService;
import com.timain.web.sys.utils.LoginUtils;
import com.timain.web.sys.vo.WorkFlowVO;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipInputStream;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/7/1 14:35
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WorkFlowServiceImpl implements WorkFlowService {
    
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ManagementService managementService;
    @Autowired
    private LeaveBillMapper leaveBillMapper;

    /**
     * 添加流程部署
     * @param inputStream
     * @param deploymentName
     */
    @Override
    public void addWorkFlow(InputStream inputStream, String deploymentName) {
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        this.repositoryService.createDeployment().name(deploymentName).addZipInputStream(zipInputStream).deploy();
        try {
            zipInputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询流程部署信息
     * @param workFlowVO
     * @return
     */
    @Override
    public DataGridView queryAllDeployInfo(WorkFlowVO workFlowVO) {
        if (StringUtils.isEmpty(workFlowVO.getDeploymentName())) {
            workFlowVO.setDeploymentName("");
        }
        String name = workFlowVO.getDeploymentName();
        //查询总条数
        long count = this.repositoryService.createDeploymentQuery().deploymentNameLike("%" + name + "%").count();
        //分页查询
        int firstResult = (workFlowVO.getPage() - 1) * workFlowVO.getLimit();
        int maxResults = workFlowVO.getLimit();
        List<Deployment> deployments = this.repositoryService.createDeploymentQuery().deploymentNameLike("%" + name + "%").listPage(firstResult, maxResults);
        List<ActReDeployment> data = new ArrayList<>();
        if (null!=deployments && deployments.size() > 0) {
            for (Deployment deployment : deployments) {
                ActReDeployment entity = new ActReDeployment();
                BeanUtils.copyProperties(deployment, entity);
                data.add(entity);
            }
        }
        return new DataGridView(count, data);
    }

    /**
     * 查询流程定义信息
     * @param workFlowVO
     * @return
     */
    @Override
    public DataGridView queryAllProcessDefinition(WorkFlowVO workFlowVO) {
        if (StringUtils.isEmpty(workFlowVO.getDeploymentName())) {
            workFlowVO.setDeploymentName("");
        }
        String name = workFlowVO.getDeploymentName();
        //模糊查询列表信息
        List<Deployment> deploymentList = this.repositoryService.createDeploymentQuery().deploymentNameLike("%" + name + "%").list();
        Set<String> deploymentIdList = new HashSet<>();
        if (null!=deploymentList && deploymentList.size() > 0) {
            for (Deployment deployment : deploymentList) {
                deploymentIdList.add(deployment.getId());
            }
        }
        long count = 0;
        List<ActProcessDefinition> data = new ArrayList<>();
        if (null!=deploymentIdList && deploymentIdList.size() > 0) {
            count = this.repositoryService.createProcessDefinitionQuery().deploymentIds(deploymentIdList).count();
            //分页查询流程定义信息
            int firstResult = (workFlowVO.getPage() - 1) * workFlowVO.getLimit();
            int maxResults = workFlowVO.getLimit();
            List<ProcessDefinition> processDefinitionList = this.repositoryService.createProcessDefinitionQuery().deploymentIds(deploymentIdList).listPage(firstResult, maxResults);
            if (null!=processDefinitionList && processDefinitionList.size() > 0) {
                for (ProcessDefinition processDefinition : processDefinitionList) {
                    ActProcessDefinition actProcessDefinition = new ActProcessDefinition();
                    BeanUtils.copyProperties(processDefinition, actProcessDefinition);
                    data.add(actProcessDefinition);
                }
            }
        }
        return new DataGridView(count, data);
    }

    /**
     * 删除流程部署
     * @param deploymentId
     */
    @Override
    public void deleteWorkFlow(String deploymentId) {
        this.repositoryService.deleteDeployment(deploymentId, true);
    }

    /**
     * 查看流程图
     * @param deploymentId
     * @return
     */
    @Override
    public InputStream queryProcessImg(String deploymentId) {
        //根据流程部署ID查询流程定义对象
        ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        //从流程定义对象中获取图片名称
        String diagramResourceName = processDefinition.getDiagramResourceName();
        //根据流程部署ID和图片名称查询图片流
        InputStream inputStream = this.repositoryService.getResourceAsStream(deploymentId, diagramResourceName);
        return inputStream;
    }

    /**
     * 提交请假申请
     *
     * @param id
     */
    @Override
    public void submitLeave(String id) {
        //获取流程定义KEY
        String processDefinitionKey = LeaveBill.class.getSimpleName();
        String businessKey = processDefinitionKey + ":" + id;
        //设置流程变量
        Map<String, Object> variables = new HashMap<>();
        User user = (User) LoginUtils.getSession().getAttribute("user");
        variables.put("username", user.getName());
        this.runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
        //更新请假单状态
        LeaveBill leaveBill = this.leaveBillMapper.selectById(id);
        leaveBill.setState(Constants.STATE_LEAVEBILL_ONE);
        this.leaveBillMapper.updateById(leaveBill);
    }

    /**
     * 查询当前登录人的待办任务
     *
     * @param workFlowVO
     * @return
     */
    @Override
    public DataGridView queryCurrentUserTask(WorkFlowVO workFlowVO) {
        //获取办理人信息
        User user = (User) LoginUtils.getSession().getAttribute("user");
        String assignee = user.getName();
        //获取总数
        long count = this.taskService.createTaskQuery().taskAssignee(assignee).count();
        //分页查询
        int firstResult = (workFlowVO.getPage() - 1) * workFlowVO.getLimit();
        int maxResults = workFlowVO.getLimit();
        List<Task> tasks = this.taskService.createTaskQuery().taskAssignee(assignee).listPage(firstResult, maxResults);
        List<ActRuTask> data = new ArrayList<>();
        if (null!=tasks && tasks.size() > 0) {
            for (Task task : tasks) {
                ActRuTask actRuTask = new ActRuTask();
                BeanUtils.copyProperties(task, actRuTask);
                data.add(actRuTask);
            }
        }
        return new DataGridView(count, data);
    }

    /**
     * 根据任务ID查询请假单信息
     *
     * @param taskId
     * @return
     */
    @Override
    public LeaveBill queryLeaveBillByTaskId(String taskId) {
        //根据任务ID查询任务实例
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        //获取任务实例中的流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        //根据流程实例ID查询流程实例
        ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //获取流程实例中的business_key
        String businessKey = processInstance.getBusinessKey();
        //获取请假单ID
        String leaveBillId = businessKey.split(":")[1];
        LeaveBill leaveBill = this.leaveBillMapper.selectById(leaveBillId);
        return leaveBill;
    }

    /**
     * 根据任务ID查询连线信息
     *
     * @param taskId
     * @return
     */
    @Override
    public List<String> queryOutComesByTaskId(String taskId) {
        List<String> outNames = new ArrayList<>();
        ActivityImpl activityImpl = queryActivity(taskId);
        //从ActivityImpl中获取连线信息
        List<PvmTransition> outgoingTransitions = activityImpl.getOutgoingTransitions();
        if (null!=outgoingTransitions && outgoingTransitions.size() > 0) {
            for (PvmTransition pvmTransition : outgoingTransitions) {
                String name = pvmTransition.getProperty("name").toString();
                outNames.add(name);
            }
        }
        return outNames;
    }

    /**
     * 根据任务ID获取节点数据
     * @param taskId
     * @return
     */
    private ActivityImpl queryActivity(String taskId) {
        //根据任务ID查询任务实例
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        //获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        //获取流程定义ID
        String processDefinitionId = task.getProcessDefinitionId();
        //根据流程实例ID获取流程实例
        ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //根据流程定义ID查询流程定义的xml信息
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) this.repositoryService.getProcessDefinition(processDefinitionId);
        //从流程实例中获取当前活动节点
        String activityId = processInstance.getActivityId();
        //根据活动ID获取xml和当前活动ID相关节点数据
        ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
        return activityImpl;
    }

    /**
     * 根据任务ID查询批注信息
     *
     * @param taskId
     * @return
     */
    @Override
    public DataGridView queryCommentByTaskId(String taskId) {
        //根据任务ID查询任务实例
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        //获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        //获取批注信息
        List<Comment> comments = taskService.getProcessInstanceComments(processInstanceId);
        List<ActComment> data = new ArrayList<>();
        if (null!=comments && comments.size() > 0) {
            for (Comment comment : comments) {
                ActComment actComment = new ActComment();
                BeanUtils.copyProperties(comment, actComment);
                data.add(actComment);
            }
        }
        return new DataGridView(Long.valueOf(data.size()), data);
    }

    /**
     * 完成待办任务
     *
     * @param workFlowVO
     */
    @Override
    public void completeTask(WorkFlowVO workFlowVO) {
        String leaveBillId = workFlowVO.getId();//请假单ID
        String outcome = workFlowVO.getOutcome();//连接名称
        String taskId = workFlowVO.getTaskId();//任务ID
        String comment = workFlowVO.getComment();//批注信息
        if (StringUtils.isEmpty(leaveBillId) || StringUtils.isEmpty(outcome) || StringUtils.isEmpty(taskId)) {
            throw new RuntimeException("参数错误!");
        }
        //根据任务ID获取任务实例
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        //获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        //设置批注人
        User user = (User) LoginUtils.getSession().getAttribute("user");
        String userName = user.getName();
        Authentication.setAuthenticatedUserId(userName);
        //添加批注信息
        this.taskService.addComment(taskId, processInstanceId, outcome, "【" + outcome + "】" + comment);
        //完成任务
        Map<String, Object> variables = new HashMap<>();
        variables.put("outcome", outcome);
        this.taskService.complete(taskId, variables);
        //判断任务是否结束
        ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (null==processInstance) {
            LeaveBill leaveBill = new LeaveBill();
            leaveBill.setId(leaveBillId);
            if ("放弃".equals(outcome)) {
                leaveBill.setState(Constants.STATE_LEAVEBILL_THREE);
            } else {
                leaveBill.setState(Constants.STATE_LEAVEBILL_TWO);
            }
            this.leaveBillMapper.updateById(leaveBill);
        }
    }

    /**
     * 根据任务ID查询流程部署ID
     *
     * @param taskId
     * @return
     */
    @Override
    public String queryDeploymentIdByTaskId(String taskId) {
        //根据任务ID查询任务实例
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        //获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        //根据流程实例ID获取流程实例对象
        ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //获取流程定义ID
        String processDefinitionId = processInstance.getProcessDefinitionId();
        //根据流程定义ID查询流程定义对象
        ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        //获取流程部署ID
        String deploymentId = processDefinition.getDeploymentId();
        return deploymentId;
    }

    /**
     * 根据任务ID查询节点坐标
     *
     * @param taskId
     * @return
     */
    @Override
    public Map<String, Object> queryCoordinateByTaskId(String taskId) {
        Map<String, Object> map = new HashMap<>();
        ActivityImpl activityImpl = queryActivity(taskId);
        //从activityImpl中取出坐标信息
        map.put("x", activityImpl.getX());
        map.put("y", activityImpl.getY());
        map.put("width", activityImpl.getWidth());
        map.put("height", activityImpl.getHeight());
        return map;
    }

    /**
     * 根据请假单ID查询批注信息
     *
     * @param id
     * @return
     */
    @Override
    public DataGridView queryCommentByLeaveBillId(String id) {
        String businessKey = LeaveBill.class.getSimpleName() + ":" + id;
        //根据businessKey查询历史流程实例
        HistoricProcessInstance historicProcessInstance = this.historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
        //根据流程实例ID查询批注信息
        List<Comment> comments = this.taskService.getProcessInstanceComments(historicProcessInstance.getId());
        List<ActComment> list = new ArrayList<>();
        if (null!=comments && comments.size() > 0) {
            for (Comment comment : comments) {
                ActComment actComment = new ActComment();
                BeanUtils.copyProperties(comment, actComment);
                list.add(actComment);
            }
        }
        return new DataGridView(Long.valueOf(list.size()), list);
    }

    /**
     * 查询当前登录人的审批记录
     *
     * @param workFlowVO
     * @return
     */
    @Override
    public DataGridView queryCurrentHistory(WorkFlowVO workFlowVO) {
        User user = (User) LoginUtils.getSession().getAttribute("user");
        String assignee = user.getName();
        int firstResult = (workFlowVO.getPage() - 1) * workFlowVO.getLimit();
        int maxResults = workFlowVO.getLimit();
        long count = this.historyService.createHistoricTaskInstanceQuery().taskAssignee(assignee).count();
        List<HistoricTaskInstance> historicTaskInstances = this.historyService.createHistoricTaskInstanceQuery().taskAssignee(assignee).listPage(firstResult, maxResults);
        return new DataGridView(count, historicTaskInstances);
    }

    /**
     * 查询所有历史流程
     *
     * @param workFlowVO
     * @return
     */
    @Override
    public DataGridView queryWorkFlow(WorkFlowVO workFlowVO) {
        int firstResult = (workFlowVO.getPage() - 1) * workFlowVO.getLimit();
        int maxResults = workFlowVO.getLimit();
        long count = this.historyService.createHistoricProcessInstanceQuery().count();
        List<HistoricProcessInstance> historicProcessInstances = this.historyService.createHistoricProcessInstanceQuery().listPage(firstResult, maxResults);
        return new DataGridView(count, historicProcessInstances);
    }
}
