package com.cl.roadshow.model;

public class Person {

	private String name;

	/**
	 * 必须有缺省构造函数
	 */
	public Person() {
	}

	public Person(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
