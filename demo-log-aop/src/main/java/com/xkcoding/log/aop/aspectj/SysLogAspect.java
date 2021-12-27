package com.xkcoding.log.aop.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName : WhitelistAspect  //类名
 * @Description : 白名单注解  //描述
 * @Author : HTB  //作者
 * @Date: 2021-12-27 21:06  //时间
 */
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SysLogAspect {
    @Around(value = "pointCut() && @annotation(sysLog)")
    public Object around(ProceedingJoinPoint joinPoint, SysLog sysLog) throws Throwable {
//       //逻辑开始时间
      long beginTime = System.currentTimeMillis();
//
//      //执行方法
      Object result = joinPoint.proceed();
//
//      //todo，保存日志，自己完善
//      saveLog(point,beginTime);
//
        String logTrackValue = sysLog.value();

        return result;



    }

   //这里需要拦截的肯定是@SysLog这个注解，只要方法上标注了该注解都将会被拦截，表达式如下
    @Pointcut("@annotation(com.xkcoding.log.aop.aspectj.SysLog)")
    public void pointCut() {
    }
}
