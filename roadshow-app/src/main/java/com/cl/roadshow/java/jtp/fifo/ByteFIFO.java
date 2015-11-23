package com.cl.roadshow.java.jtp.fifo;

/**
 * 演示线程安全的FIFO，代码示例来源于：《Java Thread Programming》 第 18 章
 * 
 * 我们可以看到，JDK 5 才开始的BlockingQueue，和这个原理十分相似
 * 
 */
public class ByteFIFO extends Object {
    private byte[] queue;
    private int capacity;
    private int size;
    private int head;
    private int tail;
    
    public ByteFIFO(int cap) {
        // at lease 1
        capacity = (cap > 0) ? cap : 1;
        queue = new byte[capacity];
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
    
    public synchronized void add(byte b) throws InterruptedException {
        waitWhileFull();
        queue[head] = b;
        head = (head + 1) % capacity;
        size++;
        // let any waiting threads know about change
        notifyAll();
    }
    
    public synchronized void add(byte[] list) throws InterruptedException {
        // 可以比较一下：ObjectFIFO中addEach的实现，这个方法更有效率（有界队列有多少空闲，就添加多少个）
        // For efficiency, the bytes are copied in blocks instead of one at a time
        // As space becomes available, more bytes are copied until all of them have been aadded
        int ptr = 0;
        while(ptr < list.length) {
            // If full, the lock will be released to allow another thread to come in and remove bytes.
            waitWhileFull();
            int space = capacity - size;
            int distToEnd = capacity - head;
            // distToEnd：循环数组中，head（添加元素的位置标记）的剩余空间；space：循环数组剩余容量
            // blockLen：取最大的可用连续块
            int blockLen = Math.min(space, distToEnd);
            int bytesRemaining = list.length - ptr;
            int copyLen = Math.min(blockLen, bytesRemaining);
            System.arraycopy(list, ptr, queue, head, copyLen);
            head = (head + copyLen) % capacity;
            size += copyLen;
            ptr += copyLen;
            // Keep the lock, but let any waiting threads know that something has changed
            notifyAll();
        }
    }
    
    public synchronized byte remove() throws InterruptedException {
        waitWhileEmpty();
        byte b = queue[tail];
        tail = (tail + 1) % capacity;
        size--;
        // let any waiting threads know about change
        notifyAll();
        return b;
    }
    
    public synchronized byte[] removeAll() throws InterruptedException {
        // 可以比较一下：ObjectFIFO中addEach的实现，这个方法更有效率
        // For efficiency, the bytes are copied in blocks insteand of one at a time
        if(isEmpty()) {
            // Nothing to remove, return a zero-length array and do not bother with notification since nothing was removed
            return new byte[0];
        }
        // based on the current size
        byte[] list = new byte[size];
        System.out.println(Thread.currentThread().getName() + ":list.length:" + list.length);
        // copy in the block from tail to the end
        int distToEnd = capacity - tail;
        int copyLen = Math.min(size, distToEnd);
        System.arraycopy(queue, tail, list, 0, copyLen);
        // If data wraps around, copy the remaining data from the front of the array
        if(size > copyLen) {
            System.arraycopy(queue, 0, list, copyLen, size - copyLen);
        }
        tail = (tail + size) % capacity;
        // everything has been removed
        size = 0;
        // Signal any and all waiting threads that something has changed
        notifyAll();
        return list;
    }
    
    public synchronized byte[] removeAtLeastOne() throws InterruptedException {
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