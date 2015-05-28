package com.cl.roadshow.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class SpringMVCDemoTest {

	@Autowired
	private SpringMVCDemo springMVCDemo;

	@Test
	public void getNameByParam() {

		String name = "张三";
		System.out.println(springMVCDemo.getNameByParam(name));
	}

	@Test
	public void getPersonByName() {
		String name = "张三";
		System.out.println(springMVCDemo.getPersonByName(name));
	}
}
