package org.example;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: Cai
 * @description: TODO
 * @date: 2024/7/29 13:41
 * @version: 1.0
 */
public class TeacherProxy implements MethodInterceptor {
    private Object target;

    public TeacherProxy(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        Enhancer enhancer = new Enhancer();
        // 生成子类
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("befpr");
        Object res = method.invoke(target, objects);
        System.out.println("after");
        return res;
    }
}
