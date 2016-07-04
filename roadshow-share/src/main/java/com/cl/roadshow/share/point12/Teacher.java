package com.cl.roadshow.share.point12;

/**
 * 堆内存中，Teacher的实例占用多少字节
 * 计算方式：对象头字节（12字节）+每个类型对象的大小+每个引用的大小（4字节）
 * 所以，本对象的实例（刚刚new时）占用的大小=12 + 1(age) + 4(reference1) + 4(student) + 4(reference2) = 25字节，必须是8的整数
 * 所以，内存占用32字节，即Shadow Memory（浅内存）是32字节
 * 如果计算Retained Memory（保留内存），也就是Teacher是否后，可以释放的内存，则是112字节
 * 
 * 参考：http://www.open-open.com/lib/view/open1423111722764.html
 * 
 * @author dongyongjin
 *
 */
public class Teacher {
	private byte age;
	private Object reference1;
	private Student student = new Student();
	private Object reference2;

	public byte getAge() {
		return age;
	}
	public void setAge(byte age) {
		this.age = age;
	}
	public Object getReference1() {
		return reference1;
	}
	public void setReference1(Object reference1) {
		this.reference1 = reference1;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Object getReference2() {
		return reference2;
	}
	public void setReference2(Object reference2) {
		this.reference2 = reference2;
	}
	public static void main(String[] args) throws Exception {
		
		Teacher teacher = new Teacher();
		Student student = new Student();

		teacher.setReference1(new Student());
		teacher.setReference2(student);
		
		System.out.print(teacher.toString() + student);
		
		Thread.sleep(30 * 1000);
		
	}
}
