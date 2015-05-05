package com.cl.roadshow.java.util.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Java SE5新增的用来解决并发问题的类：优先级队列
 * 
 * 这是一个很基础的优先级队列，它具有可阻塞的读取操作
 * 
 */

public class PriorityBlockingQueueDemo {
	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<Runnable>();
		exec.execute(new PrioritizedTaskProducer(queue, exec));
		exec.execute(new PrioritizedTaskConsumer(queue));
	}
}

class PrioritizedTask implements Runnable, Comparable<PrioritizedTask> {
	private Random rand = new Random(47);
	private static int counter = 0;
	private final int id = counter++;
	private final int priority;
	protected static List<PrioritizedTask> sequence = new ArrayList<PrioritizedTask>();

	public PrioritizedTask(int priority) {
		this.priority = priority;
		sequence.add(this);
	}

	public int compareTo(PrioritizedTask arg) {
		return priority < arg.priority ? 1 : (priority > arg.priority ? -1 : 0);
	}

	public void run() {
		try {
			TimeUnit.MILLISECONDS.sleep(rand.nextInt(250));
		} catch (InterruptedException e) {
			// Acceptable way to exit
		}
		System.out.println(this);
	}

	@Override
	public String toString() {
		return String.format("[%1$-3d]", priority) + " Task " + id;
	}

	public String summary() {
		return "(" + id + ":" + priority + ")";
	}

	public static class EndSentinel extends PrioritizedTask {
		private ExecutorService exec;

		public EndSentinel(ExecutorService e) {
			super(-1); // Lowest priority in this program
			exec = e;
		}

		@Override
		public void run() {
			for (PrioritizedTask pt : sequence) {
				System.out.println(pt.summary());
			}
			System.out.println(this + " Calling shutdownNow()");
			exec.shutdownNow();
		}
	}
}

class PrioritizedTaskProducer implements Runnable {
	private Random rand = new Random(47);
	private Queue<Runnable> queue;
	private ExecutorService exec;

	public PrioritizedTaskProducer(Queue<Runnable> q, ExecutorService e) {
		queue = q;
		exec = e; // Used for EndSentinel
	}

	public void run() {
		// Unbounded queue;never blocks
		// Fill it up fast with random priorities
		for (int i = 0; i < 20; i++) {
			queue.add(new PrioritizedTask(rand.nextInt(10)));
			Thread.yield();
		}
		// Trickle in highest-priority jobs
		try {
			for (int i = 0; i < 10; i++) {
				TimeUnit.MILLISECONDS.sleep(250);
				queue.add(new PrioritizedTask(10));
			}
			// Add jobs,lowest priority first
			for (int i = 0; i < 10; i++) {
				queue.add(new PrioritizedTask(i));
			}
			// A sentinel to stop all the tasks
			queue.add(new PrioritizedTask.EndSentinel(exec));
		} catch (InterruptedException e) {
			// Acceptable way to exit
		}
		System.out.println("Finished PrioritizedTaskProducer");
	}
}

class PrioritizedTaskConsumer implements Runnable {
	private PriorityBlockingQueue<Runnable> q;

	public PrioritizedTaskConsumer(PriorityBlockingQueue<Runnable> q) {
		this.q = q;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				// Use current thread to run the task
				q.take().run();
			}
		} catch (InterruptedException e) {
			// Acceptable way to exit
		}
		System.out.println("Finished PrioritizedTaskConsumer");
	}
}