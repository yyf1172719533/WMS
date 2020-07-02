package com.timain.web.sys.service.impl;

import com.timain.web.sys.common.Constants;
import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.mapper.LeaveBillMapper;
import com.timain.web.sys.pojo.LeaveBill;
import com.timain.web.sys.pojo.User;
import com.timain.web.sys.pojo.act.ActProcessDefinition;
import com.timain.web.sys.pojo.act.ActReDeployment;
import com.timain.web.sys.pojo.act.ActRuTask;
import com.timain.web.sys.service.WorkFlowService;
import com.timain.web.sys.utils.LoginUtils;
import com.timain.web.sys.vo.WorkFlowVO;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
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
}
