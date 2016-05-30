package com.cl.roadshow.spring.aop.factory;

import org.springframework.aop.framework.ProxyFactory;

/**
 * Spring代理工厂：http://book.51cto.com/art/200908/147288.htm
 * 
 */
public class ProxyFactoryDemo {
	public static void main(String[] args) {
		TestInterceptor ti = new TestInterceptor("com.cl.roadshow.spring.aop.factory.TestImpl");
		ProxyFactory pf = new ProxyFactory(ITest.class,ti);
		ITest test = (ITest)pf.getProxy();
		test.sayOk();
	}

}
