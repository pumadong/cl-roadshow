package com.cl.roadshow.thrift;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool.Config;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Thrift线程池
 * 
 */
public class ThriftPool extends BasePool<TTransport>{

    public ThriftPool(String host, int port, int timeout) {
        this(new Config(), host,port,timeout);
    }
    
    public ThriftPool(final Config poolConfig,String host, int port, int timeout) {
        super(poolConfig, new ThriftFactory(host,port,timeout));
    }

    private static class ThriftFactory extends BasePoolableObjectFactory<TTransport>{

        private String host;
        private int port;
        private int timeout;
        
        public ThriftFactory(String host, int port, int timeout){
            super();
            this.host = host;
            this.port = port;
            this.timeout = timeout;
        }

        @Override
        public TTransport makeObject() throws Exception {
            //特别注意：这个协议必须和ThriftServer的一致
            TTransport t = new TFramedTransport(new TSocket(this.host, this.port, this.timeout ));
            t.open();
            return t.isOpen() ? t : null;
        }
        
        @Override
        public void destroyObject(TTransport t){
            if(t.isOpen()){
                t.close();
            }
        }
        @Override
         public boolean validateObject(TTransport t) {
            return t.isOpen() && t.peek();
        }
        
    }
}