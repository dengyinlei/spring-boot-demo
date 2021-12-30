package com.xkcoding.log.aop.aspectj;

import java.lang.annotation.*;

/**
* @description
* @author Denley
* @date 2021/12/27 下午9:15
*/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE,ElementType.METHOD,ElementType.TYPE})
public @interface SysLog {
    String value() default "";
}
