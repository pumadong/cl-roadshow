package com.cl.roadshow.java.nio;

/**
 * nio 基本演示
 * 
 * http://www.ibm.com/developerworks/cn/education/java/j-nio/j-nio.html
 * http://www.ibm.com/developerworks/cn/java/j-nio2-1/
 */
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsynchronousSocketChannelExample {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        new AsynchronousSocketChannelExample();
    }
    
    public AsynchronousSocketChannelExample() throws IOException, InterruptedException, ExecutionException {
        // open a server channel and bind to a free address, then accept a connection
        System.out.println("Open server channel");
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open().bind(null);
        System.out.println("Initiate accept");
        Future<AsynchronousSocketChannel> future = server.accept();
        
        // create a client
        Client client = new Client(server.getLocalAddress());
        
        // wait for the accept to finish
        AsynchronousSocketChannel worker = future.get();
        System.out.println("Accept completed");
        
        // start client thread
        client.start();
        ByteBuffer readBuffer = ByteBuffer.allocate(100);
        try {
            // read a message from the client, timeout after 10 seconds
            worker.read(readBuffer).get(10, TimeUnit.SECONDS);
            System.out.println("Message received from client: " + new String(readBuffer.array()));
        } catch (TimeoutException e) {
            System.out.println("Client didn't respond in time");
        }
        
        client.join();
        client.close();
        server.close();
    }
}

class Client extends Thread {
    AsynchronousSocketChannel client;
    Future<Void> connectFuture;
    
    public Client(SocketAddress server) throws IOException {
        // open a new socket channel and connect to the server
        System.out.println("Open client channel");
        client = AsynchronousSocketChannel.open();
        System.out.println("Connect to server");
        connectFuture = client.connect(server);
    }
    
    public void run() {
        // if the connect hasn't happened yet cancel it
        if (!connectFuture.isDone()) {
            connectFuture.cancel(true);
            return;
        }
        try {
            // send a message to the server
            ByteBuffer message = ByteBuffer.wrap("ping".getBytes());
            // wait for the response
            System.out.println("Sending message to the server...");
            @SuppressWarnings("unused")
            int numberBytes = client.write(message).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    
    public void close() throws IOException {
        client.close();
    }
}