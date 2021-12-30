package com.xkcoding.log.aop.config;

import com.xkcoding.log.aop.interceptor.RepeatSubmitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName : WebConfig  //类名
 * @Description : 自定义拦截器  //描述
 * @Author : HTB  //作者
 * @Date: 2021-12-27 22:38  //时间
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private RepeatSubmitInterceptor repeatSubmitInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //不拦截的uri
        final String[] commonExclude = {"/error", "/files/**"};
        registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/*").excludePathPatterns(commonExclude).order(1);

    }
}
