package com.timain.web.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.timain.web.sys.pojo.User;
import com.timain.web.sys.vo.UserVO;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/14 20:44
 */
public interface UserService extends IService<User> {

    /**
     * 分配角色给用户
     * @param uid
     * @param ids
     */
    void saveUserRole(Integer uid, Integer[] ids);

}
