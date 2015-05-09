package com.cl.roadshow.designpattern.structure;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

/**
 * 适配器模式
 * 
 * 广义上来说，日常应用开发，入目尽是适配器模式
 *
 */

@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class AdapterDemo {

	public static void main(String[] args) {
		AdapterDemo adapterDemo = new AdapterDemo();

		Adaptee adaptee = adapterDemo.new Adaptee();
		Target target = adapterDemo.new Adapter(adaptee);
		target.sampleOp1();
		target.sampleOp2();

		System.out.println("\n演示从Enumeration到Iterator");
		Vector<String> vectors = new Vector<String>();
		vectors.add("张三");
		vectors.add("李四");
		Enumeration<String> enumeration = vectors.elements();
		Iterator<String> iterator = adapterDemo.new Enumeration2Iterator(enumeration);
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}

	}

	interface Target {
		// 源类有这个方法
		void sampleOp1();

		void sampleOp2();
	}

	// 适配器模式：把源Adaptee转换成目标Target
	class Adaptee {
		public void sampleOp1() {
			System.out.println("Invoke Adaptee.sampleOp1()");
		}
	}

	class Adapter implements Target {
		private Adaptee adaptee;

		public Adapter(Adaptee adaptee) {
			super();
			this.adaptee = adaptee;
		}

		// 源类有这个方法，直接委派
		@Override
		public void sampleOp1() {
			adaptee.sampleOp1();
		}

		// 由于源类没有这个方法，因此适配器类补充上这个方法
		@Override
		public void sampleOp2() {
			System.out.println("Invoke Adapter.sampleOp2()");
		}
	}

	// 适配器模式：演示从Enumeration到Iterator
	public class Enumeration2Iterator<E> implements Iterator<E> {

		Enumeration enu;

		public Enumeration2Iterator(Enumeration enu) {
			this.enu = enu;
		}

		@Override
		public boolean hasNext() {
			return enu.hasMoreElements();
		}

		@Override
		public E next() {
			return (E) enu.nextElement();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
