package com.cl.roadshow.java.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * SocketServer演示
 * 
 *
 */
public class SocketServer {
    public static void main(String[] args) {
        SocketServer ss = new SocketServer();
    
        //设定服务端的端口号  
        ServerSocket s = null;
        try {
            s = new ServerSocket(10099);
            System.out.println("ServerSocket Start:"+s); 
            while(true)
            {
                //这个地方是阻塞的
                Socket socket = s.accept(); 
                System.out.println("Server_Accept:"+socket);  
                SocketServer.HandleSocket vsm = ss.new HandleSocket(socket);
                new Thread(vsm).start();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }  finally
        {
            try {
                s.close();
            } catch (IOException e) {
            }
        }
    }
    
    class HandleSocket implements Runnable
    {
        private Socket socket;
        
        public HandleSocket(Socket socket)
        {
                this.socket = socket;
        }
        @Override
        public void run() {         
            BufferedReader br = null;  
            PrintWriter pw = null;
            try {
                //用于接收客户端发来的请求  
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
                //用于发送返回信息,可以不需要装饰这么多io流使用缓冲流时发送数据要注意调用.flush()方法  
                pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true); 
                String str = br.readLine(); 
                if(str != null)
                {
                    pw.println("OK_"+Thread.currentThread().getName());  
                    pw.flush();
                    System.out.println("Command:"+str);
                }   
            } catch (Exception e) {  
                e.printStackTrace();  
            }finally{  
                System.out.println("Server_Close:"+socket);  
                try {  
                    br.close();  
                    pw.close();  
                    socket.close();  
                } catch (Exception e2) {                      
                }  
            }
        }
    }
}
