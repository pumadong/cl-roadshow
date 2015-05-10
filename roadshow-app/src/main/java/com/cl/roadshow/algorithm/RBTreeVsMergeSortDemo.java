package com.cl.roadshow.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

/**
 * 比较红黑树和归并排序的性能：这对于推荐的排序计算中节省时间很有帮助
 * 
 * http://blog.csdn.net/puma_dong/article/details/39995321
 *
 */
public class RBTreeVsMergeSortDemo {

	public static void main(String[] args) {
		RBTreeVsMergeSortDemo demo = new RBTreeVsMergeSortDemo();

		final int LEN = 100000;
		final int SEED = 1000000;

		Random r = new Random();

		System.out.println("---------------------------");

		long beginTime = System.currentTimeMillis();

		ArrayList<Temp> tempArrayList = new ArrayList<Temp>();

		for (int i = 0; i < LEN; i++) {
			int id = HashDemo.bernstein(String.valueOf(r.nextInt(SEED)));
			Temp temp = demo.new Temp(id);
			tempArrayList.add(temp);
		}
		System.out.println("ArrayList数据初始化耗费时间" + ":" + String.valueOf(System.currentTimeMillis() - beginTime));

		beginTime = System.currentTimeMillis();
		Collections.sort(tempArrayList, new Comparator<Temp>() {
			@Override
			public int compare(Temp t1, Temp t2) {
				if ((t1.id > t2.id) && ((t1.id - t2.id) < 0)) {
					System.out.println(t1.id + "-" + t2.id);
				}
				return t1.id > t2.id ? 1 : (t1.id < t2.id ? -1 : 0);
				// JDK7中，如果这样写：return t1.id-t2.id，则可能报异常，原因是由于以上场景发生，溢出取值范围了
				// Comparison method violates its general contract
				// 参见文章：http://www.tuicool.com/articles/MZreyuv
			}
		});
		System.out.println("ArrayList数据排序耗费时间" + ":" + String.valueOf(System.currentTimeMillis() - beginTime));

		System.out.println("---------------------------");

		beginTime = System.currentTimeMillis();
		TreeSet<Temp> tempTreeSet = new TreeSet<Temp>(new Comparator<Temp>() {
			@Override
			public int compare(Temp t1, Temp t2) {
				return t1.id - t2.id;
			}
		});
		for (Temp t : tempArrayList) {
			tempTreeSet.add(t);
		}
		System.out.println("TreeSet数据初始化及排序总耗费时间" + ":" + String.valueOf(System.currentTimeMillis() - beginTime));

		System.out.println("---------------------------");
	}

	class Temp {

		public Temp(int id) {
			this.id = id;
		}

		public int id;
	}
}
