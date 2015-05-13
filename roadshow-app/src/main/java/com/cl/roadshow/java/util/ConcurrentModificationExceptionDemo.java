package com.cl.roadshow.java.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * 演示ConcurrentModificationException出现的场景
 * 
 * 乐观锁的思想
 *
 */
@SuppressWarnings("unused")
public class ConcurrentModificationExceptionDemo {
	public static void main(String[] args) throws Exception {

		HashMap<String, Integer> hashMapTemp = new HashMap<String, Integer>();
		hashMapTemp.put("testHashMap", 1);
		Iterator<Entry<String, Integer>> iteratorTemp = hashMapTemp.entrySet().iterator();

		// 以下这行代码会引发java.util.ConcurrentModificationException，
		// 因为对聚集创建迭代器之后，进行遍历或者修改操作时，
		// 如果遇到期望的修改计数器和实际的修改计数器不一样的情况（modCount != expectedModCount）
		// 就会报这个Exception，乐观锁的思想
		// hashMapTemp.put("testCreateConcurrentModificationException", 2);
		while (iteratorTemp.hasNext()) {
			System.out.println(iteratorTemp.next().getKey());
		}

		// for循环,写法更简单一些，在编译后，还是会被转换为迭代器
		for (Entry<String, Integer> e : hashMapTemp.entrySet()) {
			System.out.println(e.getKey());
		}

		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("testArrayList");
		for (String s : arrayList) {
			Integer i = Integer.reverse((new java.util.Random().nextInt(100)));
			// 这行代码也会报ConcurrentModificationException
			// al.add(i.toString());
		}
	}
}
