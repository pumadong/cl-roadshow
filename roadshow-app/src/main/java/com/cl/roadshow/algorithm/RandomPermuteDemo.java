package com.cl.roadshow.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 不重复随机序列生成
 * 
 * http://www.cnblogs.com/eaglet/archive/2011/01/17/1937083.html
 * 
 * 这个帖子分析了数组和链表的性能差异
 *
 */
public class RandomPermuteDemo {
	public static void main(String[] args) {

		List<Person> personList = new ArrayList<Person>();
		personList.add(new Person(1, "张三"));
		personList.add(new Person(2, "李四"));
		personList.add(new Person(3, "王五"));
		personList.add(new Person(4, "赵六"));

		createSequence(personList);

	}

	public static void createSequence(List<Person> personList) {
		Person[] persons = new Person[personList.size()];
		personList.toArray(persons);
		int count = persons.length;
		for (int i = 0; i < count; i++) {
			int index0 = new Random().nextInt(count - i);
			int index1 = count - i - 1;
			Person tmp = persons[index0];
			persons[index0] = persons[index1];
			persons[index1] = tmp;
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
