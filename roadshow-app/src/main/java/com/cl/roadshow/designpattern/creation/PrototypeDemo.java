package com.cl.roadshow.designpattern.creation;

import com.alibaba.fastjson.JSON;

/**
 * 通过复制原型对象的办法创造出更多同类型的对象
 * 
 * 了解Java的深层次克隆，浅层次克隆即可
 *
 */
public class PrototypeDemo {
	public static void main(String[] args) throws CloneNotSupportedException {
		PrototypeDemo prototypeDemo = new PrototypeDemo();

		Deep deep = prototypeDemo.new Deep();
		deep.setHappyLevel(100);
		deep.setPerson(prototypeDemo.new Person("张三", 25));
		Deep deepClone = (Deep) deep.clone();
		deepClone.setHappyLevel(200);
		deepClone.getPerson().setAge(30);
		deepClone.getPerson().setName("李四");
		System.out.println("我们可以看到deep和deepClone是完全不同的：");
		System.out.println("deep:\n" + JSON.toJSONString(deep));
		System.out.println("deepClone:\n" + JSON.toJSONString(deepClone));

		Shallow shallow = prototypeDemo.new Shallow();
		shallow.setHappyLevel(100);
		shallow.setPerson(prototypeDemo.new Person("张三", 25));
		Shallow shallowClone = (Shallow) shallow.clone();
		shallowClone.setHappyLevel(200);
		shallowClone.getPerson().setAge(30);
		shallowClone.getPerson().setName("李四");
		System.out.println("我们可以看到对shallowClone的赋值同时影响了shallow：");
		System.out.println("shallow:\n" + JSON.toJSONString(shallow));
		System.out.println("shallowClone:\n" + JSON.toJSONString(shallowClone));
	}

	class Deep implements Cloneable {
		private Integer happyLevel;
		private Person person;

		public Integer getHappyLevel() {
			return happyLevel;
		}

		public void setHappyLevel(Integer happyLevel) {
			this.happyLevel = happyLevel;
		}

		public Person getPerson() {
			return person;
		}

		public void setPerson(Person person) {
			this.person = person;
		}

		@Override
		protected Object clone() throws CloneNotSupportedException {
			Deep deepClone = (Deep) super.clone();
			deepClone.setHappyLevel(this.happyLevel);
			deepClone.setPerson((Person) this.person.clone());
			return deepClone;
		}
	}

	class Shallow implements Cloneable {
		private Integer happyLevel;
		private Person person;

		public Integer getHappyLevel() {
			return happyLevel;
		}

		public void setHappyLevel(Integer happyLevel) {
			this.happyLevel = happyLevel;
		}

		public Person getPerson() {
			return person;
		}

		public void setPerson(Person person) {
			this.person = person;
		}

		@Override
		protected Object clone() throws CloneNotSupportedException {
			Shallow shallowClone = (Shallow) super.clone();
			return shallowClone;
		}
	}

	class Person implements Cloneable {
		private String name;
		private Integer age;

		public Person(String name, Integer age) {
			this.name = name;
			this.age = age;
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
		protected Object clone() throws CloneNotSupportedException {
			Person person = (Person) super.clone();
			person.setName(this.name);
			person.setAge(this.age);
			return person;
		}
	}
}
