package com.timain.web.sys.cache;

import com.timain.web.sys.pojo.Dept;
import com.timain.web.sys.pojo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 缓存处理
 * @author yyf
 * @version 1.0
 * @date 2020/1/28 21:51
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class AspectCache {

    //声明日志信息
    private Log log = LogFactory.getLog(AspectCache.class);

    //声明一个缓存容器
    private Map<String, Object> CACHE_CONTAINER = CachePool.CACHE_CONTAINER;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    //声明切面表达式
    private static final String POINTCUT_DEPT_ADD = "execution(* com.timain.web.sys.service.impl.DeptServiceImpl.save(..))";
    private static final String POINTCUT_DEPT_UPDATE = "execution(* com.timain.web.sys.service.impl.DeptServiceImpl.updateById(..))";
    private static final String POINTCUT_DEPT_GET = "execution(* com.timain.web.sys.service.impl.DeptServiceImpl.getById(..))";
    private static final String POINTCUT_DEPT_DELETE = "execution(* com.timain.web.sys.service.impl.DeptServiceImpl.removeById(..))";

    //前缀
    private static final String CACHE_DEPT_PROFIX = "dept:";

    //初始化序列化方式
    public void initSerizler() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    }

    /**
     * 添加切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_DEPT_ADD)
    public Object cacheDeptAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        initSerizler();
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        //取出第一个参数
        Dept object = (Dept) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res) {
            opsForValue.set(CACHE_DEPT_PROFIX + object.getId(), object);
            //CACHE_CONTAINER.put(CACHE_DEPT_PROFIX + object.getId(), object);
        }
        return res;
    }

    /**
     * 查询切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_DEPT_GET)
    public Object cacheDeptGet(ProceedingJoinPoint joinPoint) throws Throwable {
        initSerizler();
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        //取出第一个参数
        Integer object = (Integer) joinPoint.getArgs()[0];
        //从缓存里取
        //Object res1 = CACHE_CONTAINER.get(CACHE_DEPT_PROFIX + object);
        Object res1 = opsForValue.get(CACHE_DEPT_PROFIX + object);
        if (null!=res1) {
            log.info("已从缓存中找到部门对象" + CACHE_DEPT_PROFIX + object);
            return res1;
        }
        Dept res2 = (Dept) joinPoint.proceed();
        //CACHE_CONTAINER.put(CACHE_DEPT_PROFIX+res2.getId(), res2);
        opsForValue.set(CACHE_DEPT_PROFIX+res2.getId(), res2);
        log.info("未从缓存里面找到部门对象，去数据库查询并放到缓存"+CACHE_DEPT_PROFIX+res2.getId());
        return res2;
    }

    /**
     * 更新切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_DEPT_UPDATE)
    public Object cacheDeptUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        initSerizler();
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        //取出第一个参数
        Dept deptVO = (Dept) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            //Dept dept = (Dept) CACHE_CONTAINER.get(CACHE_DEPT_PROFIX + deptVO.getId());
            Dept dept = (Dept) opsForValue.get(CACHE_DEPT_PROFIX + deptVO.getId());
            if (null==dept) {
                dept = new Dept();
                BeanUtils.copyProperties(deptVO, dept);
                log.info("部门对象缓存已更新" + CACHE_DEPT_PROFIX + deptVO.getId());
                //CACHE_CONTAINER.put(CACHE_DEPT_PROFIX+dept.getId(), dept);
                opsForValue.set(CACHE_DEPT_PROFIX+dept.getId(), dept);
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
    @Around(value = POINTCUT_DEPT_DELETE)
    public Object cacheDeptDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        initSerizler();
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean proceed = (Boolean) joinPoint.proceed();
        if (proceed) {
            //删除缓存
            //CACHE_CONTAINER.remove(CACHE_DEPT_PROFIX + id);
            redisTemplate.delete(CACHE_DEPT_PROFIX + id);
            log.info("部门对象缓存已删除" + CACHE_DEPT_PROFIX + id);
        }
        return proceed;
    }



    //声明切面表达式
    private static final String POINTCUT_USER_ADD = "execution(* com.timain.web.sys.service.impl.UserServiceImpl.save(..))";
    private static final String POINTCUT_USER_UPDATE = "execution(* com.timain.web.sys.service.impl.UserServiceImpl.updateById(..))";
    private static final String POINTCUT_USER_GET = "execution(* com.timain.web.sys.service.impl.UserServiceImpl.getById(..))";
    private static final String POINTCUT_USER_DELETE = "execution(* com.timain.web.sys.service.impl.UserServiceImpl.removeById(..))";

    private static final String CACHE_USER_PROFIX = "user:";

    /**
     * 添加切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_USER_ADD)
    public Object cacheUserAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        initSerizler();
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        // 取出第一个参数
        User object = (User) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res) {
            //CACHE_CONTAINER.put(CACHE_USER_PROFIX + object.getId(), object);
            opsForValue.set(CACHE_USER_PROFIX + object.getId(), object);
        }
        return res;
    }

    /**
     * 查询切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_USER_GET)
    public Object cacheUserGet(ProceedingJoinPoint joinPoint) throws Throwable {
        initSerizler();
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        // 取出第一个参数
        Integer object = (Integer) joinPoint.getArgs()[0];
        // 从缓存里面取
        //Object res1 = CACHE_CONTAINER.get(CACHE_USER_PROFIX + object);
        Object res1 = opsForValue.get(CACHE_USER_PROFIX + object);
        if (res1 != null) {
            log.info("已从缓存里面找到用户对象" + CACHE_USER_PROFIX + object);
            return res1;
        } else {
            User res2 = (User) joinPoint.proceed();
            //CACHE_CONTAINER.put(CACHE_USER_PROFIX + res2.getId(), res2);
            opsForValue.set(CACHE_USER_PROFIX + res2.getId(), res2);
            log.info("未从缓存里面找到用户对象，去数据库查询并放到缓存"+CACHE_USER_PROFIX+res2.getId());
            return res2;
        }
    }

    /**
     * 更新切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_USER_UPDATE)
    public Object cacheUserUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        initSerizler();
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        // 取出第一个参数
        User userVo = (User) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            //User user = (User) CACHE_CONTAINER.get(CACHE_USER_PROFIX + userVo.getId());
            User user = (User) opsForValue.get(CACHE_USER_PROFIX + userVo.getId());
            if (null == user) {
                user = new User();
            }
            BeanUtils.copyProperties(userVo, user);
            log.info("用户对象缓存已更新" + CACHE_USER_PROFIX + userVo.getId());
            //CACHE_CONTAINER.put(CACHE_USER_PROFIX + user.getId(), user);
            opsForValue.set(CACHE_USER_PROFIX + user.getId(), user);
        }
        return isSuccess;
    }

    /**
     * 删除切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_USER_DELETE)
    public Object cacheUserDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        initSerizler();
        // 取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            // 删除缓存
            //CACHE_CONTAINER.remove(CACHE_USER_PROFIX + id);
            redisTemplate.delete(CACHE_USER_PROFIX + id);
            log.info("用户对象缓存已删除" + CACHE_USER_PROFIX + id);
        }
        return isSuccess;
    }
}
