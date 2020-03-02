package com.timain.web.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.common.ResultObj;
import com.timain.web.sys.pojo.Notice;
import com.timain.web.sys.pojo.User;
import com.timain.web.sys.service.NoticeService;
import com.timain.web.sys.utils.LoginUtils;
import com.timain.web.sys.vo.NoticeVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * 公告管理
 * @author yyf
 * @version 1.0
 * @date 2020/1/16 9:28
 */
@RestController
@RequestMapping("notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 分页条件查询公告信息
     * @param noticeVO
     * @return
     */
    @RequestMapping("loadAllNotice")
    public DataGridView loadAllNotice(NoticeVO noticeVO) {
        IPage<Notice> page = new Page<>(noticeVO.getPage(), noticeVO.getLimit());
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(noticeVO.getTitle()), "title", noticeVO.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(noticeVO.getOperName()), "operName", noticeVO.getOperName());
        queryWrapper.ge(noticeVO.getStartTime()!=null, "createTime", noticeVO.getStartTime());
        queryWrapper.le(noticeVO.getEndTime()!=null, "createTime", noticeVO.getEndTime());
        queryWrapper.orderByDesc("createTime");
        this.noticeService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加公告
     * @param noticeVO
     * @return
     */
    @RequestMapping("addNotice")
    public ResultObj addNotice(NoticeVO noticeVO) {
        try {
            noticeVO.setCreateTime(new Date());
            User user = (User) LoginUtils.getSession().getAttribute("user");
            noticeVO.setOperName(user.getName());
            this.noticeService.save(noticeVO);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改公告
     * @param noticeVO
     * @return
     */
    @RequestMapping("updateNotice")
    public ResultObj updateNotice(NoticeVO noticeVO) {
        try {
            this.noticeService.updateById(noticeVO);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 根据id删除单个公告
     * @param id
     * @return
     */
    @RequestMapping("delOneNotice")
    public ResultObj delOneNotice(Integer id) {
        try {
            this.noticeService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除公告
     * @param noticeVO
     * @return
     */
    @RequestMapping("delMoreNotice")
    public ResultObj delMoreNotice(NoticeVO noticeVO) {
        try {
            Collection<Integer> idsList = new ArrayList<>();
            for (Integer id: noticeVO.getIds()) {
                idsList.add(id);
            }
            this.noticeService.removeByIds(idsList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
