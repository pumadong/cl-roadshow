package com.cl.roadshow.algorithm;

import java.util.Random;

/**
 * 不重复随机序列生成
 * 
 * http://www.cnblogs.com/eaglet/archive/2011/01/17/1937083.html
 * 
 * 这个帖子分析了数组和链表的性能差异
 *
 */
public class RandomPermuteDemo<T> {
	public static void main(String[] args) {

		RandomPermuteDemo<Person> demo = new RandomPermuteDemo<Person>();

		Person[] persons = new Person[] { new Person(1, "张三"), new Person(2, "李四"), new Person(3, "王五"),
				new Person(4, "赵六") };
		demo.createSequence(persons);

		// 关于泛型，不能在静态方法中使用，否则会报异常：
		// Cannot make a static reference to the non-static type T
		// 参考帖子：http://my.oschina.net/sulliy/blog/134442
	}

	public void createSequence(T[] ts) {
		int count = ts.length;
		for (int i = 0; i < count; i++) {
			int index0 = new Random().nextInt(count - i);
			int index1 = count - i - 1;
			T tmp = ts[index0];
			ts[index0] = ts[index1];
			ts[index1] = tmp;
			System.out.println(tmp);
		}
	}
}

class Person {
	private Integer id;
	private String name;

	public Person(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}
}
