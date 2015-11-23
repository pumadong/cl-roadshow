package com.cl.roadshow.java.jtp.fulltimeout;

/**
 * 演示"A General-Purpose Wait-Until Pattern"，代码示例来源于：《Java Thread Programming》第14章
 * 
 * 避免Early-Notify，导致线程等待时间不够
 * 
 */
public class FullWait {
    private volatile int value;
    public FullWait(int initialValue) {
        value = initialValue;
    }
    
    public synchronized void setValue(int newValue) {
        if( value != newValue) {
            value = newValue;
            notifyAll();
        }
    }
    
    public synchronized boolean waitUntilAtLeast(int minValue, long msTimeout) throws InterruptedException {
        if( msTimeout == 0L ) {
            while( value < minValue ) {
                // wait indefinitely until notified
                wait();
            }
            // condition has finally been met
            return true;
        }
        // only wait from the specified amount of time
        long endTime = System.currentTimeMillis() + msTimeout;
        long msRemaining = msTimeout;
        while( (value < minValue) && (msRemaining > 0L) ) {
            wait(msRemaining);
            msRemaining = endTime - System.currentTimeMillis();
        }
        // May have timed out, or may have met value, calc return value
        return ( value >= minValue );
    }
    
    public String toString() {
        return getClass().getName() + "[value=" + value + "]";
    }
}
