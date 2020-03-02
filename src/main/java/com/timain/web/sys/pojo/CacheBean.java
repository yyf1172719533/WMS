package com.timain.web.sys.pojo;

import com.alibaba.fastjson.JSON;

/**
 * 缓存对象
 * @author yyf
 * @version 1.0
 * @date 2020/3/1 14:41
 */
public class CacheBean {

    private String key;
    private Object value;

    public CacheBean() {
    }

    public CacheBean(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return JSON.toJSON(value).toString();
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
