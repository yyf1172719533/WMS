package com.timain.web.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timain.web.sys.mapper.DeptMapper;
import com.timain.web.sys.mapper.PermissionMapper;
import com.timain.web.sys.pojo.Permission;
import com.timain.web.sys.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/15 10:58
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
