package com.cl.roadshow.java.nio;

/**
 * nio 基本演示
 * 
 * http://m.blog.csdn.net/blog/shirdrn_11109/6263692
 */
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NioTcpClient {

    private static final Logger log = LoggerFactory.getLogger("console");
    private InetSocketAddress inetSocketAddress;
    
    public NioTcpClient(String hostname, int port) {
        inetSocketAddress = new InetSocketAddress(hostname, port);
    }
    
    /**
     * 发送请求数据
     * @param requestData
     */
    public void send(String requestData) {
        try {
            SocketChannel socketChannel = SocketChannel.open(inetSocketAddress);
            socketChannel.configureBlocking(false);
            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
            socketChannel.write(ByteBuffer.wrap(requestData.getBytes()));
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        String hostname = "localhost";
        String requestData = "Actions speak louder than words!";
        int port = 10099;
        new NioTcpClient(hostname, port).send(requestData);
    }
}