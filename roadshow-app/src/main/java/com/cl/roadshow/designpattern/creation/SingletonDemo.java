package com.cl.roadshow.designpattern.creation;

/**
 * 单例模式：饿汉式、懒汉式、双重检查
 * 
 * 双重检查出现的问题设计JVM级别的技术，要注意
 *
 */
public class SingletonDemo {
	public static void main(String[] args) {
		System.out.println("饿汉式：");
		for (int i = 0; i < 5; i++) {
			System.out.println("EagerSingleton" + "：" + EagerSingleton.getInstance().toString());
		}
		System.out.println("懒汉式：");
		for (int i = 0; i < 5; i++) {
			System.out.println("LazySingleton" + "：" + LazySingleton.getInstance().toString());
		}
		System.out.println("双重检查：");
		for (int i = 0; i < 5; i++) {
			System.out.println("DoubleCheckedSingleton" + "：" + DoubleCheckedSingleton.getInstance().toString());
		}
	}
}

class EagerSingleton {
	private static final EagerSingleton instance = new EagerSingleton();

	private EagerSingleton() {
	}

	// 静态工厂方法
	public static EagerSingleton getInstance() {
		return instance;
	}
}

class LazySingleton {
	private static LazySingleton instance = null;

	private LazySingleton() {
	}

	// 静态工厂方法法
	public static synchronized LazySingleton getInstance() {
		if (instance == null) {
			instance = new LazySingleton();
		}
		return instance;
	}
}

class DoubleCheckedSingleton {
	// 如果没有volatile，则由于内存模型运行“无序列入”，可能instance读到不完整的实例，导致程序崩溃
	private static volatile DoubleCheckedSingleton instance = null;

	private DoubleCheckedSingleton() {
	}

	// 静态工厂方法法
	public static DoubleCheckedSingleton getInstance() {
		if (instance == null) {
			// 此处同一时刻可能多个线程到达
			synchronized (DoubleCheckedSingleton.class) {
				// 此处同一时刻只能一个线程到达
				if (instance == null) {
					instance = new DoubleCheckedSingleton();
				}
			}
		}
		return instance;
	}
}