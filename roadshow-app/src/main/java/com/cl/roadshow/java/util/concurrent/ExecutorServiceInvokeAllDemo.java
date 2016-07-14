package com.cl.roadshow.java.util.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 测试ExecutorService.involeAll是否正常返回
 * 有同事反映timeout不生效，根据这个测试来看：timeout是生效的
 * @author dongyongjin
 *
 */
public class ExecutorServiceInvokeAllDemo {
	
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		List<Callable<String>> tasks = new ArrayList<Callable<String>>();
		ExecutorServiceInvokeAllDemo demo = new ExecutorServiceInvokeAllDemo();
		for (int i = 1; i < 6; i++) {
			tasks.add(demo.new NeverStopTask());
		}
		try {
			System.out.println("main:before inveokeAll " + System.currentTimeMillis());
			executorService.invokeAll(tasks, 1, TimeUnit.SECONDS);
			System.out.println("main:after invokeAll" + System.currentTimeMillis());
		} catch (Exception e) {
			e.printStackTrace();
		}
		executorService.shutdownNow();
	}
	
	class NeverStopTask implements Callable<String> {

	    @Override
	    public String call() throws Exception {
	        int no = 0;
	        try {
	            while (true) {
	                //不会停止
	                no++;
	                if (no % 10000000 == 0) {
	                    //System.out.println(Thread.currentThread().getName() + ":" + no);
	                }

	                //会停止
//	                no++;
//	                if (no % 10000000 == 0) {
//	                	System.out.println(Thread.currentThread().getName() + ":" + no);
//	                }
//	                TimeUnit.MILLISECONDS.sleep(100);
	            }
	        } catch (Exception e) {
	            System.out.println("task exception:" + e.getMessage());
	        }
	        return "foobar";
	    }
	}

}
