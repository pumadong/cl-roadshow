package com.cl.roadshow.spring.aop.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Spring实现AOP依赖两种底层技术：JDK动态代理和CgLib字节码增强
 * 
 * 演示CgLib动态代理L实现AOP的基本原理
 */
public class CgLibProxyDemo {
	public static void main(String[] args) throws Exception {
		CglibProxy proxy = new CglibProxy();

		ForumServiceImpl forumService = (ForumServiceImpl) proxy.getProxy(ForumServiceImpl.class);
		forumService.removeTopic(100);
	}
}

class CglibProxy implements MethodInterceptor {
	private Enhancer enhancer = new Enhancer();

	public Object getProxy(Class<?> clazz) {
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		return enhancer.create(); // 通过字节码技术动态创建子类实例
	}

	// 拦截父类所有方法的调用
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		PerformanceMonitor.begin(obj.getClass().getName() + "." + method.getName());
		Object result = proxy.invokeSuper(obj, args);
		PerformanceMonitor.end();
		return result;
	}
}

// 将要被注入切面功能的类
class ForumServiceImpl {
	public void removeTopic(int topicId) throws Exception {
		System.out.println("模拟删除Topic记录：" + topicId);
		// Thread.sleep(20);
	}
}

// 性能监视类（切面类）
class PerformanceMonitor {
	private static ThreadLocal<MethodPerformance> performanceRecord = new ThreadLocal<MethodPerformance>();

	public static void begin(String method) {
		System.out.println("begin monitor...");
		MethodPerformance mp = new MethodPerformance(method);
		performanceRecord.set(mp);
	}

	public static void end() {
		System.out.println("end monitor...");
		MethodPerformance mp = performanceRecord.get();

		mp.printPerformance();
	}
}

class MethodPerformance {
	private long begin, end;
	private String serviceMethod;

	public MethodPerformance(String serviceMethod) {
		this.serviceMethod = serviceMethod;
		this.begin = System.currentTimeMillis();
	}

	public void printPerformance() {
		end = System.currentTimeMillis();
		long elapse = end - begin;
		System.out.println(serviceMethod + "花费" + elapse + "毫秒。");
	}
}