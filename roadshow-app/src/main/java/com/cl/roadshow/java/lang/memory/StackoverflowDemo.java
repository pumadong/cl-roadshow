package com.cl.roadshow.java.lang.memory;

/**
 * 栈溢出错误
 * 
 *
 */
public class StackoverflowDemo {
	public static void main(String[] args) {
		recursive(100);
	}

	static void recursive(int n) {
		recursive(n);
	}
}
