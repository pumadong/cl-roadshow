package com.cl.roadshow.java.jtp.httpserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URLConnection;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import com.cl.roadshow.java.jtp.fifo.ObjectFIFO;

/**
 * 演示线程池的实现，代码示例来源于：《Java Thread Programming》第13章
 * 
 * 这是一个简单的WebServer实现，可以帮助我们理解WebServer的实现思路及加深对Http协议的理解
 * 
 */
public class HttpWorker {
    private static int nextWorkerID = 0;
    private File docRoot;
    // 空闲线程队列
    private ObjectFIFO idleWorkers;
    private int workerID;
    // 将要被处理的任务队列，如果没有任何，就阻塞
    private  ObjectFIFO handoffBox;
    private Thread internalThread;
    private volatile boolean noStopRequested;
    
    public HttpWorker(File docRoot,int workerPriority,ObjectFIFO idleWorkers) {
        this.docRoot = docRoot;
        this.idleWorkers = idleWorkers;
        
        workerID = getNextWorkerID();
        // Only one slot
        handoffBox = new ObjectFIFO(1);
        // Just before returning, the thread should be created and started
        noStopRequested = true;
        Runnable r = new Runnable() {
            public void run() {
                try {
                    runWork();
                } catch(Exception x) {
                    // in case ANY exception slips through
                    x.printStackTrace();
                }
            }
        };
        internalThread = new Thread(r);
        internalThread.setPriority(workerPriority);
        internalThread.start();
    }
    
    public static synchronized int getNextWorkerID() {
        // synchronized at the class level to ensure uniqueness
        int id = nextWorkerID;
        nextWorkerID++;
        return id;
    }
    
    public void processRequest(Socket s) throws InterruptedException {
        handoffBox.add(s);
    }
    
    private void runWork() {
        Socket s = null;
        InputStream in = null;
        OutputStream out = null;
        while(noStopRequested) {
            try {
                // Worker is ready to receive new service requests, so it adds itself to the idle worker queue
                idleWorkers.add(this);
                // Wait here until the server puts a request into the handoff box
                s = (Socket)handoffBox.remove();
                in = s.getInputStream();
                out = s.getOutputStream();
                generateResponse(in,out);
                out.flush();
            } catch(IOException iox) {
                System.err.println("I/O error while processing request, " + "ignoring and adding back to idle " + "queue - workerID=" + workerID);
            } catch(InterruptedException x) {
                // re-assert the interrupt
                Thread.currentThread().interrupt();
            } finally {
                // Try to close everything, ignoring any IOExceptions that might occur
                if(in != null) {
                    try {
                        in.close();
                    } catch (IOException iox) {
                        // ignore
                    } finally {
                        in = null;
                    }
                }
                if(out != null) {
                    try {
                        out.close();
                    } catch (IOException iox) {
                        // ignore
                    } finally {
                        out = null;
                    }
                }
                if(s != null) {
                    try {
                        s.close();
                    } catch (IOException iox) {
                        // ignore
                    } finally {
                        s = null;
                    }
                }
            }
        }
    }
    
    private void generateResponse(InputStream in, OutputStream out) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String requestLine = reader.readLine();
        if((requestLine == null || (requestLine.length() < 1))) {
            throw new IOException("could not read request");
        }
        System.out.println("workerID=" + workerID + ", requestLine=" + requestLine);
        StringTokenizer st = new StringTokenizer(requestLine);
        String filename = null;
        try {
            // request method, typically 'GET', but ignored
            st.nextToken();
            // the second token should be the filename
            filename = st.nextToken();
        } catch (NoSuchElementException x) {
            throw new IOException("could not parse request line");
        }
        File requestedFile = generateFile(filename);
        BufferedOutputStream buffOut = new BufferedOutputStream(out);
        if(requestedFile.exists()) {
            System.out.println("workerID=" + workerID + ", 200 OK: " + filename);
            int fileLen = (int)requestedFile.length();
            BufferedInputStream fileIn = new BufferedInputStream(new FileInputStream(requestedFile));
            // Use this utility to make a guess about the content type based on the first few bytes in the stream
            String contentType = URLConnection.guessContentTypeFromStream(fileIn);
            byte[] headerBytes = createHeaderBytes("HTTP/1.0 200 OK", fileLen, contentType);
            buffOut.write(headerBytes);
            byte[] buf = new byte[2048];
            int blockLen = 0;
            while((blockLen = fileIn.read(buf)) != -1) {
                buffOut.write(buf, 0, blockLen);
            }
            fileIn.close();
        } else {
            System.out.println("workerID=" + workerID + ", 404 Not Found: " + filename);
            byte[] headerBytes = createHeaderBytes("HTTP/1.0 404 Not Found", -1, null);
            buffOut.write(headerBytes);
        }
        buffOut.flush();
    }
    
    private File generateFile(String filename) {
        // Start at the base
        File requestedFile = docRoot;
        // Build up the path to the requested file in a platform independent way
        // URL's use '/' in their path, but this platform may not
        StringTokenizer st = new StringTokenizer(filename,"/");
        while(st.hasMoreTokens()) {
            String tok = st.nextToken();
            if(tok.equals("..")) {
                // Silently ignore parts of path that might lead out of the document root area
                continue;
            }
            requestedFile = new File(requestedFile, tok);
        }
        if(requestedFile.exists() && requestedFile.isDirectory()) {
            // If a directory was requested, modify the request to look for the "index.html" file in that directory
            requestedFile = new File(requestedFile, "index.html");
        }
        return requestedFile;
    }
    
    private byte[] createHeaderBytes(String resp, int contentLen, String contentType) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(baos));
        // Write the first line of the response, followed by the RFC-specified line termination sequence
        writer.write(resp + "\r\n");
        // If a length was specified, add it to the header
        if(contentLen != -1) {
            writer.write("Content-Length:" + contentLen + "\r\n");
        }
        // If a type was specified, add it to the header
        if(contentType != null) {
            writer.write("Content-Type:" + contentType + "\r\n");
        }
        // A blank line is required after the header
        writer.write("\r\n");
        writer.flush();
        byte[] data = baos.toByteArray();
        writer.close();
        return data;
    }
    
    public void stopRequest() {
        noStopRequested = false;
        internalThread.interrupt();
    }
    
    public boolean isAlive() {
        return internalThread.isAlive();
    }
}
