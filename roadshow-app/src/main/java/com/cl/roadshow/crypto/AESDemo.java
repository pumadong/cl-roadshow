package com.cl.roadshow.crypto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * AES加密、解密演示
 * 
 * http://outofmemory.cn/code-snippet/35524/AES-with-javascript-java-csharp-python-or-php
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
        
        // 第一种方式
        String str = "Hi,This is AES DEMO";
        String ss =  AESDemo.getInstance().encrypt(str) ;
        System.out.println(ss);
        System.out.println(AESDemo.getInstance().decrypt(ss));
        
        System.out.println("===================================");
        
        // 第二种方式
        String secretKey = "XMzDdG4D03CKm2IxIWQw7g==";
        String value1= "ABCD";
        String enctypedValue1= "3uweh4pzoVyH1uODQmVNJA==";
        String enctypedValue2= "37PTC20w4DMZYjG3f+GWepSvAbEJUccMXwS/lXilLav1qM/PrCTdontw5/82OdC1zzyhDEsFVRGo rV6gXAQcm+Zai15hliiUQ8l8KRMtUl4=";
        String value4= "20000";

        /**  Ecnryption and decryption of value1 **/
        String encryptedValue1= symmetricEncrypt(value1, secretKey);
        String decryptedValue1 = symmetricDecrypt(encryptedValue1, secretKey);
        System.out.println(decryptedValue1);

        /**  Decryption of  enctypedValue1 **/
        String decryptedValue2 = symmetricDecrypt(enctypedValue1, secretKey);
        System.out.println(decryptedValue2);

        /**  Decryption of  enctypedValue2 **/
        String decryptedValue3 = symmetricDecrypt(enctypedValue2, secretKey);
        System.out.println(decryptedValue3);

        /**  Ecnryption and decryption of value4 **/
        String encryptedValue4= symmetricEncrypt(value4, secretKey);
        String decryptedValue4 = symmetricDecrypt(encryptedValue4, secretKey);
        System.out.println(decryptedValue4);
        
        System.out.println("===================================");
        
        // 第三种方式
        System.out.println(encrypt());
        System.out.println(desEncrypt());
        
        System.out.println("===================================");
        
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
    
    public static String symmetricEncrypt(String text, String secretKey) {
        byte[] raw;
        String encryptedString;
        SecretKeySpec skeySpec;
        byte[] encryptText = text.getBytes();
        Cipher cipher;
        try {
            raw = Base64.decodeBase64(secretKey);
            skeySpec = new SecretKeySpec(raw, "AES");
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            encryptedString = Base64.encodeBase64String(cipher.doFinal(encryptText));
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
        return encryptedString;
    }

    public static String symmetricDecrypt(String text, String secretKey) {
        Cipher cipher;
        String encryptedString;
        byte[] encryptText = null;
        byte[] raw;
        SecretKeySpec skeySpec;
        try {
            raw = Base64.decodeBase64(secretKey);
            skeySpec = new SecretKeySpec(raw, "AES");
            encryptText = Base64.decodeBase64(text);
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            encryptedString = new String(cipher.doFinal(encryptText));
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
        return encryptedString;
    }
    
    public static String encrypt() throws Exception {
        try {
            String data = "Test String";
            String key = "1234567812345678";
            String iv = "1234567812345678";
            
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String desEncrypt() throws Exception {
        try
        {
            String data = "2fbwW9+8vPId2/foafZq6Q==";
            String key = "1234567812345678";
            String iv = "1234567812345678";
            
            byte[] encrypted1 = Base64.decodeBase64(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}