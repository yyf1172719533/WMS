package com.timain.web.sys.pojo.act;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 流程定义信息
 * @author yyf
 * @version 1.0
 * @date 2020/7/2 10:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActProcessDefinition implements Serializable {

    private static final long serialVersionUID=1L;
    
    private String id;
    private String name;
    private String key;
    private Integer version;
    private String deploymentId;
    private String resourceName;
    private String diagramResourceName;
}
