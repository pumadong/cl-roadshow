package com.cl.roadshow.spring.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/*
 * SpringIOC，自动扫描注解、XML配置bean并装入容器演示
 * 
 *
 */
@SuppressWarnings("resource")
public class IocDemo {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "com/cl/roadshow/spring/ioc/beans-autoscan.xml" });

		/**
		 * 测试IOC：自动扫描注解标记
		 */
		IocUtil iocUtil = ctx.getBean("iocUtil", IocUtil.class);
		if (iocUtil == null) {
			System.out.println("iocUtil is null");
		} else {
			System.out.println(iocUtil.getString());
		}

		/**
		 * 以下演示一种更原始的载入和注册Bean过程，对我们了解IoC容器的工作原理很有帮助
		 * 
		 * 揭示了在IoC容器实现中的关键类
		 * 
		 * 比如：Resource、DefaultListableBeanFactory、BeanDefinitionReader，之间的相互联系，相互协作
		 */
		ClassPathResource res = new ClassPathResource("com/cl/roadshow/spring/ioc/beans.xml");
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(res);
		Car car = factory.getBean("car", Car.class);
		System.out.println(car.toString());

	}
}

@Service
class IocUtil {

	@Autowired
	private ConfigUtil configUtil;

	public String getString() {

		return "IocUtil invoke configUtil.getString()：" + configUtil.getString();
	}

}

@Service
class ConfigUtil {
	public String getString() {
		return "ConfigUtil.getString()";
	}
}

/**
 * 这个类没有@Component注解，在xml中配置
 *
 */
class Car {
	@Override
	public String toString() {
		return "car is ok";
	}
}
