package com.cl.roadshow.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * SpringMvc中随时获得HttpServletRequest对象
 *
 * http://blog.csdn.net/nacey5201/article/details/8547772
 * http://blog.csdn.net/mydwr/article/details/8051086
 * http://itindex.net/detail/50450-java-web-%E5%88%A9%E7%94%A8
 * SpringWeb&SpringWebMvc：http://stackoverflow.com/questions/13533700/maven-dependency-spring-web-vs-spring-webmvc
 */
public class RequestContextListenerTest {
    
    public String getName() {
        
        //实现这个功能，只需要依赖两个jar即可：spring-web和servlet-api
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        return "request.getName()：" + request.getParameter("name");

    }
}
