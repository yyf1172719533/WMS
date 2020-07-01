package com.timain.web.sys.service;


import java.io.InputStream;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/7/1 14:34
 */
public interface WorkFlowService {
    
    void addWorkFlow(InputStream inputStream, String deploymentName);
}
