package com.timain.web.sys.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单导航栏树形结构
 * @author yyf
 * @version 1.0
 * @date 2020/1/15 11:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {

    private Integer id;
    @JsonProperty("parentId")
    private Integer pId;
    private String title;
    private String icon;//图标
    private String href;//接口路径
    private Boolean spread;//是否打开窗口

    private List<TreeNode> children = new ArrayList<>();

    private String checkArr="0";//0-未选中 1-选中

    /**
     * TreeNode的数据格式
     * @param id
     * @param pId
     * @param title
     * @param icon
     * @param href
     * @param spread
     */
    public TreeNode(Integer id, Integer pId, String title, String icon, String href, Boolean spread) {
        this.id = id;
        this.pId = pId;
        this.title = title;
        this.icon = icon;
        this.href = href;
        this.spread = spread;
    }

    /**
     * dTree的数据格式
     * @param id
     * @param pId
     * @param title
     * @param spread
     */
    public TreeNode(Integer id, Integer pId, String title, Boolean spread) {
        this.id = id;
        this.pId = pId;
        this.title = title;
        this.spread = spread;
    }

    /**
     * dTree的复选树
     * @param id
     * @param pId
     * @param title
     * @param spread
     * @param checkArr
     */
    public TreeNode(Integer id, Integer pId, String title, Boolean spread, String checkArr) {
        this.id = id;
        this.pId = pId;
        this.title = title;
        this.spread = spread;
        this.checkArr = checkArr;
    }
}
