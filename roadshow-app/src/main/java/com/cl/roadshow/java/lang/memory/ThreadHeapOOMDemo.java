package com.cl.roadshow.java.lang.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程堆溢出，则主线程崩溃，退出
 * 
 * java -Xmx2m HeapOOMDemo
 */
public class ThreadHeapOOMDemo {
	public static void main(String[] args) {
		ThreadHeapOOMDemo demo = new ThreadHeapOOMDemo();

		(new Thread(demo.new ThreadHeapOOM())).start();
	}

	class ThreadHeapOOM implements Runnable {
		@Override
		public void run() {
			List<String> list = new ArrayList<String>();
			int i = 0;
			while (true) {
				list.add("内存溢出啊，内存溢出啊！");
				i++;
				if ((i % 1000) == 0) {
					list.remove(i % 10);
				}
			}
		}
	}
}
