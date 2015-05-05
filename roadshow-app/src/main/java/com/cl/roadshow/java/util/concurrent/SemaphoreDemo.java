package com.cl.roadshow.java.util.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Java SE5新增的用来解决并发问题的类：计数信号量
 * 
 * 正常的锁在任何时刻都只允许一个任务访问一项资源，而Semaphore允许n个任务同时访问这个资源
 * 
 */
public class SemaphoreDemo {
	final static int SIZE = 25;

	public static void main(String[] args) throws Exception {
		final Pool<Fat> pool = new Pool<Fat>(Fat.class, SIZE);
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < SIZE; i++) {
			exec.execute(new CheckoutTask<Fat>(pool));
		}
		System.out.println("All CheckoutTasks created");
		List<Fat> list = new ArrayList<Fat>();
		for (int i = 0; i < SIZE; i++) {
			Fat f = pool.checkOut();
			System.out.println(i + ": main() thread checked out ");
			f.operation();
			list.add(f);
		}
		Future<?> blocked = exec.submit(new Runnable() {
			public void run() {
				try {
					// Semaphore prevents additional checkout,so call is blocked
					pool.checkOut();
				} catch (InterruptedException e) {
					System.out.println("checkOut() Interrupted");
				}
			}
		});
		TimeUnit.SECONDS.sleep(2);
		blocked.cancel(true); // Break out of blocked call
		System.out.println("Checking in objects in " + list);
		for (Fat f : list) {
			pool.checkIn(f);
		}
		for (Fat f : list) {
			pool.checkIn(f); // Second checkIn ignored
		}
		exec.shutdown();
	}
}

class Pool<T> {
	private int size;
	private List<T> items = new ArrayList<T>();
	private volatile boolean[] checkedOut;
	private Semaphore available;

	public Pool(Class<T> classObject, int size) {
		this.size = size;
		checkedOut = new boolean[size];
		available = new Semaphore(size, true);
		// Load pool with objects that can be checked out
		for (int i = 0; i < size; ++i) {
			try {
				// Assumes a default constructor
				items.add(classObject.newInstance());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public T checkOut() throws InterruptedException {
		available.acquire();
		return getItem();
	}

	public void checkIn(T x) {
		if (releaseItem(x)) {
			available.release();
		}
	}

	private synchronized T getItem() {
		for (int i = 0; i < size; ++i) {
			if (!checkedOut[i]) {
				checkedOut[i] = true;
				return items.get(i);
			}
		}
		return null; // Semaphore prevents reaching here
	}

	private synchronized boolean releaseItem(T item) {
		int index = items.indexOf(item);
		if (index == -1) {
			return false; // Not in the list
		}
		if (checkedOut[index]) {
			checkedOut[index] = false;
			return true;
		}
		return false; // Wasn't checked out
	}

	public static void main(String[] args) {
		int counter = 0;
		int id = counter++;
		System.out.println(id);
	}
}

@SuppressWarnings("unused")
class Fat {
	private volatile double d; // Prevent optimization
	private static int counter = 0;
	private final int id = counter++;

	public Fat() {
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
		return "Fat id: " + id;
	}
}

// A task to check a resource out of a pool
class CheckoutTask<T> implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private Pool<T> pool;

	public CheckoutTask(Pool<T> pool) {
		this.pool = pool;
	}

	public void run() {
		try {
			T item = pool.checkOut();
			System.out.println(this + " checked out " + item);
			TimeUnit.SECONDS.sleep(1);
			System.out.println(this + " checking in " + item);
			pool.checkIn(item);
		} catch (InterruptedException e) {
			// Acceptable way to terminate
		}
	}

	@Override
	public String toString() {
		return "CheckoutTask " + id + " ";
	}
}
