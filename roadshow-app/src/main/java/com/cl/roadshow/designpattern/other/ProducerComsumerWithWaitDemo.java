package com.cl.roadshow.designpattern.other;

/**
 * 使用wait实现的生产者消费者模式
 *
 * 使用中断来让程序退出，是很不好的方式，应该是设置退出Boolean标记
 *
 * 这里的演示，可以看到InterruptedException被吃掉后，interrupted()没有被赋值成true，依然是false
 */
public class ProducerComsumerWithWaitDemo {
	private Object product;
	private Object lock = new Object();

	public static void main(String[] args) {

		ProducerComsumerWithWaitDemo producerComsumerWithWaitDemo = new ProducerComsumerWithWaitDemo();

		Producer producer = producerComsumerWithWaitDemo.new Producer("Producer");
		Consumer consumer = producerComsumerWithWaitDemo.new Consumer("Consumer");

		producer.start();
		consumer.start();

		try {
			Thread.sleep(1000 * 1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		producer.interrupt();
		consumer.interrupt();
	}

	class Consumer extends Thread {

		public Consumer(String name) {
			super(name);
		}

		@Override
		public void run() {
			consume();
		}

		private void consume() {
			while (true && !interrupted()) {
				System.out.println(Thread.currentThread().getName() + "-" + interrupted());
				synchronized (lock) {
					// 这里要用while，不要用if，防止线程被异常唤醒而向下执行
					while (product == null) {
						System.out.println(Thread.currentThread().getName() + "-" + "产品尚未生产，等待数据");
						try {
							lock.wait();
						} catch (InterruptedException e) {
							System.err.println(Thread.currentThread().getName() + "-" + "被中断");
							// return;
						}
					}
					// 消费产品
					System.out.println(Thread.currentThread().getName() + "-" + "消费产品：" + product.toString() + "");
					product = null;
					lock.notify();
				}
			}
		}
	}

	class Producer extends Thread {

		public Producer(String name) {
			super(name);
		}

		@Override
		public void run() {
			produce();
		}

		private void produce() {
			while (true && !interrupted()) {
				System.out.println(Thread.currentThread().getName() + "-" + interrupted());
				synchronized (lock) {
					while (product != null) {
						System.out.println(Thread.currentThread().getName() + "-" + "产品已经生产，等待被消费");
						try {
							lock.wait();
						} catch (InterruptedException e) {
							System.err.println(Thread.currentThread().getName() + "-" + "被中断");
							// return;
						}
					}
					// 生产产品
					product = new Object();
					System.out.println(Thread.currentThread().getName() + "-" + "生产产品：" + product.toString() + "");
					lock.notify();
				}
			}
		}
	}
}
