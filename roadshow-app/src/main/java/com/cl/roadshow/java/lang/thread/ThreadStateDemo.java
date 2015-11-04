package com.cl.roadshow.java.lang.thread;

import java.util.LinkedHashSet;
import java.util.concurrent.atomic.AtomicLong;

/**
 *  线程状态演示：演示线程枚举ThreadState 6种状态中的5种的出现场景
 * 
 */
public class ThreadStateDemo {
    
    private static LinkedHashSet<String> threadStateList = new LinkedHashSet<String>();
    private static Boolean stopMonitor = false;
    private static AtomicLong atomicLong = new AtomicLong();
    
    private static Object lock = new Object();
    
    public static void main(String[] args) throws InterruptedException {
        
        ThreadA threadA = new ThreadA();
        threadA.setName("I'm ThreadA");
        
        ThreadB threadB = new ThreadB();
        
        ThreadMonitor threadMonitor = new ThreadMonitor(threadA);

        threadMonitor.start();
        
        Thread.sleep(2);
        
        threadA.start();
        threadB.start();
        
        Thread.sleep(2000);
        
        stopMonitor = true;
        
        for(String s : threadStateList) {
            System.out.println(s);
        }
        System.out.println("AtomicLong:" + atomicLong.longValue());
    }
    
    static class ThreadA extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized(lock) {
                System.out.println(Thread.currentThread().getName() + ":get lock");
            }
        }
    }
    
    static class ThreadB extends Thread {
        @Override
        public void run() {
            synchronized(lock) {
                try {
                    Thread.sleep(1600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ThreadMonitor extends Thread {
        private Thread t;
        public ThreadMonitor(Thread t) {
            this.t = t;
        }
        @Override
        public void run() {
            while(true && !stopMonitor) {
                synchronized(this) {
                    threadStateList.add(t.getState().toString());
                    atomicLong.incrementAndGet();
                }
            }
        }
    }
}


