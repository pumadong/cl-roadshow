package com.cl.roadshow.java.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * SocketClient演示
 * 
 *
 */
public class SocketClient {
	public static void main(String[] args) throws InterruptedException {
		for(int i=0;i<10;i++) {
			call( "Hello" + i);
		}
	}
	public static void call(String command) {
	    Socket socket = null;  
	    BufferedReader br = null;  
	    PrintWriter pw = null;
	    try {  
	        //客户端socket指定服务器的地址和端口号  
	        socket = new Socket("127.0.0.1", 10099);  
	        System.out.println("Client_Open" + socket + " Command:" + command);  
	        //同服务器原理一样  
	        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
	        pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));  
	        pw.println(command);  
	        pw.flush(); 
	        String str = br.readLine(); 
	        System.out.println("Client_Receive:"+str);
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    } finally {  
	        try {  
	            System.out.println("Client_Close："+socket);  
	            br.close();  
	            pw.close();  
	            socket.close();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }
	}
}
