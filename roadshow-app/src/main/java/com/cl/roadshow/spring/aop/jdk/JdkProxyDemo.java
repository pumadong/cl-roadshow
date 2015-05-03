package com.cl.roadshow.spring.aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Spring实现AOP依赖两种底层技术：JDK动态代理和CgLib字节码增强
 * 
 * 演示JDK动态代理实现AOP的基本原理
 */
class JdkProxyDemo {
	public static void main(String[] args) throws Exception {
		HelloInterface hello = BeanFactory.getBean("com.cl.roadshow.spring.aop.jdk.HelloImpl", HelloInterface.class);
		hello.setInfo("zhangsan", "zhangsan@163.com");
	}
}

interface HelloInterface {
	public String setInfo(String name, String email);
}

class HelloImpl implements HelloInterface {
	public String setInfo(String name, String email) {
		System.out.println("\n\n===>setInfo函数内部输出此行...");
		return "OK";
	}
}

class AOPHandler implements InvocationHandler {
	private Object target;

	public AOPHandler(Object target) {
		this.target = target;
	}

	public void println(String str, Object... args) {
		System.out.println(str);
		if (args == null) {
			System.out.println("\t\t\t 未传入任何值...");
		} else {
			for (Object obj : args) {
				System.out.println("\t\t\t" + obj);
			}
		}
	}

	public Object invoke(Object proxyed, Method method, Object[] args) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		// 以下定义调用之前执行的操作
		System.out.println("\n\n===>调用方法名: " + method.getName());
		Class<?>[] variables = method.getParameterTypes();
		System.out.println("\n\t参数类型列表：\n");
		for (Class<?> typevariable : variables) {
			System.out.println("\t\t\t" + typevariable.getName());
		}
		println("\n\n\t传入参数值为：", args);

		// 以下开始执行代理方法
		System.out.println("\n\n开始执行method.invoke...调用代理方法...");
		Object result = method.invoke(target, args);

		// 以下定义调用之后执行的操作
		println("\n\n\t返回的参数为：", result);
		println("\n\n\t返回值类型为：", method.getReturnType());
		return result;
	}
}

class BeanFactory {
	public static Object getBean(String className) throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		Object obj = Class.forName(className).newInstance();
		InvocationHandler handler = new AOPHandler(obj);// 定义过滤器
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), handler);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String className, Class<T> c) throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		return (T) getBean(className);
	}
}