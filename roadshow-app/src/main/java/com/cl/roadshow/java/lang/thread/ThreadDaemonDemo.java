package com.cl.roadshow.java.lang.thread;

/**
 *  演示后台线程
 * 
 *  http://lavasoft.blog.51cto.com/62575/221845/
 *  http://www.cnblogs.com/super-d2/p/3348183.html
 *  
 *  JRE判断程序是否执行结束的标准是所有的前台执线程行完毕了，而不管后台线程的状态
 */
public class ThreadDaemonDemo {
    public static void main(String[] args) { 
        Thread t1 = new MyCommon(); 
        Thread t2 = new Thread(new MyDaemon()); 
      //设置为守护线程 
        t2.setDaemon(true);        

        t2.start(); 
        t1.start(); 
    }
    
    static class MyCommon extends Thread { 
        public void run() { 
            for (int i = 0; i < 5; i++) { 
                System.out.println("线程1第" + i + "次执行！"); 
                try { 
                        Thread.sleep(7); 
                } catch (InterruptedException e) { 
                        e.printStackTrace(); 
                } 
            } 
        } 
    } 
    
    static class MyDaemon implements Runnable { 
        public void run() { 
            for (long i = 0; i < 9999999L; i++) { 
                System.out.println("后台线程第" + i + "次执行！"); 
                try { 
                        Thread.sleep(7); 
                } catch (InterruptedException e) { 
                        e.printStackTrace(); 
                } 
            } 
        } 
    }
}
