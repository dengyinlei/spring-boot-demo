package com.xkcoding.log.aop.aspectj;

import cn.hutool.core.date.StopWatch;
import cn.hutool.json.JSONUtil;
import com.xkcoding.log.aop.entity.RequestLogInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName : RequestLogAop  //类名
 * @Description : 日志切面类  //描述
 * @Author : HTB  //作者
 * @Date: 2021-12-27 21:48  //时间
 */
@Component
// 标识这是一个切面
@Aspect
@Slf4j
public class RequestLogAop {

    /**
     * @Aspect：声明该类为一个注解类
     * @Pointcut：定义一个切点，后面跟随一个表达式，表达式可以定义为切某个注解，也可以切某个 package 下的方法
     * @Before: 在切点之前，织入相关代码
     * @After: 在切点之后，织入相关代码
     * @AfterReturning: 在切点返回内容后，织入相关代码，一般用于对返回值做些加工处理的场景
     * @AfterThrowing: 用来处理当织入的代码抛出异常后的逻辑处理
     * @Around: 环绕，可以在切入点前后织入代码，并且可以自由的控制何时执行切点
     */

    /**
     * 被 @requestLog 所注解的切点
     * @param requestLog
     */
    @Pointcut("@annotation(requestLog)")
    public void requestLogPointcut(RequestLog requestLog){}

    /**
     * 环绕增强
     * @param pjp
     * @param requestLog
     */
    @Around(value = "requestLogPointcut(requestLog)", argNames = "pjp,requestLog")
    public Object around(ProceedingJoinPoint pjp, RequestLog requestLog) throws Throwable {

        // 请求信息
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 使用包装类目的为了减少多请求情况下 日志串行的问题
        RequestLogInfo info = RequestLogInfo.builder()
            .requestUri(request.getRequestURI())
            .apiDesc(requestLog.desc())
            .httpMethod(request.getMethod())
            .classMethod(pjp.getSignature().getDeclaringTypeName() + "." +pjp.getSignature().getName())
            .requestIp(request.getRemoteHost())
            .requestParams(JSONUtil.toJsonStr(pjp.getArgs()))
            .build();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("【请求链接】:{}",request.getRequestURI());
        log.info("【接口描述】:{}",requestLog.desc());
        log.info("【请求类型】:{}",request.getMethod());
        log.info("【请求方法】:{}.{}",pjp.getSignature().getDeclaringTypeName(),pjp.getSignature().getName());
        log.info("【请求IP】:{},{}:{}",request.getRemoteAddr(),request.getRemoteHost(),request.getRemotePort());
        log.info("【请求参数】:{}", JSONUtil.toJsonStr(pjp.getArgs()));

        // 执行原方法逻辑
        Object result = pjp.proceed();

        stopWatch.stop();

        info.setCostTimeMillis(stopWatch.getTotalTimeMillis());
        info.setResult(JSONUtil.toJsonStr(result));
        log.info("【requestLog】:{}",JSONUtil.toJsonStr(info));
        log.info("【接口花费时间统计】:{} 秒",stopWatch.getTotalTimeSeconds());
        log.info("【接口花费时间调度】:{}",stopWatch.prettyPrint());
        log.info("【请求返回结果】:{}",JSONUtil.toJsonStr(result));
        return result;
    }

}
