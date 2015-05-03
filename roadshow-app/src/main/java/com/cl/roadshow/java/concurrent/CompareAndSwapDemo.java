package com.cl.roadshow.java.concurrent;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import sun.misc.Unsafe;

/**
 * 演示Java并发的几种实现方法，演示CAS算法的原理
 *
 */
public class CompareAndSwapDemo {

	private static int factorialUnSafe;
	private static int factorialSafe;
	private static int factorialCas;
	private static long factorialCasOffset;
	private static AtomicInteger factorialAtomic = new AtomicInteger(0);

	private static int SIZE = 200;
	private static CountDownLatch latch = new CountDownLatch(SIZE * 4);

	private static Object lock = new Object();
	private static Unsafe unsafe;

	// 获取CasTest的静态Field的内存偏移量
	static {
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsafe = (Unsafe) field.get(null);
			factorialCasOffset = unsafe.staticFieldOffset(CompareAndSwapDemo.class.getDeclaredField("factorialCas"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 存储每种计算方法的最终结果
	 */
	private static Map<String, Integer> factorialMax = new HashMap<String, Integer>();

	public static void main(String[] args) throws Exception {

		for (int i = 0; i < SIZE; i++) {
			new Thread(new IncreamUnSafe()).start();
			new Thread(new IncreamSafe()).start();
			new Thread(new IncreamCas()).start();
			new Thread(new IncreamAtomic()).start();
		}

		latch.await();
		System.out.println("IncreamUnsafe Result：" + factorialMax.get("unsafe"));
		System.out.println("IncreamSafe Result：" + factorialMax.get("safe"));
		System.out.println("IncreamCas Result：" + factorialMax.get("cas"));
		System.out.println("IncreamAtomic Result：" + factorialMax.get("atomic"));
	}

	/**
	 * 非线程安全的阶乘
	 *
	 */
	static class IncreamUnSafe implements Runnable {

		public void run() {
			for (int j = 0; j < 1000; j++) {
				factorialUnSafe++;
			}
			recordMax("unsafe", factorialUnSafe);
			latch.countDown();
		}
	}

	/**
	 * 线程安全的阶乘
	 *
	 */
	static class IncreamSafe implements Runnable {

		public void run() {
			synchronized (lock) {
				for (int j = 0; j < 1000; j++) {
					factorialSafe++;
				}
			}
			recordMax("safe", factorialSafe);
			latch.countDown();
		}
	}

	/**
	 * 用CAS算法实现的线程安全的阶乘，Java的原子类就是这么搞的，死循环，就是疯狂的压榨CPU
	 *
	 */
	static class IncreamCas implements Runnable {

		public void run() {
			for (int j = 0; j < 1000; j++) {
				for (;;) {
					int current = factorialCas;
					int next = factorialCas + 1;
					if (unsafe.compareAndSwapInt(CompareAndSwapDemo.class, factorialCasOffset, current, next)) {
						break;
					}
				}
			}
			recordMax("cas", factorialCas);
			latch.countDown();
		}
	}

	static class IncreamAtomic implements Runnable {
		public void run() {
			for (int j = 0; j < 1000; j++) {
				factorialAtomic.incrementAndGet();
			}
			recordMax("atomic", factorialAtomic.get());
			latch.countDown();
		}
	}

	/**
	 * 记录每个线程的最终结果
	 * 
	 * @param key
	 * @param target
	 */
	static synchronized void recordMax(String key, int target) {
		Integer value = factorialMax.get(key);
		if ((value == null) || (value < target)) {
			factorialMax.put(key, target);
		}
	}
}
