package com.cl.roadshow.spring.perm;

import org.springframework.stereotype.Component;

/**
 * 使用说明：
 * 1、默认情况下，一个类一个Cache
 * 2、可以通过group定义多个类的多个方法使用同一个Cache
 * 3、对于同一个Cache，timeout，size参数都一样，按照第一个被调用的方法的来
 * 4、如果是类作为参数：必须实现GuavaCacheInterface接口，如果不实现，则会用toString()的MD5作为Key
 * 
 * @author dongyongjin
 *
 */
@Component
public class GuavaCacheExample {
	
    @GuavaCache
    public String getGuavaCacheString(String input) {
    	return input;
    }
    
    @GuavaCache(group="group2", timeout=1, size=10)
    public int getGuavaCacheInteger(int input) {
    	return input;
    }
    
    @GuavaCache(group="group3", timeout=30, size=10)
    public Key getGuavaCacheObject(Key input, String str, Integer n) {
    	return input;
    }
    
    /**
     * 如果是类作为参数名：必须实现GuavaCacheInterface接口，如果不实现，则会用toString()的MD5作为Key
     * @author dongyongjin
     *
     */
    public static class Key implements GuavaCacheInterface {
    	private Integer age;
    	private String name;
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		@Override
		public String getCacheKey() {
			return "Key [age=" + age + ", name=" + name + "]";
		}
    }
}
