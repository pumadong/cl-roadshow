package com.cl.roadshow.algorithm;

import java.util.HashSet;
import java.util.Set;

/**
 * 演示一些常用的哈希算法
 * 
 * 最常用的是time33哈希算法
 * 
 */
public class HashDemo {
	// 进行哈希计算的对象总数
	private static final int TOTAL = 1000;
	// 用来取余的质数：哈希索引范围会在[0,1499)之间
	private static final int PRIME = 1499;

	// private static final int SHIFT = 0;
	// private static final int MASK = 0x8765fed1;

	public static void main(String[] args) throws InterruptedException {
		Set<String> stringSet = new HashSet<String>();
		Set<byte[]> byteArraySet = new HashSet<byte[]>();
		for (int i = 0; i < TOTAL; i++) {
			Thread.sleep(1);
			String s = String.valueOf(System.currentTimeMillis());
			stringSet.add(s);
			byte[] b = new byte[s.length()];
			for (int j = 0; j < s.length(); j++) {
				b[j] = (byte) s.charAt(j);
			}
			byteArraySet.add(b);
		}

		Set<Integer> hashResult = new HashSet<Integer>();
		for (String s : stringSet) {
			hashResult.add(additiveHash(s, PRIME));
		}
		System.out.println("加法哈希" + ":" + hashResult.size());

		hashResult.clear();
		for (String s : stringSet) {
			hashResult.add(rotatingHash(s, PRIME));
		}
		System.out.println("旋转哈希" + ":" + hashResult.size());

		hashResult.clear();
		for (String s : stringSet) {
			hashResult.add(bernstein(s));
		}
		System.out.println("time33哈希" + ":" + hashResult.size());

		hashResult.clear();
		for (String s : stringSet) {
			hashResult.add(java(s));
		}
		System.out.println("java自带的哈希" + ":" + hashResult.size());

		hashResult.clear();
		for (String s : stringSet) {
			hashResult.add(apHash(s));
		}
		System.out.println("AP哈希" + ":" + hashResult.size());

		hashResult.clear();
		for (String s : stringSet) {
			hashResult.add(dekHash(s));
		}
		System.out.println("DEK哈希" + ":" + hashResult.size());

		hashResult.clear();
		for (String s : stringSet) {
			hashResult.add(djbHash(s));
		}
		System.out.println("DJB哈希" + ":" + hashResult.size());

		hashResult.clear();
		for (String s : stringSet) {
			hashResult.add(sdbmHash(s));
		}
		System.out.println("SDBM哈希" + ":" + hashResult.size());

		hashResult.clear();
		for (String s : stringSet) {
			hashResult.add(bkdrHash(s));
		}
		System.out.println("BKDR哈希" + ":" + hashResult.size());

		hashResult.clear();
		for (String s : stringSet) {
			hashResult.add(elfHash(s));
		}
		System.out.println("ELF哈希" + ":" + hashResult.size());

		hashResult.clear();
		for (String s : stringSet) {
			hashResult.add(pjwHash(s));
		}
		System.out.println("PJW哈希" + ":" + hashResult.size());

		hashResult.clear();
		for (String s : stringSet) {
			hashResult.add(jsHash(s));
		}
		System.out.println("JS哈希" + ":" + hashResult.size());

		hashResult.clear();
		for (String s : stringSet) {
			hashResult.add(rsHash(s));
		}
		System.out.println("RS哈希" + ":" + hashResult.size());

		hashResult.clear();
		for (byte[] b : byteArraySet) {
			hashResult.add(fnvHash(b));
		}
		System.out.println("32位FNV哈希" + ":" + hashResult.size());

		hashResult.clear();
		for (byte[] b : byteArraySet) {
			hashResult.add(fnvHash1(b));
		}
		System.out.println("改进的32位FNV哈希1" + ":" + hashResult.size());

		hashResult.clear();
		for (String s : stringSet) {
			hashResult.add(fnvHash2(s));
		}
		System.out.println("改进的32位FNV哈希2" + ":" + hashResult.size());
	}

	/**
	 * 加法哈希
	 * 
	 * @param key
	 * @param prime
	 * @return
	 */
	public static int additiveHash(String key, int prime) {
		int hash, i;
		for (hash = key.length(), i = 0; i < key.length(); i++) {
			hash += key.charAt(i);
		}
		return (hash % prime);
	}

	/**
	 * 旋转哈希
	 * 
	 * @param key
	 * @param prime
	 * @return
	 */
	public static int rotatingHash(String key, int prime) {
		int hash, i;
		for (hash = key.length(), i = 0; i < key.length(); ++i) {
			hash = (hash << 4) ^ (hash >> 28) ^ key.charAt(i);
		}
		return (hash % prime);
	}

	/**
	 * time33 哈希函数，又叫 DJBX33A，Bernstein's hash
	 * 
	 * php, apache, perl,sddb都使用time33哈希.
	 * 
	 * @param key
	 * @return
	 */
	public static int bernstein(String key) {
		int hash = 0;
		int i;
		for (i = 0; i < key.length(); ++i) {
			hash = (33 * hash) + key.charAt(i);
		}
		return hash;
	}

	/**
	 * java自带的算法，和time33有点像
	 * 
	 * @param str
	 * @return
	 */
	public static int java(String str) {
		int h = 0;
		int off = 0;
		int len = str.length();
		for (int i = 0; i < len; i++) {
			h = (31 * h) + str.charAt(off++);
		}
		return h;
	}

	/**
	 * AP算法
	 * 
	 * @param str
	 * @return
	 */
	public static int apHash(String str) {
		int hash = 0;

		for (int i = 0; i < str.length(); i++) {
			hash ^= ((i & 1) == 0) ? ((hash << 7) ^ str.charAt(i) ^ (hash >> 3))
					: (~((hash << 11) ^ str.charAt(i) ^ (hash >> 5)));
		}
		return hash;
	}

	/**
	 * DEK算法
	 * 
	 * @param str
	 * @return
	 */
	public static int dekHash(String str) {
		int hash = str.length();

		for (int i = 0; i < str.length(); i++) {
			hash = ((hash << 5) ^ (hash >> 27)) ^ str.charAt(i);
		}
		return (hash & 0x7FFFFFFF);
	}

	/**
	 * DJB算法
	 * 
	 * @param str
	 * @return
	 */
	public static int djbHash(String str) {
		int hash = 5381;

		for (int i = 0; i < str.length(); i++) {
			hash = ((hash << 5) + hash) + str.charAt(i);
		}
		return (hash & 0x7FFFFFFF);
	}

	/**
	 * SDBM算法
	 * 
	 * @param str
	 * @return
	 */
	public static int sdbmHash(String str) {
		int hash = 0;

		for (int i = 0; i < str.length(); i++) {
			hash = (str.charAt(i) + (hash << 6) + (hash << 16)) - hash;
		}
		return (hash & 0x7FFFFFFF);
	}

	/**
	 * BKDR算法
	 * 
	 * @param str
	 * @return
	 */
	public static int bkdrHash(String str) {
		int seed = 131; // 31 131 1313 13131 131313 etc..
		int hash = 0;

		for (int i = 0; i < str.length(); i++) {
			hash = (hash * seed) + str.charAt(i);
		}
		return (hash & 0x7FFFFFFF);
	}

	/**
	 * ELF算法
	 * 
	 * @param str
	 * @return
	 */
	public static int elfHash(String str) {
		int hash = 0;
		int x = 0;

		for (int i = 0; i < str.length(); i++) {
			hash = (hash << 4) + str.charAt(i);
			if ((x = (int) (hash & 0xF0000000L)) != 0) {
				hash ^= (x >> 24);
				hash &= ~x;
			}
		}
		return (hash & 0x7FFFFFFF);
	}

	/**
	 * PJW算法
	 * 
	 * @param str
	 * @return
	 */
	public static int pjwHash(String str) {
		int BitsInUnsignedInt = 32;
		int ThreeQuarters = (BitsInUnsignedInt * 3) / 4;
		int OneEighth = BitsInUnsignedInt / 8;
		int HighBits = 0xFFFFFFFF << (BitsInUnsignedInt - OneEighth);
		int hash = 0;
		int test = 0;

		for (int i = 0; i < str.length(); i++) {
			hash = (hash << OneEighth) + str.charAt(i);

			if ((test = hash & HighBits) != 0) {
				hash = ((hash ^ (test >> ThreeQuarters)) & (~HighBits));
			}
		}
		return (hash & 0x7FFFFFFF);
	}

	/**
	 * JS哈希
	 * 
	 * @param str
	 * @return
	 */
	public static int jsHash(String str) {
		int hash = 1315423911;

		for (int i = 0; i < str.length(); i++) {
			hash ^= ((hash << 5) + str.charAt(i) + (hash >> 2));
		}
		return (hash & 0x7FFFFFFF);
	}

	/**
	 * RS算法
	 * 
	 * @param str
	 * @return
	 */
	public static int rsHash(String str) {
		int b = 378551;
		int a = 63689;
		int hash = 0;

		for (int i = 0; i < str.length(); i++) {
			hash = (hash * a) + str.charAt(i);
			a = a * b;
		}
		return (hash & 0x7FFFFFFF);
	}

	/**
	 * 32位FNV算法
	 * 
	 * @param data
	 * @return
	 */
	public static int fnvHash(byte[] data) {
		int hash = (int) 2166136261L;
		for (byte b : data) {
			hash = (hash * 16777619) ^ b;
		}
		return hash;

		// if (SHIFT == 0) {
		// return hash;
		// }
		// return (hash ^ (hash >> SHIFT)) & MASK;
	}

	/**
	 * 改进的32位FNV算法1
	 * 
	 * @param data
	 * @return
	 */
	public static int fnvHash1(byte[] data) {
		final int p = 16777619;
		int hash = (int) 2166136261L;
		for (byte b : data) {
			hash = (hash ^ b) * p;
		}
		hash += hash << 13;
		hash ^= hash >> 7;
		hash += hash << 3;
		hash ^= hash >> 17;
		hash += hash << 5;
		return hash;
	}

	/**
	 * 改进的32位FNV算法1
	 * 
	 * @param data
	 * @return
	 */
	public static int fnvHash2(String data) {
		final int p = 16777619;
		int hash = (int) 2166136261L;
		for (int i = 0; i < data.length(); i++) {
			hash = (hash ^ data.charAt(i)) * p;
		}
		hash += hash << 13;
		hash ^= hash >> 7;
		hash += hash << 3;
		hash ^= hash >> 17;
		hash += hash << 5;
		return hash;
	}
}
