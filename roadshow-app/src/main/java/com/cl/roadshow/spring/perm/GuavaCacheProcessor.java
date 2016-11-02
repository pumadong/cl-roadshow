package com.cl.roadshow.spring.perm;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;

/**
 * GuavaCache注解处理器
 * @author dongyongjin
 *
 */
@Component
@Aspect
public class GuavaCacheProcessor {
	
	private static Map<String, Cache<String, Object>> cacheMap = Maps.newConcurrentMap();
	
    @Around("execution(* *(..)) && @annotation(guavaCache)")
    public Object aroundMethod(ProceedingJoinPoint pjd ,GuavaCache guavaCache) throws Throwable {
    	Cache<String, Object> cache = getCache(pjd, guavaCache);
    	String key = getKey(pjd);
        Object result = null;
        result = cache.getIfPresent(key);
        if(result != null) {
        	return result;
        }
        try {
            result = pjd.proceed();
            cache.put(key, result);
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    /**
     * 获取Cache
     * @param pjd
     * @param guavaCache
     * @return
     */
    private Cache<String, Object> getCache(ProceedingJoinPoint pjd, GuavaCache guavaCache) {
    	String group = guavaCache.group();
    	if(group == null || "".equals(group)) {
	    	MethodSignature signature = (MethodSignature) pjd.getSignature();
	        Method method = signature.getMethod();
	        Class<?> clazz =  method.getDeclaringClass();
	        group = clazz.getName();
    	}
        Cache<String, Object> cache = cacheMap.get(group);
        if(cache == null) {
        	cache = CacheBuilder.newBuilder()
        			.maximumSize(guavaCache.size())
        			.expireAfterAccess(guavaCache.timeout(), TimeUnit.SECONDS)
        			.build();
        	cacheMap.put(group, cache);
        }
        return cache;
    }
    
    /**
     * 获取Key：方法名+getCacheKey方法(如果没有，则用toString())的MD5值
     * @param pjd
     * @return
     */
    private String getKey(ProceedingJoinPoint pjd) {
    	StringBuilder sb = new StringBuilder();
    	MethodSignature signature = (MethodSignature) pjd.getSignature();
        Method method = signature.getMethod();
        sb.append(method.getName());
    	for(Object param : pjd.getArgs()) {
    		if(GuavaCacheInterface.class.isAssignableFrom(param.getClass())) {
    			sb.append(((GuavaCacheInterface)param).getCacheKey());
    		} else {
    			sb.append(param.toString());
    		}
    	}
    	String key = md5(sb.toString());
    	return key;
    }
    
    /**
     * 进行MD5加密
     *
     * @param info
     *            要加密的信息
     * @return String 加密后的字符串
     */
    private static String md5(String info) {
        byte[] digesta = null;
        try {
            // 得到一个md5的消息摘要
            MessageDigest alga = MessageDigest.getInstance("MD5");
            // 添加要进行计算摘要的信息
            alga.update(info.getBytes());
            // 得到该摘要
            digesta = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 将摘要转为字符串
        String rs = byte2hex(digesta);
        return rs;
    }
    
    /**
     * 将二进制转化为16进制字符串
     *
     * @param b
     *            二进制字节数组
     * @return String
     */
    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }
}
