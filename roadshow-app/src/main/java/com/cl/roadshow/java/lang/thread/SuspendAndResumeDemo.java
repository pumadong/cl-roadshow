package com.cl.roadshow.java.lang.thread;

/**
 * suspend/resume/stop都已经被废弃，它们是Thread的，会产生不可预料的问题
 * 
 * 对于线程间协作，推荐的是使用wait/notify，当然这是底层的，搞不好也会产生问题
 * 
 * 所以，尽量实现JDK提供的高层实现类解决线程互斥协作问题
 *
 */
@SuppressWarnings("deprecation")
public class SuspendAndResumeDemo {
	private final static Object object = new Object();

	static class ThreadA extends Thread {

		@Override
		public void run() {
			synchronized (object) {
				System.out.println("start...");
				Thread.currentThread().suspend();
				System.out.println("thread end");
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ThreadA t1 = new ThreadA();
		ThreadA t2 = new ThreadA();
		t1.start();
		t2.start();
		Thread.sleep(100);
		System.out.println(t1.getState());
		System.out.println(t2.getState());
		t1.resume();
		t2.resume(); // 这段代码执行的时候,run()里面的代码有锁，进不去，相当于没执行
	}
}
