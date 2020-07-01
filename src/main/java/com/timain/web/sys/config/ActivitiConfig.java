package com.timain.web.sys.config;

import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/7/1 15:02
 */
@Configuration
public class ActivitiConfig {
    
    @Autowired
    private DataSource dataSource;

    /**
     * 初始化配置
     * @return
     */
    @Bean
    public StandaloneProcessEngineConfiguration processEngineConfiguration() {
        StandaloneProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        configuration.setAsyncExecutorActivate(false);
        return configuration;
    }
    
    @Bean
    public ProcessEngine processEngine() {
        return processEngineConfiguration().buildProcessEngine();
    }
    
    @Bean
    public RepositoryService repositoryService() {
        return processEngine().getRepositoryService();
    }
    
    @Bean
    public RuntimeService runtimeService() {
        return processEngine().getRuntimeService();
    }
    
    @Bean
    public TaskService taskService() {
        return processEngine().getTaskService();
    }
    
    @Bean
    public HistoryService historyService() {
        return processEngine().getHistoryService();
    }
    
    
    @Bean
    public ManagementService managementService() {
        return processEngine().getManagementService();
    }
    
}
