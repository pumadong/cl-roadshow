package com.cl.roadshow.java.nio;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * nio 基本演示
 * 
 * 以下链接的文章可以看，但是直接从网站下代码的话有一些错误，得注意
 * 
 * http://www.ibm.com/developerworks/cn/education/java/j-nio/j-nio.html
 * http://www.ibm.com/developerworks/cn/java/j-nio2-1/
 */
public class MultiPortEchoServer {
    private static final Logger log = LoggerFactory.getLogger("console");

    private int ports[];
    private ByteBuffer echoBuffer = ByteBuffer.allocate(1024);

    public MultiPortEchoServer(int ports[]) throws IOException {
        this.ports = ports;
        go();
    }

    private void go() throws IOException {
        // Create a new selector
        Selector selector = Selector.open();
        // Open a listener on each port, and register each one
        // with the selector
        for (int i = 0; i < ports.length; ++i) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(ports[i]));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            log.info("Server: Going to listen on " + ports[i]);
        }

        while (true) {
            // 1、这个select()操作是必须的，否则selectedKeys拿不到值;
            // 2、如果read之后,SocketChannel不close的话，这里将一直拿到值
            int nKeys = selector.select();
            log.info("Server: Got nKeys  " + nKeys);

            if (nKeys > 0) {
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectedKeys.iterator();

                while (it.hasNext()) {
                    SelectionKey key = (SelectionKey) it.next();
                    log.info("Server: SelectionKey is  " + JSON.toJSONString(key));
                    if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
                        // Accept the new connection
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key
                                .channel();
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        // Add the new connection to the selector
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        it.remove();
                        log.info("Server: Got connection from " + socketChannel);
                    } else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
                        // Read the data
                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        // Echo data
                        int bytesEchoed = 0;
                        while (true) {
                            echoBuffer.clear();
                            int r = socketChannel.read(echoBuffer);
                            if (r <= 0) {
                                break;
                            }
                            echoBuffer.flip();
                            socketChannel.write(echoBuffer);
                            bytesEchoed += r;
                        }
                        log.info("Server: Echoed " + bytesEchoed + " from "
                                + socketChannel);
                        it.remove();
                        socketChannel.close();
                    }
                }
            }
        }
    }

    static public void main(String args[]) throws Exception {
        // if (args.length<=0) {
        // System.err.println( "Usage: java MultiPortEcho port [port port ...]"
        // );
        // System.exit( 1 );
        // }

        args = new String[] { "10099", "10100" };
        int ports[] = new int[args.length];

        for (int i = 0; i < args.length; ++i) {
            ports[i] = Integer.parseInt(args[i]);
        }

        new MultiPortEchoServer(ports);
    }
}
