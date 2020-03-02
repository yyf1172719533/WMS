package com.timain.web.bus.cache;

import com.timain.web.bus.pojo.Customer;
import com.timain.web.sys.cache.CachePool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户缓存处理
 * @author yyf
 * @version 1.0
 * @date 2020/2/28 17:40
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class CustomerAspectCache {

    //声明日志信息
    private Log log = LogFactory.getLog(CustomerAspectCache.class);

    //声明一个缓存容器
    private Map<String, Object> CACHE_CONTAINER = CachePool.CACHE_CONTAINER;

    //声明切面表达式
    private static final String POINTCUT_CUSTOMER_ADD = "execution(* com.timain.web.bus.service.impl.CustomerServiceImpl.save(..))";
    private static final String POINTCUT_CUSTOMER_UPDATE = "execution(* com.timain.web.bus.service.impl.CustomerServiceImpl.updateById(..))";
    private static final String POINTCUT_CUSTOMER_GET = "execution(* com.timain.web.bus.service.impl.CustomerServiceImpl.getById(..))";
    private static final String POINTCUT_CUSTOMER_DELETE = "execution(* com.timain.web.bus.service.impl.CustomerServiceImpl.removeById(..))";
    private static final String POINTCUT_CUSTOMER_BATCHDELETE = "execution(* com.timain.web.bus.service.impl.CustomerServiceImpl.removeByIds(..))";


    //前缀
    private static final String CACHE_CUSTOMER_PROFIX = "customer:";

    /**
     * 添加切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_CUSTOMER_ADD)
    public Object cacheCustomerAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Customer object = (Customer) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res) {
            CACHE_CONTAINER.put(CACHE_CUSTOMER_PROFIX + object.getId(), object);
        }
        return res;
    }

    /**
     * 查询切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_CUSTOMER_GET)
    public Object cacheCustomerGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer object = (Integer) joinPoint.getArgs()[0];
        //从缓存里取
        Object res1 = CACHE_CONTAINER.get(CACHE_CUSTOMER_PROFIX + object);
        if (null!=res1) {
            log.info("已从缓存中找到客户对象" + CACHE_CUSTOMER_PROFIX + object);
            return res1;
        }
        Customer res2 = (Customer) joinPoint.proceed();
        CACHE_CONTAINER.put(CACHE_CUSTOMER_PROFIX+res2.getId(), res2);
        log.info("未从缓存里面找到客户对象，去数据库查询并放到缓存"+CACHE_CUSTOMER_PROFIX+res2.getId());
        return res2;
    }

    /**
     * 更新切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_CUSTOMER_UPDATE)
    public Object cacheCustomerUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Customer customerVO = (Customer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            Customer customer = (Customer) CACHE_CONTAINER.get(CACHE_CUSTOMER_PROFIX + customerVO.getId());
            if (null==customer) {
                customer = new Customer();
                BeanUtils.copyProperties(customerVO, customer);
                log.info("客户对象缓存已更新" + CACHE_CUSTOMER_PROFIX + customerVO.getId());
                CACHE_CONTAINER.put(CACHE_CUSTOMER_PROFIX+customer.getId(), customer);
            }
        }
        return isSuccess;
    }

    /**
     * 删除切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_CUSTOMER_DELETE)
    public Object cacheCustomerDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean proceed = (Boolean) joinPoint.proceed();
        if (proceed) {
            //删除缓存
            CACHE_CONTAINER.remove(CACHE_CUSTOMER_PROFIX + id);
            log.info("客户对象缓存已删除" + CACHE_CUSTOMER_PROFIX + id);
        }
        return proceed;
    }

    /**
     * 批量删除切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_CUSTOMER_BATCHDELETE)
    public Object cacheCustomerBatchDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Collection<Serializable> idList = (Collection<Serializable>) joinPoint.getArgs()[0];
        Boolean proceed = (Boolean) joinPoint.proceed();
        if (proceed) {
            for (Serializable id: idList) {
                //删除缓存
                CACHE_CONTAINER.remove(CACHE_CUSTOMER_PROFIX + id);
                log.info("客户对象缓存已删除" + CACHE_CUSTOMER_PROFIX + id);
            }
        }
        return proceed;
    }
}
