package org.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: Cai
 * @description: TODO
 * @date: 2024/7/29 13:41
 * @version: 1.0
 */
public class TeacherProxy {
    private Object target;

    public TeacherProxy(Object object) {
        this.target = object;
    }

    public Object getNewProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("before teach");
                        Object returnval = method.invoke(target, args);
                        System.out.println("after teach");
                        return returnval;
                    }
                });
    }
}
