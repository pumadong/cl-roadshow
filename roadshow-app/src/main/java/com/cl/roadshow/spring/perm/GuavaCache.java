package com.cl.roadshow.spring.perm;

import java.lang.annotation.*;

/**
 * 注解方式使用Guava缓存
 * @author dongyongjin
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GuavaCache {
	/**
	 * group : 一个group代表一个cache，不传或者""则使用 package+class 作为cache名字
	 * @return
	 */
	public String group() default "";
	/**
	 * key : 注意，所有参数必须实现GuavaCacheInterface接口，如果不实现，则会用toString()的MD5作为Key
	 * @return
	 */
    public String key() default "";
    /**
     * 过期时间，缺省30秒
     * @return
     */
    public long timeout() default 30;
    /**
     * 缓存最大条目，缺省10000
     * @return
     */
    public long size() default 10000;
}
