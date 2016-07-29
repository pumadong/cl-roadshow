package com.cl.roadshow.java.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * 在Nio中，Buffer类，特别是ByteBuffer非常重要，Buffer是Nio中与通道通讯的唯一方式
 * http://my.oschina.net/flashsword/blog/159613
 * http://ifeve.com/buffers/
 * @author dongyongjin
 *
 */
public class ByteBufferDemo {
	
	public static void main(String[] args) throws IOException {
		
		channelShow();
		
		String sendString="你好,服务器!";
	    ByteBuffer sendBuffer = ByteBuffer.wrap(sendString.getBytes("UTF-8"));
	    Charset cs = Charset.forName ("UTF-8");
	    System.out.println(cs.decode(sendBuffer).toString());
	}

	public static void channelShow() throws IOException {
		RandomAccessFile aFile = new RandomAccessFile(System.getProperty("user.dir") + "/src/main/java/com/cl/roadshow/java/nio/ByteBufferDemo.java", "rw");
		FileChannel inChannel = aFile.getChannel();

		//create buffer with capacity of 48 bytes
		ByteBuffer buf = ByteBuffer.allocate(48);

		int bytesRead = inChannel.read(buf); //read into buffer.
		while (bytesRead != -1) {

		  buf.flip();  //make buffer ready for read

		  while(buf.hasRemaining()){
		      System.out.print((char) buf.get()); // read 1 byte at a time
		  }

		  buf.clear(); //make buffer ready for writing
		  bytesRead = inChannel.read(buf);
		}
		aFile.close();
	}
}
