package com.cl.roadshow.java.nio;

import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * nio 基本演示
 * 
 * 以下链接的文章可以看，但是直接从网站下代码的话有一些错误，得注意
 * 
 * http://www.ibm.com/developerworks/cn/education/java/j-nio/j-nio.html
 * http://www.ibm.com/developerworks/cn/java/j-nio2-1/
 */
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
