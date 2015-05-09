package com.cl.roadshow.java.lang.thread;

/**
 * 最简单的一种死锁演示：互相等待对方释放资源
 * 
 */
public class DeadLockMutexDemo {
	public static void main(String[] args) {
		Runnable t1 = new DeadLockMutex(true);
		Runnable t2 = new DeadLockMutex(false);
		new Thread(t1).start();
		new Thread(t2).start();
	}
}

class DeadLockMutex implements Runnable {
	private static Object lock1 = new Object();
	private static Object lock2 = new Object();
	private boolean flag;

	public DeadLockMutex(boolean flag) {
		this.flag = flag;
	}

	public void run() {
		if (flag) {
			synchronized (lock1) {
				try {
					Thread.sleep(1000); // 保证晚于另一个线程锁lock2，目的是产生死锁
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (lock2) {
					System.out.println("flag=true:死锁了，还能print的出来吗？");
				}
			}
		} else {
			synchronized (lock2) {
				try {
					Thread.sleep(1000); // 保证晚于另一个线程锁lock1，目的是产生死锁
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (lock1) {
					System.out.println("flag=false:死锁了，还能print的出来吗？");
				}
			}
		}
	}
}