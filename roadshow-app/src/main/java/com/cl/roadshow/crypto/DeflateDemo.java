package com.cl.roadshow.crypto;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Zlib压缩解压算法
 * 
 * https://en.wikipedia.org/wiki/DEFLATE
 * 
 * https://en.wikipedia.org/wiki/Zlib
 * 
 * 对应的JavaScript实现：https://github.com/nodeca/pako
 *
 */
public class DeflateDemo {
    
    private static final Logger log = LoggerFactory.getLogger("console");
    
    public static void main(String[] args) throws Exception {
        // 演示hello 编码后的字节码数组
        String data = "hello";
        ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
        DeflaterOutputStream zos = new DeflaterOutputStream(bos,new Deflater(-1,false)); 
        zos.write(data.getBytes("utf-8")); 
        zos.close();
        
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<bos.toByteArray().length;i++) {
            sb.append(bos.toByteArray()[i] + "|");
        }
        log.info(sb.toString());
        
        // 演示压缩解压
        String source = "hello";
        String compressStr = compress(source);
        log.info(compressStr);
        String deCompressStr = deCompress(compressStr);
        log.info(deCompressStr);

    }
    
    /**
     * 压缩数据 rfc-1951
     * @param data
     * @return
     * @throws Exception
     */
    public static String compress(String data) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
        DeflaterOutputStream zos = new DeflaterOutputStream(bos); 
        zos.write(data.getBytes("UTF-8")); 
        zos.close(); 
        String res = Base64.encodeBase64String(bos.toByteArray());
        return res;
    }
    
    /**
     * 解压数据 rfc-1951
     * @param data
     * @return
     * @throws Exception
     */
    public static String deCompress(String data) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
        InflaterOutputStream zos = new InflaterOutputStream(bos);  
        zos.write(Base64.decodeBase64(data));
        zos.close(); 
        return new String(bos.toByteArray()); 
    }
}
