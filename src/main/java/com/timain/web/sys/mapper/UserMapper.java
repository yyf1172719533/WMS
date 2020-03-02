package com.timain.web.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timain.web.sys.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/14 20:40
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户ID保存角色ID
     * @param rid
     * @param uid
     */
    void insertUserRole(@Param("rid") Integer rid, @Param("uid") Integer uid);

}
