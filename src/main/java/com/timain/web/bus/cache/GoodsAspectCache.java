package com.timain.web.bus.cache;

import com.timain.web.bus.pojo.Goods;
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
 * 商品缓存处理
 * @author yyf
 * @version 1.0
 * @date 2020/2/28 17:40
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class GoodsAspectCache {

    //声明日志信息
    private Log log = LogFactory.getLog(GoodsAspectCache.class);

    //声明一个缓存容器
    private Map<String, Object> CACHE_CONTAINER = CachePool.CACHE_CONTAINER;

    //声明切面表达式
    private static final String POINTCUT_GOODS_ADD = "execution(* com.timain.web.bus.service.impl.GoodsServiceImpl.save(..))";
    private static final String POINTCUT_GOODS_UPDATE = "execution(* com.timain.web.bus.service.impl.GoodsServiceImpl.updateById(..))";
    private static final String POINTCUT_GOODS_GET = "execution(* com.timain.web.bus.service.impl.GoodsServiceImpl.getById(..))";
    private static final String POINTCUT_GOODS_DELETE = "execution(* com.timain.web.bus.service.impl.GoodsServiceImpl.removeById(..))";


    //前缀
    private static final String CACHE_GOODS_PROFIX = "goods:";

    /**
     * 添加切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_GOODS_ADD)
    public Object cacheGoodsAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Goods object = (Goods) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res) {
            CACHE_CONTAINER.put(CACHE_GOODS_PROFIX + object.getId(), object);
        }
        return res;
    }

    /**
     * 查询切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_GOODS_GET)
    public Object cacheGoodsGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer object = (Integer) joinPoint.getArgs()[0];
        //从缓存里取
        Object res1 = CACHE_CONTAINER.get(CACHE_GOODS_PROFIX + object);
        if (null!=res1) {
            log.info("已从缓存中找到商品对象" + CACHE_GOODS_PROFIX + object);
            return res1;
        }
        Goods res2 = (Goods) joinPoint.proceed();
        CACHE_CONTAINER.put(CACHE_GOODS_PROFIX+res2.getId(), res2);
        log.info("未从缓存里面找到商品对象，去数据库查询并放到缓存"+CACHE_GOODS_PROFIX+res2.getId());
        return res2;
    }

    /**
     * 更新切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_GOODS_UPDATE)
    public Object cacheGoodsUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Goods goodsVO = (Goods) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            Goods goods = (Goods) CACHE_CONTAINER.get(CACHE_GOODS_PROFIX + goodsVO.getId());
            if (null==goods) {
                goods = new Goods();
                BeanUtils.copyProperties(goodsVO, goods);
                log.info("商品对象缓存已更新" + CACHE_GOODS_PROFIX + goodsVO.getId());
                CACHE_CONTAINER.put(CACHE_GOODS_PROFIX+goods.getId(), goods);
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
    @Around(value = POINTCUT_GOODS_DELETE)
    public Object cacheGoodsDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean proceed = (Boolean) joinPoint.proceed();
        if (proceed) {
            //删除缓存
            CACHE_CONTAINER.remove(CACHE_GOODS_PROFIX + id);
            log.info("商品对象缓存已删除" + CACHE_GOODS_PROFIX + id);
        }
        return proceed;
    }
}
