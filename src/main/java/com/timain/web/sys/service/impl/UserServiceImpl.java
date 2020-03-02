package com.timain.web.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timain.web.sys.mapper.RoleMapper;
import com.timain.web.sys.mapper.UserMapper;
import com.timain.web.sys.pojo.User;
import com.timain.web.sys.service.UserService;
import com.timain.web.sys.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/14 20:45
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public boolean save(User entity) {
        return super.save(entity);
    }

    @Override
    public boolean updateById(User entity) {
        return super.updateById(entity);
    }

    @Override
    public User getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean removeById(Serializable id) {
        //根据用户ID删除用户角色中间表的数据
        roleMapper.deleteRoleUserByUID(id);
        //TODO
        return super.removeById(id);
    }


    /**
     * 分配角色给用户
     *
     * @param uid
     * @param ids
     */
    @Override
    public void saveUserRole(Integer uid, Integer[] ids) {
        //先根据用户ID删除角色信息
        this.roleMapper.deleteRoleUserByUID(uid);
        //再添加角色信息
        if (null!=ids && ids.length>0) {
            for (Integer rid: ids) {
                this.getBaseMapper().insertUserRole(rid, uid);
            }
        }
    }
}
