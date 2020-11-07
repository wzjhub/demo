package com.example.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKDynamicProxyTest implements InvocationHandler {
    private Target target;

    private JDKDynamicProxyTest(Target target){
        this.target = target;
    }

    public static Target newProxyInstance(Target target) {
        return (Target) Proxy.newProxyInstance(JDKDynamicProxyTest.class.getClassLoader(),
                new Class<?>[] {Target.class},
                new JDKDynamicProxyTest(target));
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(target, args);
    }
}
