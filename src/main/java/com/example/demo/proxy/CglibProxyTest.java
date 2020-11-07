package com.example.demo.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxyTest implements MethodInterceptor {

    private CglibProxyTest(){}

    public static <T extends Target> Target newProxyInstance(Class<T> targetInstanceClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetInstanceClass);
        enhancer.setCallback(new CglibProxyTest());
        return (Target) enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        return methodProxy.invokeSuper(obj, args);
    }
}
