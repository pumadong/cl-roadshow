package com.cl.roadshow.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * fastjson 是一个性能很好的 Java 语言实现的 JSON 解析器和生成器，来自阿里巴巴的工程师开发
 * 
 * 
 * 主要特点：比其它任何基于Java的解析器和生成器更快，包括jackson；强大；零依赖
 *
 */
public class FastjsonDemo {
	public static void main(String[] args) {

		// 将JSON和JavaBean对象互相转换
		Person person = new Person(1, "张三", null);
		String jsonString = JSON.toJSONString(person);
		System.out.println(jsonString);
		person = JSON.parseObject(jsonString, Person.class);
		System.out.println(person.getName());

		System.out.println("--------------------------------------");

		// 将JSON字符串转化成List<JavaBean>对象
		Person person1 = new Person(1, "fastjson1", 11);
		Person person2 = new Person(2, "fastjson2", 22);
		List<Person> persons = new ArrayList<Person>();
		persons.add(person1);
		persons.add(person2);
		jsonString = JSON.toJSONString(persons);
		System.out.println("json字符串:" + jsonString);
		persons = JSON.parseArray(jsonString, Person.class);
		System.out.println(persons.toString());

		System.out.println("--------------------------------------");

		// 将JSON字符串转化成List<String>对象
		List<String> list1 = new ArrayList<String>();
		list1.add("fastjson1");
		list1.add("fastjson2");
		list1.add("fastjson3");
		jsonString = JSON.toJSONString(list1);
		System.out.println(jsonString);
		List<String> list2 = JSON.parseObject(jsonString, new TypeReference<List<String>>() {
		});
		System.out.println("list2：" + list2.toString());

		System.out.println("--------------------------------------");

		// JSON<Map<String,Object>>对象
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("key1", 1);
		map2.put("key2", 2);
		List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();
		list3.add(map);
		list3.add(map2);
		jsonString = JSON.toJSONString(list3);
		System.out.println("json字符串:" + jsonString);
		List<Map<String, Object>> list4 = JSON.parseObject(jsonString, new TypeReference<List<Map<String, Object>>>() {
		});
		System.out.println("list4：" + list4.toString());
	}
}

class Person {

	private Integer id;
	private String name;
	private Integer age;

	public Person() {
	}

	public Person(Integer id, String name, Integer age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ID:").append(id);
		sb.append("-Name:").append(name);
		sb.append("-Age:").append(age);
		return sb.toString();
	}
}
