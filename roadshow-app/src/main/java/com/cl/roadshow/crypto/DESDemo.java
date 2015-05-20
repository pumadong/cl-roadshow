package com.cl.roadshow.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.bouncycastle.util.encoders.Base64;

/**
 * DES加密、解密演示
 * 
 * 关于用sun.misc包来实现Base64，是不建议使用的，因为
 * 
 * 这个包是Sun公司提供给内部使用的专用API，在java API文档中都看不到
 *
 */
public class DESDemo {
	public static void main(String[] args) throws Exception {
		String strEncrypt = encrypt("今天天气不错，OH，YEAH!", "desPassword", "UTF-8");
		System.out.println(strEncrypt);

		String strDecrypt = decrypt(strEncrypt, "desPassword", "UTF-8");
		System.out.println(strDecrypt);
	}

	/**
	 * 加密方法
	 * 
	 * @param text
	 *          明文
	 * @param key
	 *          密钥 BASE64
	 * @param charset
	 *          字符集
	 * @return 密文
	 * @throws Exception
	 */
	public static String encrypt(String text, String key, String charset) throws Exception {

		// 注意，sun.misc包是Sun公司提供给内部使用的专用API，在java API文档中我们看不到任何有关BASE64影子，不建议使用。

		// base64解码key
		byte[] keyBase64DecodeBytes = Base64.decode(key);
		// 前8个字节做为密钥
		DESKeySpec desKeySpec = new DESKeySpec(keyBase64DecodeBytes);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] textBytes = text.getBytes(charset);// 明文UTF-8格式
		byte[] bytes = cipher.doFinal(textBytes);// DES加密

		String encryptBase64EncodeString = new String(Base64.encode(bytes));// base64编码

		return encryptBase64EncodeString;
	}

	/**
	 * 解密方法
	 * 
	 * @param text
	 *          密文
	 * @param key
	 *          密钥 BASE64
	 * @param charset
	 *          字符集
	 * @return 明文
	 * @throws Exception
	 */
	public static String decrypt(String text, String key, String charset) throws Exception {
		byte[] keyBase64DecodeBytes = Base64.decode(key);

		DESKeySpec desKeySpec = new DESKeySpec(keyBase64DecodeBytes);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] textBytes = Base64.decode(text);
		byte[] bytes = cipher.doFinal(textBytes);

		String decryptString = new String(bytes, charset);

		return decryptString;
	}
}