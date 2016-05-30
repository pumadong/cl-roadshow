package com.cl.roadshow.spring.aop.factory;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class TestInterceptor implements MethodInterceptor {
	
	private String serviceImpl;
	
	public TestInterceptor(String serviceImpl) {
		this.serviceImpl = serviceImpl;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object[] args = invocation.getArguments();
        Object target = Class.forName(serviceImpl).newInstance();
        System.out.println("TestINterceptor before ...");
        Object result = method.invoke(target, args);
        System.out.println("TestINterceptor after ...");
        return result;
	}

	public String getServiceImpl() {
		return serviceImpl;
	}

	public void setServiceImpl(String serviceImpl) {
		this.serviceImpl = serviceImpl;
	}

}
