package com.cl.roadshow.thrift.pool;

import org.apache.thrift.protocol.TBinaryProtocol;

/**
 * 写一个类来模拟Thrift自动生成的类，演示Thrift的客户端调用过程
 * 
 */
public class TTestService {

    public static class Client {
        public Client(TBinaryProtocol t) {}
        public String getInfoFromServer() {
            return "ok";
        }
    }
}
