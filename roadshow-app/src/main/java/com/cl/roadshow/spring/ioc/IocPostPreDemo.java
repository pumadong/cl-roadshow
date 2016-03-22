package com.cl.roadshow.spring.ioc;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@SuppressWarnings("resource")
public class IocPostPreDemo {
	public static void main(String[] args) {
		System.out.println("begin");

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "com/cl/roadshow/spring/ioc/beans-autoscan.xml" });
		ctx.getBean("iocSortUtil",IocSortUtil.class);
		ctx.destroy();
		
		System.out.println("end\n");
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
            	System.out.println("addShutdownHook");
            }
        }));
		
	}
}

@Service
// @Scope("prototype") is not ok
@Scope("singleton")
class IocSortUtil implements InitializingBean {
	public IocSortUtil() {
		System.out.println("1.construct");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("3.afterPropertiesSet");
	}
	
	@PostConstruct
    private void postConstruct() {
		System.out.println("2.postConstruct");
	}
	
	@PreDestroy
	private void preDestroy() {
		System.out.println("4.preDestroy");
	}
}
