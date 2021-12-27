package com.xkcoding.log.aop.aspectj;

import java.lang.annotation.*;

/**
* @description
* @author Denley
* @date 2021/12/27 下午9:46
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RequestLog {

    /**
     * 描述
     * @return
     */
    String desc() default "";

}
