package com.cl.roadshow.spring.perm;

/**
 * 使用GuavaCache注解时，如果传入参数是对象，则必须实现这个类
 * @author dongyongjin
 *
 */
public interface GuavaCacheInterface {
	public String getCacheKey();
}
