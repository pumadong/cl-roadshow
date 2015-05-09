package com.cl.roadshow.designpattern.behavior;

/**
 * 一个对象的状态在对象创建之后就不再变化，这就是不变模式
 * 
 * 不变模式缺少改变自身状态的行为，因此它是关于行为的，所以把它划归为行为模式
 * 
 * java.lang.String是不变模式的实现
 *
 */
public class ImmutableDemo {
	public static void main(String[] args) {
		ImmutableDemo immutableDemo = new ImmutableDemo();

		Immutable immutable = immutableDemo.new Immutable("张三", 25);
		System.out.println(immutable.toString());
	}

	// 这是一个不变类，对于不变类，JVM编译时会进行更好的优化
	final class Immutable {
		private final String name;
		private final Integer age;

		public Immutable(String name, Integer age) {
			this.name = name;
			this.age = age;
		}

		@Override
		public String toString() {
			return name + ":" + age;
		}
	}
}
