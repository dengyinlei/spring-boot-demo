package com.example.javacore.designpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName : LogHandler  //类名
 * @Description : 动态代理类  //描述
 * @Author : HTB  //作者
 * @Date: 2022-02-13 10:21  //时间
 */

/**
 * JDKProxy 类 实现InvocationHandler接口，
 * 这个类中持有一个被代理对象(委托类)的实例target。
 * 该类别JDK Proxy类回调
 * InvocationHandler 接口中有一个invoke方法，
 * 当一个代理实例的方法被调用时，代理方法将被编码并分发到 InvocationHandler接口的invoke方法执行。
 */

public class JDKProxy implements InvocationHandler {
    //要代理的目标类
    /**
     * 被代理对象引用，invoke 方法里面method 需要使用这个 被代理对象
     */
    private Object target;

    /**
     * 在
     * @param proxy  代表动态生成的 动态代理 对象实例
     * @param method 代表被调用委托类的接口方法，和生成的代理类实例调用的接口方法是一致的，它对应的Method 实例
     * @param args   代表调用接口方法对应的Object参数数组，如果接口是无参，则为null； 对于原始数据类型返回的他的包装类型。
     * @return
     * @throws Throwable
     */

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("start --->");
        for (Object arg : args) {
            System.out.println(arg);
        }
        System.out.println("print log before target method");
        Object result = method.invoke(target, args);
        System.out.println("print log after target method");
        return result;
    }


    public Object newProxyInstance(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
            target.getClass().getInterfaces(),
            this);
    }

}
