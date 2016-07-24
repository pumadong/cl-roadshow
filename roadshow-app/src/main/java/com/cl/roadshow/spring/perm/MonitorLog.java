package com.cl.roadshow.spring.perm;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MonitorLog {
    public String MonitorKey() default "";
    public long TimeOut() default 200;//单位毫秒
}
