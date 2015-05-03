package com.cl.roadshow.spring.aop.aspectj;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 可以使用PointCut、Advice描述切点和增强，更好的方式是Advisor，他们都是通过xml配置
 * 
 * @AspectJ则采用注解来描述切点、增强，两者只是表达方式不同，描述内容的本质完全相同
 *
 *                                              就好比一个用中文、一个用英文讲述同一个伊索寓言一样
 */
@SuppressWarnings("resource")
public class AspectJProxyDemo {
	public static void main(String[] args) {
		String configPath = "com/cl/roadshow/spring/aop/aspectj/beans.xml";
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
 * AspectJ增强
 *
 */
@Aspect
class PreGreetingAspect {
	@Before("execution(* greeTo(..))")
	public void beforeGreeting() {
		System.out.println("How are you");
	}
}