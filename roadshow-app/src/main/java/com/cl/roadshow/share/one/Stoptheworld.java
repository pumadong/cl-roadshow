package com.cl.roadshow.share.one;

import java.util.ArrayList;
import java.util.List;

/** 
 * java -server -Xms1000m -Xmx1000m -XX:+UseConcMarkSweepGC Stoptheworld 
 * 可以看到，每次垃圾回收（Young GC），都是Stop the world，导致方法的执行时间 > 15毫秒，增加的时间=Young GC耗费的时间 
 * 监控生产的1台机器CSC SERVICE，JVM运行14个小时，共计400次Young GC，平均每次50毫秒
 */  
public class Stoptheworld {  
    public static void main(String[] args) throws Exception {  
          
        final List<Object> os = new ArrayList<Object>();  
          
        new Thread(new Runnable() {  
            @Override  
            public void run() {  
                int i = 0;  
                while(true) {  
                    for(int j=0;j<1000;j++)  
                        os.add(new java.util.concurrent.ConcurrentHashMap<String, String>());  
                    try {  
                        Thread.sleep(10);  
                    } catch (InterruptedException e) {  
                    }  
                    i++;  
                    // 试试50、100、300
                    if(i == 50) {
                        os.clear();  
                        i = 0;  
                    }  
                }  
                  
            }  
        }).start();  
        
        int i=0;
        while(true) {  
            long start = System.currentTimeMillis();  
            Thread.sleep(15);  
            long m = System.currentTimeMillis() - start;  
            if(m>20)  
                System.out.println("第" + (++i) + "次超过20毫秒，耗时：" + m);  
        }  
    }  
}  