package com.cl.roadshow.java.jtp.blockedio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * 演示"Closing a Stream to Break Out of the Blocked State"，代码示例来源于：《Java Thread Programming》 第 15 章
 * 
 */
public class CalcClient extends Object {
    @SuppressWarnings("resource")
    public static void main(String[] args) {

        String hostname = "localhost";
        int port = 2001;

        try {
            Socket sock = new Socket(hostname, port);

            DataInputStream in = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));

            double val = 9.0;
            out.writeDouble(val);
            out.flush();
            double sqrt = in.readDouble();
            System.out.println("sent up " + val + ", got back " + sqrt);
            // Don’t ever send another request, but stay
            // this eternally blocked state.
            Object lock = new Object();
            while (true) {
                synchronized (lock) {
                    lock.wait();
                }
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}