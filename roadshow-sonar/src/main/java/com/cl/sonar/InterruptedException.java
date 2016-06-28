package com.cl.sonar;

public class InterruptedException {
	
	public static void main(String[] args) {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					while(true) {
						System.out.println("ok");
						Thread.sleep(1000*1);
					}
				} catch (java.lang.InterruptedException e) {
					System.out.println(e.getMessage());
					// "InterruptedException" should not be ignored
					Thread.currentThread().interrupt();
				}
			}
		};
		
		final Thread t = new Thread(r);
		t.start();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000*5);
				} catch (java.lang.InterruptedException e) {
					e.printStackTrace();
				}
				t.interrupt();
				
			}}).start();
	}

}
