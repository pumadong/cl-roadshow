package com.cl.roadshow.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 演示使用Log4J打印日志，打印日志，以及log4j.xml配置
 * 
 */
public class Log4jDemo {

    private static final Logger logger = LoggerFactory.getLogger(Log4jDemo.class);
    private static final Logger consoleLogger = LoggerFactory.getLogger("console");

    public static void main(String[] args) {
        logger.error("StackTrade Title", new Exception("Hello,Stack"));

        System.out.println("--------------------------------------");

        System.out.println(JSON.toJSONString(new Exception("Hello,Stack")));

        System.out.println("--------------------------------------");

        // 这行日志不会打印出来，因为默认的root只会出来INFO及以上级别的日志
        consoleLogger.debug("This info message is only printed in console!");

        consoleLogger.error("This error message is only printed in console!");
    }
}
