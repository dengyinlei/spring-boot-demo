package com.xkcoding.log.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * <p>
 * 启动类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-10-01 22:05
 */
@EnableAspectJAutoProxy
@SpringBootApplication
public class SpringBootDemoLogAopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoLogAopApplication.class, args);
    }
}
