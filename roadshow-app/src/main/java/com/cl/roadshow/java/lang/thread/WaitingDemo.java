package com.cl.roadshow.java.lang.thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 演示一点简单的线程阻塞：被正在运行的程序、sleep、wait阻塞
 *
 */
public class WaitingDemo {
	public static Object Lock = new Object();

	public static void main(String[] args) throws Exception {

		ConditionThread block = new ConditionThread("ConditionThread");
		block.start();

		for (int i = 0; i < 1; i++) {
			new Thread(new WaitingThread()).start();
		}

		Thread.sleep(1000 * 120);

		synchronized (WaitingDemo.Lock) {
			WaitingDemo.Lock.notify();
		}
	}
}

class WaitingThread implements Runnable {
	public void run() {
		synchronized (WaitingDemo.Lock) {
			System.out.println(Thread.currentThread().getName() + ":" + "is running");
		}
	}
}

class ConditionThread extends Thread {

	public ConditionThread(String name) {
		super(name);
	}

	@Override
	public void run() {
		synchronized (WaitingDemo.Lock) {
			System.out.println(Thread.currentThread().getName() + ":" + "is running");

			// 用正常的操作阻塞其他线程
			try {
				call("http://www.sina.com.cn");
				call("http://www.baidu.com");
				call("http://www.163.com");
				call("http://www.taobao.com");
				call("http://www.amazon.com");
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 用sleep阻塞其他线程
			try {
				Thread.sleep(1000 * 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("wait is begin");
			// 用wait阻塞自己
			try {
				WaitingDemo.Lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("wait is over");
		}
	}

	private void call(String remoteUrl) throws Exception {
		System.out.println("begin send");
		String inputParam = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><content>Test</content></page>";

		URL url = null;
		HttpURLConnection httpConn = null;
		OutputStream output = null;
		OutputStreamWriter outr = null;

		url = new URL(remoteUrl);
		httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setConnectTimeout(30000);
		httpConn.setReadTimeout(30000);
		HttpURLConnection.setFollowRedirects(true);
		httpConn.setDoOutput(true);
		httpConn.setRequestMethod("POST");
		httpConn.setRequestMethod("GET");
		httpConn.setRequestProperty("Content-Type", "text/xml");
		httpConn.connect();

		output = httpConn.getOutputStream();
		outr = new OutputStreamWriter(output); // 写入请求参数
		outr.write(inputParam.toString().toCharArray(), 0, inputParam.toString().length());
		outr.flush();
		outr.close();

		System.out.println("send ok");
		int code = httpConn.getResponseCode();
		System.out.println("code " + code);
		System.out.println(httpConn.getResponseMessage());

		// 读取响应内容
		String sCurrentLine = "";
		String sTotalString = "";
		if (code == 200) {
			java.io.InputStream is = httpConn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			while ((sCurrentLine = reader.readLine()) != null) {
				if (sCurrentLine.length() > 0) {
					sTotalString = sTotalString + sCurrentLine.trim();
				}
			}
		} else {
			sTotalString = "远程服务器连接失败,错误代码:" + code;

		}
		// System.out.println("response:" + sTotalString);
	}
}