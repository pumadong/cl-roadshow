package com.cl.roadshow.java.util;

import java.util.LinkedList;

/**
 * 用链表实现先进先出队列或者栈
 * 
 *
 */
public class QueueDemo {
	public static void main(String[] args) {

	}

	class Queue {
		private LinkedList<String> llt;

		public Queue() {
			llt = new LinkedList<String>();
		}

		public void add(String s) {
			llt.add(s);
		}

		public String get() {
			return llt.removeLast(); // 队列
			// return llt.removeFirst(); //堆栈
		}

		public boolean isNull() {
			return llt.isEmpty();
		}
	}
}
