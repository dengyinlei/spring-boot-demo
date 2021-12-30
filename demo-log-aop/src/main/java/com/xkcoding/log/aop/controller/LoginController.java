package com.xkcoding.log.aop.controller;

import com.xkcoding.log.aop.annotation.RepeatSubmit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : LoginController  //类名
 * @Description : 登录controller  //描述
 * @Author : HTB  //作者
 * @Date: 2021-12-27 22:46  //时间
 */
@RestController
@RequestMapping("/user")
//标注了@RepeatSubmit注解，全部的接口都需要拦截
@RepeatSubmit
public class LoginController {

    @RequestMapping("/login")
    public String login(){
        return "login success";
    }
}
