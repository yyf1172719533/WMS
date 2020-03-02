package com.timain.web.sys.controller;

import com.timain.web.sys.cache.CachePool;
import com.timain.web.sys.common.DataGridView;
import com.timain.web.sys.common.ResultObj;
import com.timain.web.sys.pojo.CacheBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/3/1 14:51
 */
@RestController
@RequestMapping("/cache")
public class CacheController {

    public static volatile Map<String, Object> CACHE_CONTAINER = CachePool.CACHE_CONTAINER;

    /**
     * 查询所有缓存数据
     * @return
     */
    @RequestMapping("loadAllCache")
    public DataGridView loadAllCache() {
        List<CacheBean> beans = new ArrayList<>();
        Set<Map.Entry<String, Object>> entrySet = CACHE_CONTAINER.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            beans.add(new CacheBean(entry.getKey(), entry.getValue()));
        }
        return new DataGridView(beans);
    }

    /**
     * 清空所有缓存数据
     * @return
     */
    @RequestMapping("removeAll")
    public ResultObj removeAll() {
        try {
            CachePool.removeAll();
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 根据key删除缓存数据
     * @param key
     * @return
     */
    @RequestMapping("removeByKey")
    public ResultObj removeByKey(String key) {
        try {
            CachePool.removeByKey(key);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 同步缓存数据
     * @return
     */
    @RequestMapping("syncData")
    public ResultObj syncData() {
        try {
            CachePool.syncData();
            return ResultObj.SYNC_CACHE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.SYNC_CACHE_ERROR;
        }
    }
}
