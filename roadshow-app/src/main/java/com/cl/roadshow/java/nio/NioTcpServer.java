package com.cl.roadshow.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * nio 基本演示
 * 
 * 理解几个思想
 * 
 * 1、每个channel只对应一个SelectionKey，在register时产生，所以，只能改变interestOps()，不能重复register()
 * 2、任何复杂一点的通讯应用，都要定协议，比如：我本次发送数据长度多长，哪几位是长度标记，这样接收方才好根据协议进行数据处理
 * 3、以socket来说，nio编程模型，比io模型编程，复杂很多，但带来的好处是单台机器可以支持更多的客户端连接，这是因为：
 *    a、相对于阻塞io编程模型，服务器不用每来一个连接就开一个线程处理，不会受制于服务器可以开的线程数；
 *    b、如果说我在服务器端用线程池，任务过多进入队列来提高性能呢？想一想，这还是阻塞io吗？
 *    c、使用基础的非阻塞io进行通讯，对于上层应用来说，我可以同步返回给调用者，也可以异步返回给调用者；所以阻塞非阻塞和同步异步，是两个维度的东西。
 * 
 * http://blog.csdn.net/shirdrn/article/details/6263692
 */
public class NioTcpServer extends Thread {

    private static final Logger log = LoggerFactory.getLogger("console");
    private InetSocketAddress inetSocketAddress;
    private Handler handler = new ServerHandler();
    
    public NioTcpServer(String hostname, int port) {
        inetSocketAddress = new InetSocketAddress(hostname, port);
    }
    
    @Override
    public void run() {
        try {
            // 打开选择器
            Selector selector = Selector.open(); 
            // 打开通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            // 非阻塞
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(inetSocketAddress);
            // 向通道注册选择器和对应事件标识
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); 
            log.info("Server: socket server started.");
            // 轮询
            while(true) {
                int nKeys = selector.select();
                if(nKeys>0) {
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> it = selectedKeys.iterator();
                    while(it.hasNext()) {
                        SelectionKey key = it.next();
                        if(key.isAcceptable()) {
                            log.info("Server: SelectionKey is acceptable.");
                            handler.handleAccept(key);
                        } else if(key.isReadable()) {
                            log.info("Server: SelectionKey is readable.");
                            handler.handleRead(key);
                        } else if(key.isWritable()) {
                            log.info("Server: SelectionKey is writable.");
                            handler.handleWrite(key);
                        }
                        it.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 简单处理器接口
     * 
     * @author shirdrn
     */
    interface Handler {
        /**
         * 处理{@link SelectionKey#OP_ACCEPT}事件
         * @param key 
         * @throws IOException
         */
        void handleAccept(SelectionKey key) throws IOException;
        /**
         * 处理{@link SelectionKey#OP_READ}事件
         * @param key 
         * @throws IOException
         */
        void handleRead(SelectionKey key) throws IOException;
        /**
         * 处理{@link SelectionKey#OP_WRITE}事件
         * @param key 
         * @throws IOException
         */
        void handleWrite(SelectionKey key) throws IOException;
    }
    
    /**
     * 服务端事件处理实现类
     * 
     * @author shirdrn
     */
    class ServerHandler implements Handler {

        @Override
        public void handleAccept(SelectionKey key) throws IOException {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            log.info("Server: accept client socket " + socketChannel);
            socketChannel.configureBlocking(false);
            socketChannel.register(key.selector(), SelectionKey.OP_READ);
        }

        @Override
        public void handleRead(SelectionKey key) throws IOException {
            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
            SocketChannel socketChannel = (SocketChannel)key.channel();
            while(true) {
                int readBytes = socketChannel.read(byteBuffer);
                if(readBytes>0) {
                    log.info("Server: readBytes = " + readBytes);
                    log.info("Server: data = " + new String(byteBuffer.array(), 0, readBytes));
                    byteBuffer.flip();
                    socketChannel.write(byteBuffer);
                    break;
                }
            }
            socketChannel.close();
        }

        @Override
        public void handleWrite(SelectionKey key) throws IOException {
            ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
            byteBuffer.flip();
            SocketChannel socketChannel = (SocketChannel)key.channel();
            socketChannel.write(byteBuffer);
            if(byteBuffer.hasRemaining()) {
                key.interestOps(SelectionKey.OP_READ);
            }
            byteBuffer.compact();
        }
    }

    public static void main(String[] args) {
        NioTcpServer server = new NioTcpServer("localhost", 10099);
        server.start();
    }
}