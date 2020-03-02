package com.timain.web.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timain.web.sys.mapper.NoticeMapper;
import com.timain.web.sys.pojo.Notice;
import com.timain.web.sys.service.NoticeService;
import org.springframework.stereotype.Service;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/16 9:21
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
}
