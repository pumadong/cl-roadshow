package com.cl.roadshow.java.lang.thread;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.alibaba.fastjson.JSON;



/**
 *  线程池子
 * 
 */
public class PoolingHttpClientConnectionManagerDemo {

    private static RequestConfig defaultConfig;
    private static PoolingHttpClientConnectionManager cm;
    private static CloseableHttpClient threadSafeClient;
    private static HttpPost httpPost = new HttpPost("http://123.sankuai.com/index.php");
    
    public static void main(String[] args) throws ClientProtocolException, IOException {
        
        defaultConfig = RequestConfig.custom()
                .setConnectTimeout(200) // http.connection.timeout
                .setConnectionRequestTimeout(100) // http.connection-manager.timeout
                .setSocketTimeout(200).build(); // http.socket.timeout
        cm = new PoolingHttpClientConnectionManager();
        // Increase max total connection to 10240
        cm.setMaxTotal(1024);
        // Increase default max connection per route to 300
        cm.setDefaultMaxPerRoute(30);

        // Build the client.
        threadSafeClient = HttpClients.custom()
            .setDefaultRequestConfig(defaultConfig)
            .setConnectionManager(cm)
            .build();
        
        WorkThreadManager workThreadManager = new WorkThreadManager();
        workThreadManager.start();
        
        MonitorThread monitorThread = new MonitorThread();
        monitorThread.start();
    }
    
    static class WorkThreadManager extends Thread {
        @Override
        public void run() {
            while(true) {
                for(int i=0; i<20; i++) {
                    WorkThread workThread = new WorkThread();
                    workThread.start();
                }
                try {
                    Thread.sleep(3*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    static class WorkThread extends Thread {
        @Override
        public void run() {
            CloseableHttpResponse response = null;
            try {
                response = threadSafeClient.execute(httpPost);
                System.out.println(Thread.currentThread().getName() + ":" + response.getStatusLine().getStatusCode());
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //如果不释放链接，很快就会到达setDefaultMaxPerRoute
                
                //第一种办法
                try {
                    if(response!=null) {response.close();}
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                //第二种办法
                //httpPost.reset();
                //httpPost.releaseConnection();
                
                System.out.println(Thread.currentThread().getName() + ":" + "finally");
            }
        }
    }

    static class MonitorThread extends Thread {
        @Override
        public void run() {
            while(true) {
                cm.closeExpiredConnections();
                cm.closeIdleConnections(100, TimeUnit.SECONDS);
                System.out.println("===========================");
                System.out.println(JSON.toJSONString(cm.getTotalStats()));
                System.out.println(JSON.toJSONString(cm));
                System.out.println("===========================");
                try {
                    Thread.sleep(1*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
