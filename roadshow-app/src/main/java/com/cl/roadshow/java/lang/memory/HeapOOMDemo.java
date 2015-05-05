package com.cl.roadshow.java.lang.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * 最简单的堆内存溢出
 * 
 * java -Xmx2m HeapOOMDemo
 */
public class HeapOOMDemo {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		while (true) {
			list.add("内存溢出啊，内存溢出啊！");
		}
	}
}
