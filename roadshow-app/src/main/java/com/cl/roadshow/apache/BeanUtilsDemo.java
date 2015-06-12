package com.cl.roadshow.apache;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSON;

/**
 * BeanUtils类功能演示
 * 
 * http://commons.apache.org/proper/commons-beanutils/
 *
 */
public class BeanUtilsDemo {
    public static void main(String[] args) throws IllegalAccessException,
            InvocationTargetException {
        Map<String, String[]> mapPersonWithWholeProp = new HashMap<String, String[]>();
        mapPersonWithWholeProp.put("name", new String[] { "张三" });
        mapPersonWithWholeProp.put("age", new String[] { "35" });
        mapPersonWithWholeProp.put("lover", new String[] { "jessie", "mary" });

        System.out.println("mapPersonWithWholeProp："
                + JSON.toJSONString(mapPersonWithWholeProp));

        System.out.println("--------------------------------------");

        // 注意：这个Lover类需要独立文件定义
        Lover loverFromWhole = new Lover();

        BeanUtils.populate(loverFromWhole, mapPersonWithWholeProp);

        System.out.println("loverFromWhole："
                + JSON.toJSONString(loverFromWhole));

        System.out.println("\n--------------------------------------\n");

        Map<String, String[]> mapPersonWithPartProp = new HashMap<String, String[]>();

        // Map的名字和类的属性名不完全匹配
        mapPersonWithPartProp.put("Name", new String[] { "张三" });
        mapPersonWithPartProp.put("age", new String[] { "35" });
        mapPersonWithPartProp.put("lover", new String[] { "jessie", "mary" });

        System.out.println("mapPersonWithPartProp："
                + JSON.toJSONString(mapPersonWithPartProp));

        System.out.println("--------------------------------------");

        Lover loverFromPart = new Lover();

        BeanUtils.populate(loverFromPart, mapPersonWithPartProp);

        System.out.println("loverFromPart：" + JSON.toJSONString(loverFromPart));
    }
}
