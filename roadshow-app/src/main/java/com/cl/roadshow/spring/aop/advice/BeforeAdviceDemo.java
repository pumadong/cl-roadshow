package com.cl.roadshow.spring.aop.advice;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring AOP，按照增强在目标类方法的连接点位置，有5类：前置、后置、环绕、异常抛出、引介
 * 
 * 本例演示前置增强：对所有的greeT方法增加前置增强代码
 */
@SuppressWarnings("resource")
public class BeforeAdviceDemo {
	public static void main(String[] args) {
		String configPath = "com/cl/roadshow/spring/aop/advice/beans.xml";
		ApplicationContext ctx = new ClassPathXmlApplicationContext(configPath);
		Waiter waiter = (Waiter) ctx.getBean("waiter");
		waiter.greeTo("John");
		waiter.serveTo("Tom");
	}
}

interface Waiter {
	void greeTo(String name);

	void serveTo(String name);
}

class NaiveWaiter implements Waiter {
	public void greeTo(String name) {
		System.out.println("greet to " + name + "...");
	}

	public void serveTo(String name) {
		System.out.println("serving " + name + "...");
	}
}

/**
 * 前置增强
 *
 */
class GreetingBeforeAdvice implements MethodBeforeAdvice {
	public void before(Method method, Object[] args, Object obj) throws Throwable {
		String clientName = (String) args[0];
		System.out.println("How are you! Mr." + clientName + ".");
	}
}