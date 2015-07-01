package com.cl.roadshow.java.lang.thread;

/**
 *  线程起个名字演示，好处是jstack分析时，方便定位问题
 * 
 */
public class ThreadNameDemo {
    public static void main(String[] args) {
        for(int i = 0; i < 20; i++) {
            ThreadA a = new ThreadA();
            //a.setName("ThreadName");
            a.start();
        }
        
        for(int i = 0; i < 20; i++) {
            ThreadB b = new ThreadB();
            //b.setName("ThreadName");
            b.start();
        }
    }
    
    static class ThreadA extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

    static class ThreadB extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }
}


