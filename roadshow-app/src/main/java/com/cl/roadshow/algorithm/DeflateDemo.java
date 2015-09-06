package com.cl.roadshow.algorithm;

import java.io.ByteArrayOutputStream;
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
 */
public class DeflateDemo {
    
    private static final Logger log = LoggerFactory.getLogger("console");
    
    public static void main(String[] args) throws Exception {
        
        String source = "hello 中国,hello 中国";
        
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
