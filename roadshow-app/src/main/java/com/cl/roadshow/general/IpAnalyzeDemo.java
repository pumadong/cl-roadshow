package com.cl.roadshow.general;

import java.net.UnknownHostException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

/**
 * 简写IP范围解析：解析给定的IP段代表的IP范围
 * 
 * 常见场景是黑白名单的过滤。一遍就是比较高N位，一致就认为在范围内
 */
public class IpAnalyzeDemo {
    public static void main(String[] args) throws UnknownHostException {
        Set<String> ips = new LinkedHashSet<String>();
        
        String ipStr = "[\"10.0.0.0/8\", \"10.64.38.14\", \"211.151.229.192/26\", \"222.223.204.194/28\", \"8.37.235.0/24\", \"36.110.144.18/28\", \"36.110.144.19/28\", \"8.37.236.0/24\"]";
        
        List<String> ipStructure = JSONObject.parseArray(ipStr, String.class);
        for(int i=0; i<ipStructure.size(); i++) {
            String[] ipScope = ipStructure.get(i).split("/");
            if(ipScope.length == 1) {
                ips.add(ipScope[0]);
                continue;
            }
            analyzeIpScope(ips,ipScope);
        }
        
        for(String s : ips) {
            System.out.println(s);
        }
        
    }
    
    private static void analyzeIpScope(Set<String> ips,String[] ipScope) {

        int maskLength = Integer.parseInt(ipScope[1]) ;

        int dotLast = ipScope[0].lastIndexOf(".");
        String ipBeforeLastDot = ipScope[0].substring(0, dotLast);
        String ipAfterLastDot = ipScope[0].substring(dotLast+1);
        if(maskLength == 24) {
            ips.add(ipBeforeLastDot + ".0");
            return;
        }
        if(maskLength > 24) {
            Integer subnet = Integer.parseInt(ipAfterLastDot);
            
            int prefix = 255 << (maskLength-24) & 0xFF;
            int suffix = 255 >> (maskLength-24);
            subnet = subnet & prefix;

            for(int i=0; i<=suffix; i++) {
                ips.add(ipBeforeLastDot + "." + ((subnet + i)));
            }
        }
    }
}