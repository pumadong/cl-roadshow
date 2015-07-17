package com.cl.roadshow.java.nio;

/**
 * nio 基本演示
 * 
 * http://www.ibm.com/developerworks/cn/education/java/j-nio/j-nio.html
 * http://www.ibm.com/developerworks/cn/java/j-nio2-1/
 */
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiPortEchoClient
{
    private static final Logger log = LoggerFactory.getLogger("console");
    
    static public void main( String args[] ) throws Exception {
      // 打开监听信道并设置为非阻塞模式  
      SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 10099));  
      socketChannel.configureBlocking(false);  
      
      ByteBuffer byteBuffer = ByteBuffer.allocate(512);
      socketChannel.write(ByteBuffer.wrap("Actions speak louder than words!".getBytes()));
      while (true) {
          byteBuffer.clear();
          int readBytes = socketChannel.read(byteBuffer);
          if (readBytes > 0) {
              byteBuffer.flip();
              log.info("Client: readBytes = " + readBytes);
              log.info("Client: data = " + new String(byteBuffer.array(), 0, readBytes));
              socketChannel.close();
              break;
          }
      }
      
      // 客户端也可以异步IO，比如：
      // http://blog.csdn.net/csh159/article/details/7999893
  }
}
