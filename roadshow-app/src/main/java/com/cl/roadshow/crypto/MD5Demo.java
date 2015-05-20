package com.cl.roadshow.crypto;

import java.security.MessageDigest;

public class MD5Demo {
	public static void main(String[] args) throws Exception {

		String strMD5 = md5("今天天气不错", "md5Pass");
		System.out.println(strMD5);

		boolean b = verify("今天天气不错", "md5Pass", strMD5);
		System.out.println(b);
	}

	/**
	 * MD5方法
	 * 
	 * @param text
	 *          明文
	 * @param key
	 *          密钥
	 * @return 密文
	 * @throws Exception
	 */
	public static String md5(String text, String key) throws Exception {
		byte[] bytes = (text + key).getBytes();

		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(bytes);
		bytes = messageDigest.digest();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			if ((bytes[i] & 0xff) < 0x10) {
				sb.append("0");
			}

			sb.append(Long.toString(bytes[i] & 0xff, 16));
		}

		return sb.toString().toLowerCase();
	}

	/**
	 * MD5验证方法
	 * 
	 * @param text
	 *          明文
	 * @param key
	 *          密钥
	 * @param md5
	 *          密文
	 * @return true/false
	 * @throws Exception
	 */
	public static boolean verify(String text, String key, String md5) throws Exception {
		String md5Text = md5(text, key);
		if (md5Text.equalsIgnoreCase(md5)) {
			return true;
		} else {
			return false;
		}
	}
}
