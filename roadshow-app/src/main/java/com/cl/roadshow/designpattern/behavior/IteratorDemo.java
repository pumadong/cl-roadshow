package com.cl.roadshow.designpattern.behavior;

import java.util.Vector;

/**
 * 迭代模式可以顺序的访问一个聚集中的元素而不必暴漏聚集的内部对象
 * 
 * JDK中的聚集对象都有一个内部类实现迭代模式
 *
 */
public class IteratorDemo {

	public static void main(String[] args) {
		System.out.println("正向迭代演示");
		Purchase a = new PurchaseOfCopyA();
		a.add("one");
		a.add("two");
		a.add("three");
		Iterator ia = a.createIterator();
		ia.first();
		while (!ia.isLast()) {
			System.out.println("a： " + ia.current());
			ia.next();
		}

		System.out.println("逆向迭代演示");
		Purchase b = new PurchaseOfCopyB();
		b.add("one");
		b.add("two");
		b.add("three");
		Iterator ib = b.createIterator();
		ib.first();
		while (!ib.isLast()) {
			System.out.println("b： " + ib.current());
			ib.next();
		}
	}
}

/**
 * 抽象聚集类
 *
 */

@SuppressWarnings(value = { "rawtypes", "unchecked" })
abstract class Purchase {

	private Vector elements = new Vector(5);

	// 工厂方法模式，提供迭代对象的实现
	public abstract Iterator createIterator();

	public void add(Object o) {
		elements.add(o);
	}

	public void remove(Object o) {
		elements.remove(o);
	}

	public Object currentItem(int index) {
		return elements.elementAt(index);
	}

	public int count() {
		return elements.size();
	}
}

/**
 * 具体聚集类：A
 *
 */
class PurchaseOfCopyA extends Purchase {

	@Override
	public Iterator createIterator() {
		return new ForwardIterator(this);
	}
}

/**
 * 具体聚集类：B
 *
 */
class PurchaseOfCopyB extends Purchase {

	@Override
	public Iterator createIterator() {
		return new BackIterator(this);
	}
}

/**
 * 抽象迭代角色
 *
 */
interface Iterator {
	void first(); // 移到第一个元素

	void next(); // 移到下一个元素

	boolean isLast(); // 是否最后一个元素

	Object current(); // 当前的元素
}

/**
 * 正向迭代
 *
 */
class ForwardIterator implements Iterator {
	private int index;
	private Purchase obj;

	public ForwardIterator(Purchase obj) {
		this.obj = obj;
	}

	@Override
	public void first() {
		index = 0;
	}

	@Override
	public void next() {
		if (!isLast()) {
			index++;
		}
	}

	@Override
	public boolean isLast() {
		if (index > (obj.count() - 1)) {
			return true;
		}
		return false;
	}

	@Override
	public Object current() {
		return obj.currentItem(index);
	}
}

/**
 * 逆向迭代
 *
 */
class BackIterator implements Iterator {
	private int index;
	private Purchase obj;

	public BackIterator(Purchase obj) {
		this.obj = obj;
	}

	@Override
	public void first() {
		index = obj.count() - 1;
	}

	@Override
	public void next() {
		if (!isLast()) {
			index--;
		}
	}

	@Override
	public boolean isLast() {
		if (index < 0) {
			return true;
		}
		return false;
	}

	@Override
	public Object current() {
		return obj.currentItem(index);
	}
}