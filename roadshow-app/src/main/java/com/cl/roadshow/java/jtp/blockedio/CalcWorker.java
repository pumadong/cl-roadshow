package com.cl.roadshow.java.jtp.blockedio;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 演示"Closing a Stream to Break Out of the Blocked State"，代码示例来源于：《Java Thread Programming》 第 15 章
 * 
 */
public class CalcWorker extends Object {

    private InputStream sockIn;
    private OutputStream sockOut;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private Thread internalThread;
    private volatile boolean noStopRequested;

    public CalcWorker(Socket sock) throws IOException {
        sockIn = sock.getInputStream();
        sockOut = sock.getOutputStream();
        dataIn = new DataInputStream(new BufferedInputStream(sockIn));
        dataOut = new DataOutputStream(new BufferedOutputStream(sockOut));
        noStopRequested = true;
        Runnable r = new Runnable() {
            public void run() {
                try {
                    runWork();
                } catch (Exception x) {
                    // in case ANY exception slips through

                    x.printStackTrace();
                }
            }
        };
        internalThread = new Thread(r);
        internalThread.start();
    }

    private void runWork() {
        while (noStopRequested) {
            try {
                System.out.println("in CalcWorker - about to block waiting to read a double");
                double val = dataIn.readDouble();
                System.out.println("in CalcWorker - read a double!");
                dataOut.writeDouble(Math.sqrt(val));
                dataOut.flush();
            } catch (IOException x) {
                if (noStopRequested) {
                    x.printStackTrace();
                    stopRequest();
                }
            }
        }
        // In real-world code, be sure to close other streams
        // the socket as part of the clean-up. Omitted here
        // brevity.

        System.out.println("in CalcWorker - leaving runWork()");
    }

    public void stopRequest() {
        System.out.println("in CalcWorker - entering stopRequest()");
        noStopRequested = false;
        internalThread.interrupt();
        if (sockIn != null) {
            try {
                sockIn.close();
            } catch (IOException iox) {
                // ignore
            } finally {
                sockIn = null;
            }

        }
        System.out.println("in CalcWorker - leaving stopRequest()");
    }

    public boolean isAlive() {
        return internalThread.isAlive();
    }
}