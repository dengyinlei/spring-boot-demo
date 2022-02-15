package com.example.javacore.designpattern.proxy;

/**
 * @ClassName : Client  //类名
 * @Description : 客户端调用  //描述
 * @Author : HTB  //作者
 * @Date: 2022-02-13 10:12  //时间
 */
public class Client {
    public static void main(String[] args) {
        UserManager userManager = new UserManagerImplProxy(new UserManagerImpl());
        userManager.addUser("111", "denley");

        /**
         *  动态代理对象步骤
         *      1、 创建一个与代理对象相关联的 InvocationHandler，以及真实的委托类实例
         *      2、Proxy类的getProxyClass静态方法生成一个动态代理类stuProxyClass，该类继承Proxy类，实现 Person.java接口；JDK动态代理的特点是代理类必须继承Proxy类
         *      3、通过代理类 proxyClass 获得他的带InvocationHandler 接口的构造函数 ProxyConstructor
         *      4、通过 构造函数实例 ProxyConstructor 实例化一个代理对象，并将  InvocationHandler 接口实例传递给代理类。
         */

        //创建一个与代理类相关联的InvocationHandler,每一个代理类都有一个关联的 InvocationHandler，并将代理类引用传递进去
        JDKProxy JDKProxy = new JDKProxy();
        UserManager userManagerProxy = (UserManager) JDKProxy.newProxyInstance(new UserManagerImpl());
        userManagerProxy.addUser("222", "dagd");

        CglibProxy cglibProxy = new CglibProxy(new UserManagerImpl());
        UserManager userManager1 = (UserManager) cglibProxy.getProxy();
        userManager1.addUser("333", "dagg");
    }
}
