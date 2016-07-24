package com.cl.roadshow.spring.perm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class AopTest {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "com/cl/roadshow/spring/perm/beans-perm.xml" });
		
		for(int i=0;i<20;i++) {
            ErrorService errorService = ctx.getBean("errorService", ErrorService.class);
            System.out.print("i:" + i);
            System.out.print(",obj:" + errorService.toString());
            System.out.print(",clazz:" + errorService.getClass() + "_" + errorService.getClass().hashCode());
            System.out.println(",hashcode-classloader:" + errorService.getClass().getClassLoader());
            System.out.println("\n");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

	}

    @Autowired
    private ApplicationContext ac;

    /**
     * -XX:+TraceClassLoading
     * SCOPE_PROTOTYPE + aop
     */
    public void testGetBean(){
        for(int i=0;i<22;i++) {
            ErrorService errorService = ac.getBean("errorService", ErrorService.class);
            System.out.print("i:" + i);
            System.out.print(",obj:" + errorService.toString());
            System.out.print(",clazz:" + errorService.getClass());
            System.out.println(",hashcode-classloader:" + errorService.getClass().getClassLoader());
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
