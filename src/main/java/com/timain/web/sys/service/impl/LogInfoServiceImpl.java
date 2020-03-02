package com.timain.web.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timain.web.sys.mapper.LogInfoMapper;
import com.timain.web.sys.pojo.LogInfo;
import com.timain.web.sys.service.LogInfoService;
import org.springframework.stereotype.Service;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/15 15:21
 */
@Service
public class LogInfoServiceImpl extends ServiceImpl<LogInfoMapper, LogInfo> implements LogInfoService {
}
