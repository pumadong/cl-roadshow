package com.cl.roadshow.crypto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * AES加密、解密演示
 * 
 * http://blog.163.com/hanozi@126/blog/static/18657562010111941647233/
 * http://gybmike.iteye.com/blog/1336663
 *
 */
public class AESDemo {
    
    private static KeyGenerator keygen ;
    private static SecretKey secretKey;
    private static Cipher cipher;
    private static AESDemo security = null;

    private AESDemo() {
        
    }
    
    public static AESDemo getInstance() throws Exception {
        if(security == null){
            security = new AESDemo();
            keygen = KeyGenerator.getInstance("AES");
            secretKey = keygen.generateKey();
            cipher =Cipher.getInstance("AES");
        }
        return security;
    }

    public static void main(String[] args) throws Exception {
        String str = "Hi,This is AES DEMO";
        String ss =  AESDemo.getInstance().encrypt(str) ;
        System.out.println(ss);
        System.out.println(AESDemo.getInstance().decrypt(ss));
    }
    
    // 加密
    public String encrypt(String str) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        byte [] src =  str.getBytes();
        byte [] enc = cipher.doFinal(src);
        return parseByte2HexStr(enc);
    }


    // 解密
    public String decrypt(String str) throws Exception{
        cipher.init(Cipher.DECRYPT_MODE,secretKey);
        byte[] enc = parseHexStr2Byte(str);
        byte [] dec = cipher.doFinal(enc);
        return new String(dec);
    }

    /**
     * 将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
}