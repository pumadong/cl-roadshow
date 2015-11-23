package com.cl.roadshow.java.jtp.threadpool;

/**
 * 演示线程池的实现，代码示例来源于：《Java Thread Programming》第13章
 * 
 * 我们可以看到，JDK 5 中的 ThreadPoolExecutor，和这个思路十分相似，只是 ThreadPoolExecutor 更强大
 * 
 */
public class ThreadPoolMain {
    public static Runnable makeRunnable(final String name,final long firstDelay) {
        return new Runnable() {
            public void run() {
                try {
                    System.out.println(name + ": starting up");
                    Thread.sleep(firstDelay);
                    System.out.println(name + ": doing some stuff");
                    Thread.sleep(2000);
                    System.out.println(name + ": leaving");
                } catch(InterruptedException ix) {
                    System.out.println(name + ": got interrupted");
                    return;
                } catch(Exception x) {
                    x.printStackTrace();
                }
            }
            
            public String toString() {
                return name;
            }
        };
    }
    
    public static void main(String[] args) {
        
        try {
            ThreadPool pool = new ThreadPool(3);
            
            Runnable ra = makeRunnable("RA", 3000);
            pool.execute(ra);
            
            Runnable rb = makeRunnable("RB", 1000);
            pool.execute(rb);
            
            Runnable rc = makeRunnable("RC", 2000);
            pool.execute(rc);
            
            Runnable rd = makeRunnable("RD", 60000);
            pool.execute(rd);
            
            Runnable re = makeRunnable("RE", 10000);
            pool.execute(re);
            
            pool.stopRequestIdleWorkers();
            Thread.sleep(2000);
            pool.stopRequestIdleWorkers();
            Thread.sleep(5000);
            pool.stopRequestAllWorkers();
        } catch(InterruptedException ix) {
            ix.printStackTrace();
        }
    }
}
