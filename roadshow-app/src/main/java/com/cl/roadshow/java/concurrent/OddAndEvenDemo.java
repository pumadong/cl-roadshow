package com.cl.roadshow.java.concurrent;

/**
 * Java并发仿真程序：一个线程打印奇数，另一个线程打印偶数
 * 
 */
public class OddAndEvenDemo {
	public static void main(String[] args) {

		OddAndEven o = new OddAndEven();

		(new Thread(new ThreadA(o))).start();
		(new Thread(new ThreadB(o))).start();
	}
}

class ThreadA implements Runnable {

	OddAndEven o;

	public ThreadA(OddAndEven o) {
		this.o = o;
	}

	public void run() {
		try {
			o.printOdd();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class ThreadB implements Runnable {

	OddAndEven o;

	public ThreadB(OddAndEven o) {
		this.o = o;
	}

	public void run() {
		try {
			o.printEven();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class OddAndEven {
	int[] num = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	int i = 0;

	public synchronized void printOdd() throws InterruptedException {
		while (i < 10) {
			if ((num[i] % 2) == 1) {
				System.out.println(num[i] + "\t" + Thread.currentThread().getName());
				i++;
				notify();
			} else {
				wait();
			}
		}
	}

	public synchronized void printEven() throws InterruptedException {
		while (i < 10) {
			if ((num[i] % 2) == 0) {
				System.out.println(num[i] + "\t" + Thread.currentThread().getName());
				i++;
				notify();
			} else {
				wait();
			}
		}
	}
}