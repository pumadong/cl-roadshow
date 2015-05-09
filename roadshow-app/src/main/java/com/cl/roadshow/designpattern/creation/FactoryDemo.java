package com.cl.roadshow.designpattern.creation;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * 工厂模式：工厂是创建产品，多例是维护自己
 * 
 * 演示简单工厂模式、工厂方法模式；对于稍嫌负责的抽象工厂，不演示了
 *
 */
public class FactoryDemo {
	public static void main(String[] args) {
		// 静态工厂、简单工厂
		Product p = Creator.factory();
		System.out.println("静态工厂：" + p.toString());

		// 工厂方法
		ICreator c1, c2;
		Product p1, p2;
		c1 = new ConcreteCreator1();
		p1 = c1.factory();
		c2 = new ConcreteCreator2();
		p2 = c2.factory();
		System.out.println("工厂方法p1：" + p1.toString());
		System.out.println("工厂方法p2：" + p2.toString());

		System.out.println("\n简单工厂在JDK中的应用");
		DateTest.factory();
	}
}

// 1.简单工厂

interface Product {
}

class ConcreteProduct implements Product {
	public ConcreteProduct() {
	}
}

class Creator {
	// 静态工厂方法
	public static Product factory() {
		return new ConcreteProduct();
	}
}

// 2.工厂方法
class ConcreteProduct1 implements Product {
	public ConcreteProduct1() {
	}
}

class ConcreteProduct2 implements Product {
	public ConcreteProduct2() {
	}
}

interface ICreator {
	// 工厂方法
	public Product factory();
}

class ConcreteCreator1 implements ICreator {
	@Override
	public Product factory() {
		return new ConcreteProduct1();
	}
}

class ConcreteCreator2 implements ICreator {
	@Override
	public Product factory() {
		return new ConcreteProduct2();
	}
}

// 简单工厂在JDK中的应用
class DateTest {
	public static void factory() {
		Locale local = Locale.FRENCH;
		Date date = new Date();
		String now = DateFormat.getTimeInstance(DateFormat.DEFAULT, local).format(date);
		System.out.println(now);
		try {
			date = DateFormat.getDateInstance(DateFormat.DEFAULT, local).parse("16 nov. 01");
			System.out.println(date);
		} catch (ParseException e) {
			System.out.println("Parsing exception:" + e);
		}
	}
}

// 工厂方法在JDK中的应用