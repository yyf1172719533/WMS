package com.timain.web.sys.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.timain.web.bus.mapper.CustomerMapper;
import com.timain.web.bus.mapper.GoodsMapper;
import com.timain.web.bus.mapper.ProviderMapper;
import com.timain.web.bus.pojo.Customer;
import com.timain.web.bus.pojo.Goods;
import com.timain.web.bus.pojo.Provider;
import com.timain.web.sys.mapper.DeptMapper;
import com.timain.web.sys.mapper.UserMapper;
import com.timain.web.sys.pojo.Dept;
import com.timain.web.sys.pojo.User;
import com.timain.web.sys.utils.SpringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 缓存池
 * @author yyf
 * @version 1.0
 * @date 2020/3/1 13:31
 */
public class CachePool {

    /**
     * 所有的缓存数据放到这个CACHE_CONTAINER类似于redis
     */
    public static volatile Map<String, Object> CACHE_CONTAINER = new HashMap<>();

    /**
     * 根据KEY删除缓存
     * @param key
     */
    public static void removeByKey(String key) {
        if (CACHE_CONTAINER.containsKey(key)) {
            CACHE_CONTAINER.remove(key);
        }
    }

    /**
     * 删除所有缓存
     */
    public static void removeAll() {
        CACHE_CONTAINER.clear();
    }

    /**
     * 同步缓存
     */
    public static void syncData() {
        //同步部门数据
        DeptMapper deptMapper = SpringUtils.getBean(DeptMapper.class);
        List<Dept> deptList = deptMapper.selectList(null);
        for (Dept dept : deptList) {
            CACHE_CONTAINER.put("dept:" + dept.getId(), dept);
        }
        //同步用户数据
        UserMapper userMapper = SpringUtils.getBean(UserMapper.class);
        List<User> userList = userMapper.selectList(null);
        for (User user : userList) {
            CACHE_CONTAINER.put("user:" + user.getId(), user);
        }
        //同步商品数据
        GoodsMapper goodsMapper = SpringUtils.getBean(GoodsMapper.class);
        List<Goods> goodsList = goodsMapper.selectList(null);
        for (Goods goods : goodsList) {
            CACHE_CONTAINER.put("goods:" + goods.getId(), goods);
        }
        //同步客户数据
        CustomerMapper customerMapper = SpringUtils.getBean(CustomerMapper.class);
        List<Customer> customerList = customerMapper.selectList(null);
        for (Customer customer : customerList) {
            CACHE_CONTAINER.put("customer:" + customer.getId(), customer);
        }
        //同步供应商数据
        ProviderMapper providerMapper = SpringUtils.getBean(ProviderMapper.class);
        List<Provider> providerList = providerMapper.selectList(null);
        for (Provider provider : providerList) {
            CACHE_CONTAINER.put("provider:" + provider.getId(), provider);
        }
    }
}
