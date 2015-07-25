package com.cl.roadshow.thrift.demo;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thrift Client
 */
public class Client {
    
    private static final Logger log = LoggerFactory.getLogger("console");

    public static final String SERVER_IP = "127.0.0.1";
    public static final int SERVER_PORT = 9001;
    public static final int TIMEOUT = 30000;
    public static TTransport transport = null;

    public static StudentService.Client client;

    public static void main(String[] args){
        initClient();
        
        
        try {
            client.getStudentResponse();
        } catch (TException e) {
            log.error("client.getStudentResponse() error ", e);
        }
        
        
        try {
            client.getStudentResponseRequired();
        } catch (TException e) {
            log.error("client.getStudentResponseRequired() error ", e);
        }
        
        destroyClient();
    }
    
    public static void initClient() {
        try {
            transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);

            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);

            client = new StudentService.Client(protocol);


        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void destroyClient(){
        if (null != transport)
            transport.close();
    }

}