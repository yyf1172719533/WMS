package com.timain.web.sys.utils;

import com.timain.web.sys.common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形结构菜单工具类
 * @author yyf
 * @version 1.0
 * @date 2020/1/15 14:00
 */
public class TreeNodeUtils {

    /**
     * 将没有层级的菜单转为有层级的集合
     * @param treeNodes
     * @param topId
     * @return
     */
    public static List<TreeNode> build(List<TreeNode> treeNodes, Integer topId) {
        List<TreeNode> nodeList = new ArrayList<>();
        for (TreeNode t1: treeNodes) {
            if (t1.getPId()==topId) {
                nodeList.add(t1);
            }
            for (TreeNode t2: treeNodes) {
                if (t2.getPId()==t1.getId()) {
                    t1.getChildren().add(t2);
                }
            }
        }
        return nodeList;
    }
}
