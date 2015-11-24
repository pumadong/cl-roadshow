package com.cl.roadshow.java.jtp.blockedio;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;

/**
 * 演示"Demonstration of interrupt() and stop() Being ignored"，代码示例来源于：《Java Thread Programming》 第 15 章
 * 
 */
public class DefiantStream extends Object {
    public static void main(String[] args) {
        final InputStream in = System.in;
        
        Runnable r = new Runnable() {
            public void run() {
                try {
                    System.err.println("about to try to read from in");
                    in.read();
                    System.err.println("just read from in");
                } catch (InterruptedIOException iiox) {
                    iiox.printStackTrace();
                } catch (IOException iox) {
                    iox.printStackTrace();
                } catch (Exception x) {
                    x.printStackTrace();
                } finally {
                    Thread currThread = Thread.currentThread();
                    System.err.println("inside finally:\n" + " currThread=" + currThread + "\n" + " currThread.isAlive()=" + currThread.isAlive());
                }
            }
        };
        
        Thread t = new Thread(r);
        t.start();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        
        System.err.println("about to interrupt thread");
        t.interrupt();
        System.err.println("just interrupted thread");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        
        System.err.println("about to stop thread");
        // stop() is being used here to show that the extreme action of stopping a thread is also ineffective
        // Because stop() is deprecated, the compiler issues a warning
        t.stop();
        System.err.println("just stopped thread,t.isAlive()=" + t.isAlive());
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        
        System.err.println("t.isAlive()=" + t.isAlive());
        System.err.println("leaving main()");
    }
}