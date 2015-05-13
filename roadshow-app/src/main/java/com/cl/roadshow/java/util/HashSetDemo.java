package com.cl.roadshow.java.util;

import java.util.HashSet;
import java.util.Set;

import com.alibaba.fastjson.JSON;

/**
 * HashSet如何实现判重
 * 
 *
 */
public class HashSetDemo {
	public static void main(String[] args) {

		HashSetDemo hashSetDemo = new HashSetDemo();

		Set<PersonWithHashcode> personWithHashcodeSet = new HashSet<PersonWithHashcode>();
		personWithHashcodeSet.add(hashSetDemo.new PersonWithHashcode("张三"));
		personWithHashcodeSet.add(hashSetDemo.new PersonWithHashcode("张三"));
		System.out.println(JSON.toJSONString(personWithHashcodeSet));

		Set<PersonWithoutHashcode> personWithoutHashcodeSet = new HashSet<PersonWithoutHashcode>();
		personWithoutHashcodeSet.add(hashSetDemo.new PersonWithoutHashcode("张三"));
		personWithoutHashcodeSet.add(hashSetDemo.new PersonWithoutHashcode("张三"));
		System.out.println(JSON.toJSONString(personWithoutHashcodeSet));

	}

	/**
	 * 验证哈希的Key是否存在，必须同时重写hashCode和equals方法，看看HashMap的源码就知道了
	 * 
	 * 验证key是否存在的代码：if (e.hash == hash && ((k = e.key) == key || key.equals(k)))
	 *
	 */
	class PersonWithHashcode {
		private String name;

		public PersonWithHashcode(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			}
			if (o instanceof PersonWithHashcode) {
				PersonWithHashcode p = (PersonWithHashcode) o;
				boolean b = this.name.equals(p.name);
				return b;
			}
			return false;
		}

		@Override
		public int hashCode() {
			int h = 0;
			char val[] = name.toCharArray();

			for (int i = 0; i < val.length; i++) {
				h = (33 * h) + val[i];
			}
			return h;
		}
	}

	class PersonWithoutHashcode {
		private String name;

		public PersonWithoutHashcode(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
