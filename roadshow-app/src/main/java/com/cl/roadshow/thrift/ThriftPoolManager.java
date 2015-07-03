package com.cl.roadshow.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TTransport;

/**
 * Thrift连接池使用管理器，这个包演示的是一个Thrift连接池的实现（基于commons-pool)
 * 
 * 另附上一个相关的帖子，关于数据库连接池的：http://www.cnblogs.com/tony-law/archive/2012/08/27/2658628.html
 * 
 */
public class ThriftPoolManager {
    
    private static ThriftPool pool = null;
    
    public static TTestService.Client getClient(TTransport transport){
        return new TTestService.Client(new TBinaryProtocol(transport));
    }
    
    public static void initPool() throws Exception{
        
        ThriftPoolConfig config = new ThriftPoolConfig();

        // 如果机器存在，但是端口不提供服务，则很快返回，报如下错误：
        // org.apache.thrift.transport.TTransportException: java.net.ConnectException: Connection refused
        // 如果机器不存在，则很长时间返回，报如下错误：
        // org.apache.thrift.transport.TTransportException: java.net.SocketTimeoutException: connect timed out
        // 所以这里需要一台能连接的上的服务器
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
