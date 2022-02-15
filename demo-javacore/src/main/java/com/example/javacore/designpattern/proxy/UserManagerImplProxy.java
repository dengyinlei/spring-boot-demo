package com.example.javacore.designpattern.proxy;

/**
 * @ClassName : UserManagerImplProxy  //类名
 * @Description : 用户管理代理类  //描述
 * @Author : HTB  //作者
 * @Date: 2022-02-13 10:06  //时间
 */
public class UserManagerImplProxy implements UserManager {

    private UserManager userManager;

    public UserManagerImplProxy(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public void addUser(String userId, String userName) {
        System.out.println("日志打印");
        userManager.addUser(userId, userName);
        System.out.println("添加用户成功");
    }

    @Override
    public void delUser(String userId) {
        userManager.delUser(userId);
    }
}
