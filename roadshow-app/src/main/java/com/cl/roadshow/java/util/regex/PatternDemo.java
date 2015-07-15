package com.cl.roadshow.java.util.regex;

import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Java 正则表达式演示
 * 
 *
 */
public class PatternDemo {
    
    private static final Logger log = LoggerFactory.getLogger("console");
    
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        try {
            InetAddress IP = InetAddress.getLocalHost();
            sb.append(IP.getHostName()).append(":");
            sb.append(IP.getHostAddress());
        } catch (java.net.UnknownHostException e) {
            log.error("try to get host failed.", e);
            sb.append("unknown-host");
        }
        String host = sb.toString();
        log.info("--------------------------------------");
        log.info(host);
        
        host = "dx-hotel-campaigns01:192.168.1.1";
        Matcher m = Pattern.compile("(([a-z\\-]+)\\d*)[\\.:]").matcher(host);
        if (m.find()) {
            for(int i = 0; i <= m.groupCount(); i++) {
                log.info(m.group(i).trim());
            }
        }
        log.info(m.group().trim());
        log.info("--------------------------------------");
    }
}
