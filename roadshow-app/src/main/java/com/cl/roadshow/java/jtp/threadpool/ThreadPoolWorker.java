package com.cl.roadshow.java.jtp.threadpool;

import com.cl.roadshow.java.jtp.fifo.ObjectFIFO;

/**
 * 演示线程池的实现，代码示例来源于：《Java Thread Programming》第13章
 * 
 * 我们可以看到，JDK 5 中的 ThreadPoolExecutor，和这个思路十分相似，只是 ThreadPoolExecutor 更强大
 * 
 */
public class ThreadPoolWorker {
    private static int nextWorkerId = 0;
    private ObjectFIFO idleWorkers;
    private int workerID;
    private ObjectFIFO handoffBox;
    private Thread internalThread;
    private volatile boolean noStopRequested;
    
    public ThreadPoolWorker(ObjectFIFO idleWorkders) {
        this.idleWorkers = idleWorkders;
        workerID = getNextWorkderId();
        // only one slot
        handoffBox = new ObjectFIFO(1);
        // just before returing, the thread should be created
        noStopRequested = true;
        Runnable r = new Runnable() {
            public void run() {
                try {
                    runWork();
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }
        };
        internalThread = new Thread(r);
        internalThread.start();
    }
    
    public static synchronized int getNextWorkderId() {
        // notice: sync'd at the class level to ensure uniqueness
        int id = nextWorkerId;
        nextWorkerId++;
        return id;
    }
    
    public void process(Runnable target) throws InterruptedException {
        handoffBox.add(target);
    }
    
    private void runWork() {
        while(noStopRequested) {
            try {
                System.out.println("workerID=" + workerID + ", ready for work");
                // Workder is ready work. This will never block because the idleWorker FIFO has enough capacity for all the workers
                idleWorkers.add(this);
                // Wait here until the server adds a request
                Runnable r = (Runnable) handoffBox.remove();
                System.out.println("workerID=" + workerID + ", starting execution of new Runnable:" + r);
                // catches all exceptions
                runIt(r);
            } catch(InterruptedException x) {
                // re-assert
                Thread.currentThread().interrupt();
            }
        }
    }
    
    private void runIt(Runnable r) {
        try {
            r.run();
        } catch(Exception runex) {
            // catch any and all exceptions
            System.err.println("Uncaught exception fell through from run()");
            runex.printStackTrace();
        } finally {
            // Clear the interrupted flag (in case it comes back)
            // set) so that if the loop goes again, the handoffBox.remove() does not mistakenly 
            // throw an InterruptedExcetion
            Thread.interrupted();
        }
    }
    
    public void stopRequest() {
        System.out.println("workerID=" + workerID + ", stopRequest() received.");
        noStopRequested = false;
        internalThread.interrupt();
    }
    
    public boolean isAlive() {
        return internalThread.isAlive();
    }
}
