package com.cl.roadshow.java.lang.thread;

/**
 * 线程协作时易发的一种死锁演示：错失信号
 * 
 */
public class DeadLockMissDemo {
	public static void main(String[] args) {
		Object lock = new Object();

		Runnable wait1 = new DeadLockMiss(lock, true);
		Runnable wait2 = new DeadLockMiss(lock, true);
		Runnable notify = new DeadLockMiss(lock, false);

		// wait1和wait2将有一个因为错失信号而死锁

		new Thread(wait1).start();
		new Thread(wait2).start();
		new Thread(notify).start();
	}
}

class DeadLockMiss implements Runnable {
	private Object lock;
	private Boolean isWait;

	public DeadLockMiss(Object lock, Boolean isWait) {
		this.lock = lock;
		this.isWait = isWait;
	}

	public void run() {
		synchronized (lock) {
			if (isWait) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				lock.notify();
			}
			System.out.println(Thread.currentThread().getName() + ":isWait:" + isWait + " is running!");
		}
	}
}