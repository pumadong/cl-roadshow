package com.cl.roadshow.share.point12;

import java.util.HashSet;
import java.util.Set;

/**
 * 一百万数据：检索第一个数组中，在第二个数组中存在的数
 * @author dongyongjin
 *
 */
public class DuplicateDetection {
	public static void main(String[] args) {
		int max = 1000*1000;
		int[] a1 = new int[max];
		int[] a2 = new int[max];
		for(int i = 0; i < max; i++) {
			a1[i] = i;
			a2[i] = a1[i] + 100;
		}
		// O(N) 134毫秒
		long start = System.currentTimeMillis();
		Set<Integer> set2 = new HashSet<Integer>();
		for(int i = 0; i < a2.length; i++) {
			set2.add(a2[i]);
		}
		for(int i = 0; i < a1.length; i++) {
			if(set2.contains(a1[i])) {
				a1[i] = -1;
			}
		}
		System.out.println("O(N) : " + (System.currentTimeMillis() - start));
		// O(N^2) 313209毫秒
		start = System.currentTimeMillis();
		for(int i = 0; i < a1.length; i++) {
			for(int j = 0; j < a2.length; j++) {
				if(a1[i] == a2[j]) {
					a1[i] = -1;
				}
			}
		}
		System.out.println("O(N^2) : " + (System.currentTimeMillis() - start));
	}
}
