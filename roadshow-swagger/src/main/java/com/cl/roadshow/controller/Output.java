package com.cl.roadshow.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



@ApiModel(value = "输出对象")
public class Output {
	
	@ApiModelProperty(value = "ID")
	private int id;
	@ApiModelProperty(value = "名字")
	private String name;
	@ApiModelProperty(value = "内部类")
	private Inner inner;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Inner getInner() {
		return inner;
	}
	public void setInner(Inner inner) {
		this.inner = inner;
	}
}
