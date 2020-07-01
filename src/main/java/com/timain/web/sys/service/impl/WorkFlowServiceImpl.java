package com.timain.web.sys.service.impl;

import com.timain.web.sys.service.WorkFlowService;
import org.activiti.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/7/1 14:35
 */
@Service
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
}
