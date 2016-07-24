package com.cl.roadshow.spring.perm;

import java.lang.reflect.Constructor;

import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;
import org.springframework.objenesis.instantiator.ObjectInstantiator;

/**
 * 仅包含一个自定义构造函数,未包含不带参数的构造函数
 * 这个类是想说明：Spring实例化类时，默认是借助Objenesis，不适用类的构造函数
 */
public class Person {

	public static void main(String[] args) {
		Person.testJdkWithParams();
		Person.testJdkWithoutParams();
		Person.testObjenesis();
	}

	public static void testJdkWithParams() {
		try {
			Class<?> clazz = Class
					.forName("com.cl.roadshow.spring.perm.Person");
			Constructor<?> constructor = clazz.getConstructor(String.class);
			Person person = (Person) constructor
					.newInstance("testJdkWithParams");
			System.out.println(person);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 抛出以下异常: java.lang.NoSuchMethodException 原因: 利用反射时,person类中不包含无参数的构造函数
	 */
	public static void testJdkWithoutParams() {
		try {
			Class<?> clazz = Class
					.forName("com.cl.roadshow.spring.perm.Person");
			Constructor<?> constructor = clazz.getConstructor();
			Person person = (Person) constructor.newInstance();
			person.setName("testJdkWithoutParams");
			System.out.println(person);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  spring实现方式（借助第三方框架Objenesis）
	 */
	public static void testObjenesis(){
	    Objenesis objenesis = new ObjenesisStd();
	    ObjectInstantiator<Person> parentInstantiator = objenesis.getInstantiatorOf(Person.class);
	    Person person = parentInstantiator.newInstance();
	    person.setName("testObjenesis");
	    System.out.println(person);
	}

	private String name;

	public Person(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Person{" + "name='" + name + '\'' + '}';
	}
}