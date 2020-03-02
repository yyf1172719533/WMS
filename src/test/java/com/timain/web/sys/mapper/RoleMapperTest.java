package com.timain.web.sys.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/2/20 12:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleMapperTest {

    @Autowired
    private RoleMapper roleMapper;

    @Test
    @Transactional
    public void deleteRolePermissionByRID() {
        roleMapper.deleteRolePermissionByRID(1);

    }

    @Test
    @Transactional
    public void deleteRoleUserByRID() {
        roleMapper.deleteRoleUserByRID(7);
    }

    @Test
    public void findById() {
        List<Integer> ids = roleMapper.queryRolePermissionIdsByRid(1);
        ids.forEach(System.out::println);
    }

    @Test
    @Transactional
    public void deleteRoleUserByUID() {
        roleMapper.deleteRoleUserByUID(4);
    }

    @Test
    public void queryUserRoleIdsByUid() {
        List<Integer> ids = roleMapper.queryUserRoleIdsByUid(3);
        ids.forEach(System.out::println);
    }
}