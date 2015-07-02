package com.cl.roadshow.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TTransport;

/**
 * 线程池使用管理器
 * 
 */
public class ThriftPoolManager {
    
    private static ThriftPool pool = null;
    
    public static TTestService.Client getClient(TTransport transport){
        return new TTestService.Client(new TBinaryProtocol(transport));
    }
    
    public static void initPool() throws Exception{
        
        ThriftPoolConfig config = new ThriftPoolConfig();

        //如果机器连不上，这里就报出异常，如下：
        //org.apache.thrift.transport.TTransportException: java.net.ConnectException: Connection refused
        //所以这里需要一台能连接的上的服务器
        pool = new ThriftPool(config,"192.168.1.100",8410,30000);
    }
    
    
    public static ThriftPool getThriftPool(){
        return pool;
    }
    
    public static TTransport getTransport(){
        try {
            if(pool == null){
                initPool();
            }
            return pool.getResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void closeTransport(TTransport t) {
        try {
            pool.returnResource(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int activeConnections(){
        return pool.currentActives();
    }
    
    public static void main(String[] args){
        Object o = getTransport();
        System.out.println(o);
    }
}
