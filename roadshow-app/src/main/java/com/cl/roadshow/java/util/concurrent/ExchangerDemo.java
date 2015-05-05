package com.cl.roadshow.java.util.concurrent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Java SE5新增的用来解决并发问题的类：交互栅栏
 * 
 * 典型应用场景：一个任务在创建对象，这些对象的生产代价很高昂，而另一个任务消费这些对象。
 * 
 * 通过这种方式，可以有更多的对象在被创建的同时被消费
 * 
 */
public class ExchangerDemo {
	static int size = 10;
	static int delay = 5; // Seconds

	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		Exchanger<List<FatEx>> xc = new Exchanger<List<FatEx>>();
		List<FatEx> producerList = new CopyOnWriteArrayList<FatEx>(), consumerList = new CopyOnWriteArrayList<FatEx>();
		exec.execute(new ExchangerProducer<FatEx>(FatEx.class, xc, producerList));
		exec.execute(new ExchangerConsumer<FatEx>(xc, consumerList));
		TimeUnit.SECONDS.sleep(delay);
		exec.shutdownNow();
	}
}

class ExchangerProducer<T> implements Runnable {
	private Class<T> classObject;
	private Exchanger<List<T>> exchanger;
	private List<T> holder;

	ExchangerProducer(Class<T> classObject, Exchanger<List<T>> exchg, List<T> holder) {
		this.classObject = classObject;
		exchanger = exchg;
		this.holder = holder;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				for (int i = 0; i < ExchangerDemo.size; i++) {
					try {
						holder.add(classObject.newInstance());
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				holder = exchanger.exchange(holder);
			}
		} catch (InterruptedException e) {
			// Ok to terminate this way
		}
	}
}

class ExchangerConsumer<T> implements Runnable {
	private Exchanger<List<T>> exchanger;
	private List<T> holder;
	private volatile T value;

	ExchangerConsumer(Exchanger<List<T>> ex, List<T> holder) {
		exchanger = ex;
		this.holder = holder;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				holder = exchanger.exchange(holder);
				for (T x : holder) {
					value = x; // Fetch out value
					holder.remove(x); // OK for CopyOnWriteArrayList
				}
				System.out.println("Final value: " + value);
			}
		} catch (InterruptedException e) {
			// OK to terminate this way
		}
	}
}

@SuppressWarnings("unused")
class FatEx {
	private volatile double d; // Prevent optimization
	private static int counter = 0;
	private final int id = counter++;

	public FatEx() {
		// Expensive,Interruptible operation
		for (int i = 1; i < 10000; i++) {
			d += (Math.PI + Math.E) / i;
		}
	}

	public void operation() {
		System.out.println(this);
	}

	@Override
	public String toString() {
		return "FatEx id: " + id;
	}
}
