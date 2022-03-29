package com.wanfeng.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 防止redis宕机，影响逻辑   比如redis没开就会导致先查询cache的代码都会报错
 * @author wanfeng
 * @created 2022/3/29 16:23
 * @package com.wanfeng.log
 */
@Aspect
@Order(2)
@Component
public class RedisCacheAspect {
    private static Logger LOGGER = LoggerFactory.getLogger(RedisCacheAspect.class);

    /**
     * 所有的关于缓存的方法都注入切面
     */
    @Pointcut("execution(public * com.wanfeng.service.*CacheService.*(..))")
    public void cacheAspect() {
    }

    @Around("cacheAspect()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint){
        System.out.println("-------------------------redis切面启动----------------------------------");
        Object res = null;
        try{
            // 如果redis宕机了 这行就会报错
            System.out.println("-------------------------redis方法启动----------------------------------");
            res = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            LOGGER.error(e.getMessage());
        }
        System.out.println("-------------------------redis切面结束----------------------------------");
        return res;
    }

}
