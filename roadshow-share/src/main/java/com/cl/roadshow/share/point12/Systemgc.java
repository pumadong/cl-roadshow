package com.cl.roadshow.share.point12;

/**
 * System.gc()会立刻产生GC吗？
 * @author dongyongjin
 *
 */
public class Systemgc {
	public static void main(String[] args) throws Exception {
		while(true) {
			Thread.sleep(3*1000);
			System.gc();
		}
	}
}
