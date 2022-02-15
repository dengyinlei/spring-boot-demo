package com.example.javacore.designpattern.proxy;

/**
 * @ClassName : UserManagerImpl  //类名
 * @Description : 用户管理实现类  //描述
 * @Author : HTB  //作者
 * @Date: 2022-02-13 10:03  //时间
 */
public class UserManagerImpl implements UserManager {
    @Override
    public void addUser(String userId, String userName) {
        System.out.println(" add user");
    }

    @Override
    public void delUser(String userId) {
        System.out.println("delete user");
    }
}
