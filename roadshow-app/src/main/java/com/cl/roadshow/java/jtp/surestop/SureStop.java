package com.cl.roadshow.java.jtp.surestop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 演示"SureStop Utility"，使用Thread.stop()作为停止线程的最后手段，代码示例来源于：《Java Thread Programming》 第 16 章
 * 
 * 这个工具只是演示一种技术和思想，实际工作中stop()已经是deprecated，并且线程应该优雅的停止，这个工具如果使用，只能是作为最后的手段
 * 
 */
@SuppressWarnings(value={"unchecked","rawtypes","deprecation"})
public class SureStop extends Object {
    // nested internal class for stop request entries
    private static class Entry extends Object {
        private Thread thread;
        private long stopTime;
        private Entry(Thread t, long stop) {
            thread = t;
            stopTime = stop;
        }
    }
    // static reference to the singleton instance
    private static SureStop ss;
    
    static {
        // When class is loaded, create exactly one instance using the private constructor
        ss = new SureStop();
    }
    
    private List stopList;
    private List pendingList;
    private Thread internalThread;
    
    private SureStop() {
        // Using a linked list for fast deletions
        stopList = new LinkedList();
        // Enough initial capacity for 20 pending additions, will grow automatically if necessary to keep ensureStop() from blocking
        pendingList = new ArrayList(20);
        Runnable r = new Runnable() {
            public void run() {
                try {
                    runWork();
                } catch(Exception x) {
                    // in case ANY exception slips through
                    x.printStackTrace();
                }
            }
        };
        internalThread = new Thread(r);
        // no need to run alone
        internalThread.setDaemon(true);
        // high
        internalThread.setPriority(Thread.MAX_PRIORITY);
        internalThread.start();
    }
    
    private void runWork() {
        try {
            while(true) {
                // Since this is a super-high priority thread, be sure to give other threads a chance to run each time
                // through in case the wait on pendingList is very short
                Thread.sleep(500);
                // Stop expired threads and determine the amount of time until the next thread is due to expire
                long sleepTime = checkStopList();
                synchronized(pendingList) {
                    if(pendingList.size() < 1) {
                        pendingList.wait(sleepTime);
                    }
                    if(pendingList.size() > 0) {
                        // Copy into stopList and then remove from pendingList
                        stopList.addAll(pendingList);
                        pendingList.clear();
                    }
                }
            } // while
        } catch(InterruptedException x) {
            // ignore
        } catch (Exception x) {
            // Never expect this, but print a trace in case it happens
            x.printStackTrace();
        }
    }
    
    // 设计很精巧，找出最小的wait时间，看看JDK中Timer的实现，就是这样的
    private long checkStopList() {
        // called from runWork() by the internal thread
        long currTime = System.currentTimeMillis();
        long minTime = Long.MAX_VALUE;
        Iterator iter = stopList.iterator();
        while(iter.hasNext()) {
            Entry entry = (Entry) iter.next();
            if(entry.thread.isAlive()) {
                if(entry.stopTime < currTime) {
                    // timed out, stop it abruptly right now
                    try {
                        entry.thread.stop();
                    } catch(SecurityException x) {
                        // Catch this here so that other operations are not  disrupted
                        // that thread could not be stopped
                        System.err.println("SureStop was not permitted to stop thread=" + entry.thread);
                        x.printStackTrace();
                    }
                    // Since it has stopped, remove it from stopList from stopList
                    iter.remove();
                } else {
                    // Not yet expired, check to see if this is the new minimum
                    minTime = Math.min(entry.stopTime, minTime);
                }
            } else {
                // Thread died on its own, remove it from stopList
                iter.remove();
            } // if alive
        } // while
        
        long sleepTime = minTime - System.currentTimeMillis();
        // ensure that it is a least a little bit of time
        sleepTime = Math.max(50, sleepTime);
        return sleepTime;
    }
    
    private void addEntry(Entry entry) {
        // called from ensureStop() by external thread
        synchronized(pendingList) {
            pendingList.add(entry);
            // no need for notifyAll(), one waiter
            pendingList.notify();
        }
    }
    
    public static void ensureStop(Thread t, long msGracePeriod) {
        if( !t.isAlive()) {
            // thread is already stopped, return right away
            return;
        }
        long stopTime = System.currentTimeMillis() + msGracePeriod;
        Entry entry = new Entry(t, stopTime);
        ss.addEntry(entry);
    }
}