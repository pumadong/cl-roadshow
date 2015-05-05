package com.cl.roadshow.java.lang.memory;

import java.nio.ByteBuffer;

/**
 * 直接内存溢出
 * 
 * java -XX:MaxDirectMemorySize=256m ByteBufferOOM
 *
 */
public class ByteBufferOOMDemo {
	public static void main(String[] args) {
		ByteBuffer.allocateDirect(257 * 1024 * 1024);
	}
}
