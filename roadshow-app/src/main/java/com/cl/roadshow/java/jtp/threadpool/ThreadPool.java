package com.cl.roadshow.java.jtp.threadpool;

import com.cl.roadshow.java.jtp.fifo.ObjectFIFO;

/**
 * 演示线程池的实现，代码示例来源于：《Java Thread Programming》第13章
 * 
 * 我们可以看到，JDK 5 中的 ThreadPoolExecutor，和这个思路十分相似，只是 ThreadPoolExecutor 更强大
 * 
 */
public class ThreadPool {
    private ObjectFIFO idleWorkers;
    private ThreadPoolWorker[] workerList;
    
    public ThreadPool(int numberOfThreads) {
        // make sure that it's at least one
        numberOfThreads = Math.max(1, numberOfThreads);
        idleWorkers = new ObjectFIFO(numberOfThreads);
        workerList = new ThreadPoolWorker[numberOfThreads];
        for ( int i = 0; i < workerList.length; i++ ) {
            workerList[i] = new ThreadPoolWorker(idleWorkers);
        }
    }
    
    public void execute(Runnable target) throws InterruptedException {
        // block (forever) until a worker is available
        ThreadPoolWorker worker = (ThreadPoolWorker)idleWorkers.remove();
        worker.process(target);
    }
    
    public void stopRequestIdleWorkers() {
        try {
            Object[] idle = idleWorkers.removeAll();
            for(int i = 0; i < idle.length; i++) {
                ((ThreadPoolWorker) idle[i]).stopRequest();
            }
        } catch ( InterruptedException x ) {
            // re-assert
            Thread.currentThread().interrupt();
        }
    }
    
    public void stopRequestAllWorkers() {
        // Stop the idle one’s first productive
        stopRequestIdleWorkers();
        // give the idle workers a quick chance to die
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }
        // Step through the list of ALL workers.
        for( int i = 0; i < workerList.length; i++) {
            if(workerList[i].isAlive()) {
                workerList[i].stopRequest();
            }
        }
    }
}
