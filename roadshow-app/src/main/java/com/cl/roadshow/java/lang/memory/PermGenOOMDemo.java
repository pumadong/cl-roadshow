package com.cl.roadshow.java.lang.memory;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 方法区溢出
 * 
 * java -XX:+TraceClassUnloading -XX:PermSize=10m -XX:MaxPermSize=10
 * PermGenOOMDemo
 *
 */
public class PermGenOOMDemo {
	public static void main(String[] args) {
		while (true) {
			createProxy(PermGenOOMDemo.class);
		}
	}

	public static Object createProxy(Class<?> targetClass) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetClass);
		enhancer.setUseCache(false);

		enhancer.setCallback(new MethodInterceptor() {
			public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
				return methodProxy.invokeSuper(object, args);
			}
		});
		return enhancer.create();
	}
}
