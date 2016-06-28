package com.cl.sonar;

public class StringFormat {
	public static void main(String[] args) {
		// %n should be used in place of \n to produce the platform-specific line separator
		String stringToSign = String.format("%s %s%n%s", "method", "uri", "date");
		System.out.println(stringToSign);
	}
}
