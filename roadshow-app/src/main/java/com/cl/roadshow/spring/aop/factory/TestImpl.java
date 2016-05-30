package com.cl.roadshow.spring.aop.factory;

public class TestImpl implements ITest {

	@Override
	public void sayOk() {
		System.out.println("OK,I'm " + this.getClass().getName());
	}

}
