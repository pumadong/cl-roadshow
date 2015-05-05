package com.cl.roadshow.spring.aop.advice;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Spring AOP，按照增强在目标类方法的连接点位置，有5类：前置、后置、环绕、异常抛出、引介
 * 
 * 本例演示环绕增强：对所有的say方法进行环绕增加，并自定义注解，根据注解决定如何增强
 */
@SuppressWarnings("resource")
public class SurroundAdviceDemo {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "com/cl/roadshow/spring/aop/advice/beans-withannotation.xml" });

		// 测试自定义注解
		PersonWithAnnotation personWithAnnotation = ctx.getBean("personWithAnnotation", PersonWithAnnotation.class);
		System.out.println(personWithAnnotation.say());

		System.out.println("\n");

		// 没有自定义注解的类
		PersonWithoutAnnotation personWithoutAnnotation = ctx.getBean("personWithoutAnnotation",
				PersonWithoutAnnotation.class);
		System.out.println(personWithoutAnnotation.say());
	}
}

/**
 * 自定义注解
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@interface AnnotationDemo {
	String value();
}

/**
 * 
 * 环绕增强实现类
 *
 */
@Component("annotationAdvice")
class AnnotationAdvice implements MethodInterceptor {
	public Object invoke(MethodInvocation invocation) throws Throwable {
		if (invocation.getMethod().isAnnotationPresent(AnnotationDemo.class)) {
			String content = null;
			Annotation annotation = invocation.getMethod().getAnnotation(AnnotationDemo.class);
			if (annotation != null) {
				content = ((AnnotationDemo) annotation).value();
				System.out.println("获取到注解的内容：" + content);
			}

			System.out.println("方法调用之前要进行的工作");
			Object o = invocation.proceed();
			System.out.println("方法调用之后要进行的工作");

			return o;

		} else {
			return invocation.proceed();
		}
	}
}

/**
 * say()方法带有注解的类
 * 
 *
 */
@Component("personWithAnnotation")
class PersonWithAnnotation {
	@AnnotationDemo("Annotation's Content")
	public String say() {
		System.out.println("say() with annotation");
		return "I'm say() with annotation";
	}
}

/**
 * say()方法没有注解的类
 * 
 *
 */
@Component("personWithoutAnnotation")
class PersonWithoutAnnotation {

	public String say() {
		System.out.println("say() without annotation");
		return "I'm say() without annotation";
	}
}
