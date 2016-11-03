package com.cl.roadshow.spring.perm;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class MonitorLogAnnotationProcessor {
	/**
	 * 也可以放在XML配置文件中
	 * 
		<bean id="logInterceptor" class="xx.xx"></bean>
		<aop:config>  
			<!-- 设置切面名，及切面类 -->  
			<aop:aspect id="logAspect" ref="logInterceptor">  
				<!-- 运行前方法配置，先择要执行的方法 ，并设置切入点  -->  
				<aop:around method="aroundMethod" pointcut="execution(* *(..)) &amp;&amp; @annotation(monitorLog)" />   
			</aop:aspect>  
		</aop:config>
	 */

    @Around("execution(* *(..)) && @annotation(log)")
    public Object aroundMethod(ProceedingJoinPoint pjd ,MonitorLog log) throws Throwable {
        Object result = null;
        String monitorKey =  log.MonitorKey();
        if(monitorKey==null || "".equals(monitorKey)){
            MethodSignature signature = (MethodSignature) pjd.getSignature();
            Method method = signature.getMethod();
            String mName = method.getName();
            Class<?> cls =  method.getDeclaringClass() ;
            String cName = cls.getName() ;
            monitorKey= cName+"."+mName ;
        }

        long start = System.currentTimeMillis();
        try {
            System.out.println("invoke-before");
            result = pjd.proceed();
            System.out.println("invoke-after");
        } catch (Exception e) {
            System.out.println(System.currentTimeMillis() - start);
            throw e;
        }finally {
            System.out.println(System.currentTimeMillis() - start);
        }
        return result;
    }

}
