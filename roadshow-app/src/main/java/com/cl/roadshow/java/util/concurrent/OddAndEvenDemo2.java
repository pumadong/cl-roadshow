package com.cl.roadshow.java.util.concurrent;

/**
 * Java并发仿真程序：一个线程打印奇数，另一个线程打印偶数
 * 
 */
public class OddAndEvenDemo2 {
    static Object lock = new Object();
    
    static  int num = 1;
    
    public static void main(String[] args) {
        
        // odd
        new Thread(new Runnable() {
            public void run() {
                synchronized(lock) {
                    while(num < 10) {
                        while(num % 2 == 1 && num < 10) {
                            try {
                                lock.wait();
                            } catch (Exception e) {
    
                            }
                            System.out.println(Thread.currentThread().getName() + " : " + num);
                            num++;
                            lock.notify();
                        }
                        if(num % 2 == 0){
                            System.out.println(Thread.currentThread().getName() + " : " + num);
                            num++;
                            lock.notify();
                        }
                    }
                }
            }
        }).start();;
        
        // even
        new Thread(new Runnable() {
            public void run() {
                synchronized(lock) {
                    while(num < 10) {
                        while(num % 2 == 0 && num < 10) {
                            try {
                                lock.wait();
                            } catch (Exception e) {
    
                            }
    
                        }
                        if(num % 2 == 1){
                            System.out.println(Thread.currentThread().getName() + " : " + num);
                            num++;
                            lock.notify();
                        }
                    }
                }
            }
        }).start();;
    }
}
