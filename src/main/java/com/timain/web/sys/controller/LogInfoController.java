package com.timain.web.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.common.ResultObj;
import com.timain.web.sys.pojo.LogInfo;
import com.timain.web.sys.service.LogInfoService;
import com.timain.web.sys.vo.LogInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/15 15:22
 */
@RestController
@RequestMapping("/logInfo")
public class LogInfoController {

    @Autowired
    private LogInfoService logInfoService;

    /**
     * 分页查询日志信息
     * @param logInfoVO
     * @return
     */
    @RequestMapping("loadAllLogInfo")
    public DataGridView loadAllLogInfo(LogInfoVO logInfoVO) {
        //分页查询
        IPage<LogInfo> page = new Page<>(logInfoVO.getPage(), logInfoVO.getLimit());
        QueryWrapper<LogInfo> queryWrapper = new QueryWrapper<>();
        //模糊查询
        queryWrapper.like(StringUtils.isNotBlank(logInfoVO.getLoginName()), "loginname", logInfoVO.getLoginName());
        queryWrapper.like(StringUtils.isNotBlank(logInfoVO.getLoginIp()), "loginip", logInfoVO.getLoginIp());
        queryWrapper.ge(logInfoVO.getStartTime()!=null, "logintime", logInfoVO.getStartTime());//大于
        queryWrapper.le(logInfoVO.getEndTime()!=null, "logintime", logInfoVO.getEndTime());//小于
        queryWrapper.orderByDesc("logintime");
        this.logInfoService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 根据ID删除日志信息
     * @param id
     * @return
     */
    @RequestMapping("delOneLogInfo")
    public ResultObj delOneLogInfo(Integer id) {
        try {
            this.logInfoService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除日志信息
     * @param logInfoVO
     * @return
     */
    @RequestMapping("delMoreLogInfo")
    public ResultObj delMoreLogInfo(LogInfoVO logInfoVO) {
        try {
            Collection<Integer> idList = new ArrayList();
            for (Integer id: logInfoVO.getIds()) {
                idList.add(id);
            }
            this.logInfoService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
