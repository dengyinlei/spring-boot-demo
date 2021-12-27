package com.xkcoding.log.aop.interceptor;

import com.xkcoding.log.aop.annotation.RepeatSubmit;
import com.xkcoding.log.aop.exception.RepeatSubmitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName : RepeatSubmitInterceptor  //类名
 * @Description : 重复请求的拦截器  //描述
 * @Author : HTB  //作者
 * @Date: 2021-12-27 22:04  //时间
 *
 *
 * 开发中可能会经常遇到短时间内由于用户的重复点击导致几秒之内重复的请求，可能就是在这几秒之内由于各种问题，比如网络，事务的隔离性等等问题导致了数据的重复等问题，因此在日常开发中必须规避这类的重复请求操作，今天就用拦截器简单的处理一下这个问题。
 *
 * 思路
 * 在接口执行之前先对指定接口（比如标注某个注解的接口）进行判断，如果在指定的时间内（比如5秒）已经请求过一次了，则返回重复提交的信息给调用者。
 *
 * 根据什么判断这个接口已经请求了？
 * 根据项目的架构可能判断的条件也是不同的，比如IP地址，用户唯一标识、请求参数、请求URI等等其中的某一个或者多个的组合。
 *
 * 这个具体的信息存放在哪里？
 * 由于是短时间内甚至是瞬间并且要保证定时失效，肯定不能存在事务性数据库中了，因此常用的几种数据库中只有Redis比较合适了。
 *
 *
 */
@Component
public class RepeatSubmitInterceptor  implements HandlerInterceptor {
    /**
     * Redis的API
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * preHandler方法，在controller方法之前执行
     *
     * 判断条件仅仅是用了uri，实际开发中根据实际情况组合一个唯一识别的条件。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod){
            //只拦截标注了@RepeatSubmit该注解
            HandlerMethod method=(HandlerMethod)handler;
            //标注在方法上的@RepeatSubmit
            RepeatSubmit repeatSubmitByMethod = AnnotationUtils.findAnnotation(method.getMethod(),RepeatSubmit.class);
            //标注在controler类上的@RepeatSubmit
            RepeatSubmit repeatSubmitByCls = AnnotationUtils.findAnnotation(method.getMethod().getDeclaringClass(), RepeatSubmit.class);
            //没有限制重复提交，直接跳过
            if (Objects.isNull(repeatSubmitByMethod)&&Objects.isNull(repeatSubmitByCls)) {
                return true;
            }
            // todo: 组合判断条件，这里仅仅是演示，实际项目中根据架构组合条件
            //请求的URI
            String uri = request.getRequestURI() + request.getRemoteAddr();

            //标注在方法上的超时时间会覆盖掉类上的时间，因为如下一段代码：
            //这段代码的失效时间先取值repeatSubmitByMethod中配置的，如果为null，则取值repeatSubmitByCls配置的。
            //存在即返回false，不存在即返回true
            Boolean ifAbsent = stringRedisTemplate.opsForValue().setIfAbsent(uri, "", Objects.nonNull(repeatSubmitByMethod)?repeatSubmitByMethod.seconds():repeatSubmitByCls.seconds(), TimeUnit.SECONDS);

            //如果存在，表示已经请求过了，直接抛出异常，由全局异常进行处理返回指定信息
            if (ifAbsent!=null&&!ifAbsent) {
                throw new RepeatSubmitException();
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 方法在Controller方法执行结束后执行
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在view视图渲染完成后执行
    }

}
