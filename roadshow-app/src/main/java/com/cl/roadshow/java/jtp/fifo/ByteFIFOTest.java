package com.cl.roadshow.java.jtp.fifo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/**
 * 演示线程安全的FIFO，代码示例来源于：《Java Thread Programming》 第 18 章
 * 
 * 我们可以看到，JDK 5 才开始的BlockingQueue，和这个原理十分相似
 *
 */
public class ByteFIFOTest {
    
    private ByteFIFO fifo;
    private byte[] srcData;
    
    public ByteFIFOTest() throws IOException {
        fifo = new ByteFIFO(20);
        makeSrcData();
        print("srcData.length=" + srcData.length);
        Runnable srcRunnable = new Runnable() {
            public void run() {
                src();
            }
        };
        Thread srcThread = new Thread(srcRunnable);
        srcThread.setName("src");
        srcThread.start();
        
        Runnable dstRunnable = new Runnable() {
          public void run() {
              dst();
          }  
        };
        Thread dstThread = new Thread(dstRunnable);
        dstThread.setName("dst");
        dstThread.start();
        
        //演示一个System.arrayCopy
        try {
            Thread.sleep(1*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] s1 = {"a","b","c","d","e"};
        String[] s2 = new String[3];
        System.arraycopy(s1, 0, s2, 0, 3);
        System.arraycopy(s1, 1, s2, 0, 3);
        System.out.println("\n\n" + Thread.currentThread().getName() + ":" + Arrays.toString(s2));
    }
    
    private static synchronized void print(String msg) {
        System.out.println(Thread.currentThread().getName() + ": " + msg);
    }
    
    private void makeSrcData() throws IOException {
        String[] list = {"The first string is right here",
                "The second string is a bit longer and also right here",
                "The third string",
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
                "0123456789",
                "The last string in the list"
        };
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(list);
        oos.flush();
        oos.close();
        srcData = baos.toByteArray();
     }
    
    private void src() {
        try {
            boolean justAddOne = true;
            int count = 0;
            while(count < srcData.length) {
                if( !justAddOne) {
                    int writeSize = (int)(40.0 * Math.random());
                    writeSize = Math.min(writeSize, srcData.length - count);
                    byte[] buf = new byte[writeSize];
                    System.arraycopy(srcData, count, buf, 0, writeSize);
                    fifo.add(buf);
                    count += writeSize;
                    print("just added " + writeSize + " bytes");
                } else {
                    fifo.add(srcData[count]);
                    count++;
                    print("just added exactly 1 byte");
                }
                justAddOne = !justAddOne;
            }
        } catch(InterruptedException x) {
            x.printStackTrace();
        }
    }
    
    private void dst() {
        try {
            boolean justAddOne = true;
            int count = 0;
            byte[] dstData = new byte[srcData.length];
            while(count < dstData.length) {
                if(!justAddOne)  {
                    byte[] buf = fifo.removeAll();
                    
                    if(buf.length > 0){
                        System.arraycopy(buf, 0, dstData, count, buf.length);
                        count += buf.length;
                    }
                    print("just removed " + buf.length + " bytes");
                } else {
                    byte b = fifo.remove();
                    dstData[count] = b;
                    count++;
                    print("just removed exactly 1 byte");
                }
                justAddOne = !justAddOne;
            }
            print("received all data, count=" + count);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(dstData));
            String[] line = (String[])ois.readObject();
            for(int i = 0; i < line.length; i++) {
                print("line[" + i + "]=" + line[i]);
            }
        } catch(ClassNotFoundException x1) {
            x1.printStackTrace();
        } catch(IOException iox) {
            iox.printStackTrace();
        } catch(InterruptedException x) {
            x.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        try {
            new ByteFIFOTest();
        } catch(IOException iox) {
            iox.printStackTrace();
        }
    }
}
