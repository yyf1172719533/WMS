package com.timain.web.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.mapper.LeaveBillMapper;
import com.timain.web.sys.mapper.UserMapper;
import com.timain.web.sys.pojo.LeaveBill;
import com.timain.web.sys.pojo.User;
import com.timain.web.sys.service.LeaveBillService;
import com.timain.web.sys.vo.LeaveBillVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/6/30 15:46
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LeaveBillServiceImpl extends ServiceImpl<LeaveBillMapper, LeaveBill> implements LeaveBillService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public DataGridView findLeaveInfo(LeaveBillVO leaveBillVO) {
        Page<LeaveBill> page = new Page<>(leaveBillVO.getPage(), leaveBillVO.getLimit());
        QueryWrapper<LeaveBill> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(leaveBillVO.getTitle()), "title", leaveBillVO.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(leaveBillVO.getContent()), "content", leaveBillVO.getContent());
        queryWrapper.ge(null!=leaveBillVO.getStartTime(),"leaveTime", leaveBillVO.getStartTime());
        queryWrapper.ge(null!=leaveBillVO.getEndTime(),"leaveTime", leaveBillVO.getEndTime());
        queryWrapper.orderByDesc("leaveTime");
        this.baseMapper.selectPage(page, queryWrapper);
        List<LeaveBill> list = page.getRecords();
        if (!list.isEmpty()) {
            for (LeaveBill leaveBill : list) {
                User user = this.userMapper.selectById(leaveBill.getUserId());
                if (null!=user) {
                    leaveBill.setUserName(user.getName());
                }
            }
        }
        return new DataGridView(page.getTotal(), list);
    }
}
