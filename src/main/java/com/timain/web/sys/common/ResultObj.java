package com.timain.web.sys.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 输出结果对象
 * @author yyf
 * @version 1.0
 * @date 2020/1/15 8:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObj {

    public static final ResultObj LOGIN_SUCCESS = new ResultObj(Constants.OK, "登录成功");
    public static final ResultObj LOGIN_ERROR_NAME_OR_PWD = new ResultObj(Constants.ERROR, "用户名或密码错误");
    public static final ResultObj LOGIN_ERROR_CODE = new ResultObj(Constants.ERROR, "验证码错误");

    public static final ResultObj UPDATE_SUCCESS = new ResultObj(Constants.OK, "更新成功");
    public static final ResultObj UPDATE_ERROR = new ResultObj(Constants.ERROR, "更新失败");

    public static final ResultObj DELETE_SUCCESS = new ResultObj(Constants.OK, "删除成功");
    public static final ResultObj DELETE_ERROR = new ResultObj(Constants.ERROR, "删除失败");

    public static final ResultObj ADD_SUCCESS = new ResultObj(Constants.OK, "添加成功");
    public static final ResultObj ADD_ERROR = new ResultObj(Constants.ERROR, "添加失败");

    public static final ResultObj DISPATCH_SUCCESS = new ResultObj(Constants.OK, "分配成功");
    public static final ResultObj DISPATCH_ERROR = new ResultObj(Constants.ERROR, "分配失败");

    public static final ResultObj RESERT_PWD_SUCCESS = new ResultObj(Constants.OK, "重置密码成功");
    public static final ResultObj RESERT_PWD_ERROR = new ResultObj(Constants.OK, "重置密码失败");

    public static final ResultObj BACK_GOODS_SUCCESS = new ResultObj(Constants.OK, "退货成功");
    public static final ResultObj BACK_GOODS_ERROR = new ResultObj(Constants.OK, "退货失败");

    public static final ResultObj SYNC_CACHE_SUCCESS = new ResultObj(Constants.OK, "同步缓存成功");
    public static final ResultObj SYNC_CACHE_ERROR = new ResultObj(Constants.OK, "同步缓存失败");

    private Integer code;
    private String msg;
}
