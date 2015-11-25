package com.cl.roadshow.java.jtp.blockedio;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 演示"Closing a Stream to Break Out of the Blocked State"，代码示例来源于：《Java Thread Programming》 第 15 章
 * 
 */
@SuppressWarnings(value={"rawtypes","unchecked"})
public class CalcServer extends Object {

    private ServerSocket ss;
    private List workerList;
    private Thread internalThread;
    private volatile boolean noStopRequested;

    public CalcServer(int port) throws IOException {
        ss = new ServerSocket(port);
        workerList = new LinkedList();
        noStopRequested = true;
        Runnable r = new Runnable() {
            public void run() {
                try {
                    runWork();
                } catch (Exception x) {
                    // in case ANY exception slips throuth
                    x.printStackTrace();
                }
            }
        };
        internalThread = new Thread(r);
        internalThread.start();
    }

    private void runWork() {
        System.out.println("in CalcServer - ready to accept connections");
        while (noStopRequested) {
            try {
                System.out.println("in CalcServer - about to block waiting for a new connection");
                Socket sock = ss.accept();
                System.out.println("in CalcServer - received new connection");
                workerList.add(new CalcWorker(sock));
            } catch (IOException iox) {
                if (noStopRequested) {
                    iox.printStackTrace();
                }

            }
        }
        // stop all the workers that were created
        System.out.println("in CalcServer - putting in a stop request to all the workers");
        Iterator iter = workerList.iterator();
        while (iter.hasNext()) {
            CalcWorker worker = (CalcWorker) iter.next();
            worker.stopRequest();
        }

        System.out.println("in CalcServer - leaving runWork()");
    }

    public void stopRequest() {
        System.out.println("in CalcServer - entering stopRequest()");
        noStopRequested = false;
        internalThread.interrupt();
        if (ss != null) {
            try {
                ss.close();
            } catch (IOException x) {
                // ignore
            } finally {
                ss = null;
            }
        }
    }

    public boolean isAlive() {
        return internalThread.isAlive();
    }

    public static void main(String[] args) {
        int port = 2001;
        try {
            CalcServer server = new CalcServer(port);
            Thread.sleep(15000);
            server.stopRequest();
        } catch (IOException x) {
            x.printStackTrace();
        } catch (InterruptedException x) {
            // ignore
        }
    }
}