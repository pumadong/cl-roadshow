package com.cl.roadshow.share.point12;

/**
 * 堆内存中，Student的实例占用多少字节
 * 计算方式：对象头字节（12字节）+每个类型对象的大小+每个引用的大小（4字节）
 * 所以，本对象的实例（刚刚new时）占用的大小=12 + 4(id) + 1(flag) + 8(l1) + 1(c1) + 4(f1) + 8(d1) = 38字节，必须是8的整数
 * 所以，内存占用40字节，即Shadow Memory（浅内存）是40字节
 * 
 * 参考：http://www.open-open.com/lib/view/open1423111722764.html
 * 
 * @author dongyongjin
 *
 */
public class Student {

	private String name;
	private Integer id; //4
	private boolean flag; //1
	public Long l1; // 8
	public char c1; // 1
	public float f1; // 4
	public double d1; // 8
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
