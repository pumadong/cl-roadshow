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

public class MultiPortEchoServer {
    private static final Logger log = LoggerFactory.getLogger("console");

    private int ports[];
    private ByteBuffer echoBuffer = ByteBuffer.allocate(1024);

    public MultiPortEchoServer(int ports[]) throws IOException {
        this.ports = ports;
        go();
    }

    @SuppressWarnings("unused")
    private void go() throws IOException {
        // Create a new selector
        Selector selector = Selector.open();

        // Open a listener on each port, and register each one
        // with the selector
        for (int i = 0; i < ports.length; ++i) {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ServerSocket ss = ssc.socket();
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            ss.bind(address);

            SelectionKey key = ssc.register(selector, SelectionKey.OP_ACCEPT);

            log.info("Going to listen on " + ports[i]);
        }

        while (true) {
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectedKeys.iterator();

            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();

                if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
                    // Accept the new connection
                    ServerSocketChannel ssc = (ServerSocketChannel) key
                            .channel();
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);

                    // Add the new connection to the selector
                    SelectionKey newKey = sc.register(selector,
                            SelectionKey.OP_READ);
                    it.remove();

                    log.info("Got connection from " + sc);
                } else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
                    // Read the data
                    SocketChannel sc = (SocketChannel) key.channel();

                    // Echo data
                    int bytesEchoed = 0;
                    while (true) {
                        echoBuffer.clear();

                        int r = sc.read(echoBuffer);

                        if (r <= 0) {
                            break;
                        }

                        echoBuffer.flip();

                        sc.write(echoBuffer);
                        bytesEchoed += r;
                    }

                    log.info("Echoed " + bytesEchoed + " from " + sc);

                    it.remove();
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
