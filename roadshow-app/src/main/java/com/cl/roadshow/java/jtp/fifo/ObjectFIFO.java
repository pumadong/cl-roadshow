package com.cl.roadshow.java.jtp.fifo;

/**
 * 演示线程安全的FIFO，代码示例来源于：《Java Thread Programming》 第 18 章
 * 
 * 我们可以看到，JDK 5 才开始的BlockingQueue，和这个原理十分相似
 * 
 * 关注一下 removeAll ，这是一种很低效的实现方式，批量操作时，用简单的循环
 *
 */
public class ObjectFIFO extends Object {
    private Object[] queue;
    private int capacity;
    private int size;
    private int head;
    private int tail;
    
    public ObjectFIFO(int cap) {
        // at lease 1
        capacity = (cap > 0) ? cap : 1;
        queue = new Object[capacity];
        head = tail = size = 0;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public synchronized int getSize() {
        return size;
    }
    
    public synchronized boolean isEmpty() {
        return (size == 0);
    }
    
    public synchronized boolean isFull() {
        return (size == capacity);
    }
    
    public synchronized void add(Object obj) throws InterruptedException {
        waitWhileFull();
        queue[head] = obj;
        head = (head + 1) % capacity;
        size++;
        // let any waiting threads know about change
        notifyAll();
    }
    
    public synchronized void addEach(Object[] list) throws InterruptedException {
        // You might want to code a more efficient implementation here ...
        for(int i = 0; i < list.length; i++) {
            add(list[i]);
        }
    }
    
    public synchronized Object remove() throws InterruptedException {
        waitWhileEmpty();
        Object obj = queue[tail];
        // don't block GC by keeping unnecessary reference
        queue[tail] = null;
        tail = (tail + 1) % capacity;
        size--;
        // let any waiting threads know about change
        notifyAll();
        return obj;
    }
    
    public synchronized Object[] removeAll() throws InterruptedException {
        // You might want to code a more efficient implementation here ...
        // use the current size
        Object[] list = new Object[size];
        for(int i = 0; i < list.length; i++) {
            list[i] = remove();
        }
        // if FIFO was empty, a zero-length array is returned
        return list;
    }
    
    public synchronized Object[] removeAtLeastOne() throws InterruptedException {
        // wait for at least one to be in FIFO
        waitWhileEmpty();
        return removeAll();
    }
    
    public synchronized boolean waitUntilEmpty(long msTimeout) throws InterruptedException {
        if(msTimeout == 0L) {
            // use other method
            waitUntilEmpty();
            return true;
        }
        // wait only for the specified amout of time
        long endTime = System.currentTimeMillis() + msTimeout;
        long msRemaining = msTimeout;
        while(!isEmpty() && (msRemaining > 0L)) {
            wait(msRemaining);
            msRemaining = endTime - System.currentTimeMillis();
        }
        // May have timed out, or may have met condition, calc return value.
        return isEmpty();
    }
    
    public synchronized void waitUntilEmpty() throws InterruptedException {
        while( !isEmpty()) {
            wait();
        }
    }
    
    public synchronized void waitWhileEmpty() throws InterruptedException {
        while( isEmpty()) {
            wait();
        }
    }
    
    public synchronized void waitUntilFull() throws InterruptedException {
        while( !isFull()) {
            wait();
        }
    }
    
    public synchronized void waitWhileFull() throws InterruptedException {
        while( isFull()) {
            wait();
        }
    }
}