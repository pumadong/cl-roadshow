package com.cl.roadshow.thrift;

import org.apache.thrift.transport.TTransport;

/**
 * Thrift调用测试
 * 
 */
public class ThriftTest {
    public static void main(String[] args) throws Exception {
        TTransport transport = ThriftPoolManager.getTransport();
        if(transport.isOpen()){
            TTestService.Client client = ThriftPoolManager.getClient(transport);
            try {
                String s = client.getInfoFromServer();
                System.out.println(s);
//            } catch (TException e) {
//                e.printStackTrace();
            }finally{
                ThriftPoolManager.closeTransport(transport);
            }
        }else{
            System.out.println("网络故障");
            ThriftPoolManager.closeTransport(transport);
        }

    }
}
