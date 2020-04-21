package com.timain.web.bus.cache;

import com.timain.web.bus.pojo.Provider;
import com.timain.web.sys.cache.CachePool;
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
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 供应商缓存处理
 * @author yyf
 * @version 1.0
 * @date 2020/2/28 17:40
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class ProviderAspectCache {

    //声明日志信息
    private Log log = LogFactory.getLog(ProviderAspectCache.class);

    //声明一个缓存容器
    private Map<String, Object> CACHE_CONTAINER = CachePool.CACHE_CONTAINER;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    //声明切面表达式
    private static final String POINTCUT_PROVIDER_ADD = "execution(* com.timain.web.bus.service.impl.ProviderServiceImpl.save(..))";
    private static final String POINTCUT_PROVIDER_UPDATE = "execution(* com.timain.web.bus.service.impl.ProviderServiceImpl.updateById(..))";
    private static final String POINTCUT_PROVIDER_GET = "execution(* com.timain.web.bus.service.impl.ProviderServiceImpl.getById(..))";
    private static final String POINTCUT_PROVIDER_DELETE = "execution(* com.timain.web.bus.service.impl.ProviderServiceImpl.removeById(..))";
    private static final String POINTCUT_PROVIDER_BATCHDELETE = "execution(* com.timain.web.bus.service.impl.ProviderServiceImpl.removeByIds(..))";


    //前缀
    private static final String CACHE_PROVIDER_PROFIX = "provider:";

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
    @Around(value = POINTCUT_PROVIDER_ADD)
    public Object cacheProviderAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        initSerizler();
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        //取出第一个参数
        Provider object = (Provider) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res) {
            //CACHE_CONTAINER.put(CACHE_PROVIDER_PROFIX + object.getId(), object);
            opsForValue.set(CACHE_PROVIDER_PROFIX + object.getId(), object);
        }
        return res;
    }

    /**
     * 查询切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_PROVIDER_GET)
    public Object cacheProviderGet(ProceedingJoinPoint joinPoint) throws Throwable {
        initSerizler();
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        //取出第一个参数
        Integer object = (Integer) joinPoint.getArgs()[0];
        //从缓存里取
        //Object res1 = CACHE_CONTAINER.get(CACHE_PROVIDER_PROFIX + object);
        Object res1 = opsForValue.get(CACHE_PROVIDER_PROFIX + object);
        if (null!=res1) {
            log.info("已从缓存中找到供应商对象" + CACHE_PROVIDER_PROFIX + object);
            return res1;
        }
        Provider res2 = (Provider) joinPoint.proceed();
        //CACHE_CONTAINER.put(CACHE_PROVIDER_PROFIX+res2.getId(), res2);
        opsForValue.set(CACHE_PROVIDER_PROFIX+res2.getId(), res2);
        log.info("未从缓存里面找到供应商对象，去数据库查询并放到缓存"+CACHE_PROVIDER_PROFIX+res2.getId());
        return res2;
    }

    /**
     * 更新切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_PROVIDER_UPDATE)
    public Object cacheProviderUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        initSerizler();
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        //取出第一个参数
        Provider providerVO = (Provider) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            //Provider provider = (Provider) CACHE_CONTAINER.get(CACHE_PROVIDER_PROFIX + providerVO.getId());
            Provider provider = (Provider) opsForValue.get(CACHE_PROVIDER_PROFIX + providerVO.getId());
            if (null==provider) {
                provider = new Provider();
                BeanUtils.copyProperties(providerVO, provider);
                log.info("供应商对象缓存已更新" + CACHE_PROVIDER_PROFIX + providerVO.getId());
                //CACHE_CONTAINER.put(CACHE_PROVIDER_PROFIX+provider.getId(), provider);
                opsForValue.set(CACHE_PROVIDER_PROFIX+provider.getId(), provider);
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
    @Around(value = POINTCUT_PROVIDER_DELETE)
    public Object cacheProviderDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        initSerizler();
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean proceed = (Boolean) joinPoint.proceed();
        if (proceed) {
            //删除缓存
            //CACHE_CONTAINER.remove(CACHE_PROVIDER_PROFIX + id);
            redisTemplate.delete(CACHE_PROVIDER_PROFIX + id);
            log.info("供应商对象缓存已删除" + CACHE_PROVIDER_PROFIX + id);
        }
        return proceed;
    }

    /**
     * 批量删除切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_PROVIDER_BATCHDELETE)
    public Object cacheProviderBatchDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        initSerizler();
        //取出第一个参数
        Collection<Serializable> idList = (Collection<Serializable>) joinPoint.getArgs()[0];
        Boolean proceed = (Boolean) joinPoint.proceed();
        if (proceed) {
            for (Serializable id: idList) {
                //删除缓存
                //CACHE_CONTAINER.remove(CACHE_PROVIDER_PROFIX + id);
                redisTemplate.delete(CACHE_PROVIDER_PROFIX + id);
                log.info("供应商对象缓存已删除" + CACHE_PROVIDER_PROFIX + id);
            }
        }
        return proceed;
    }
}
