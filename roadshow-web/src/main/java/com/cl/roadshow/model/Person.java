package com.cl.roadshow.model;

import java.util.Date;

public class Person {

	private String name;
	private Date createTime;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
