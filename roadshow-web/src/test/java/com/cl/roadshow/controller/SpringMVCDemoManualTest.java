package com.cl.roadshow.controller;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMVCDemoManualTest {

	private static SpringMVCDemo springMVCDemo;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		springMVCDemo = (SpringMVCDemo) ctx.getBean("springMVCDemo");
		ctx.close();
	}

	@Test
	public void getNameByParam() {

		String name = "张三";
		System.out.println(springMVCDemo.getNameByParam(name));
	}

	@Test
	public void getStudentByName() {
		String name = "张三";
		System.out.println(springMVCDemo.getStudentByName(name));
	}
}
