package com.cl.roadshow.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 斐波那契数列3种解法
 *
 */
public class FibonacciDemo {
    
    private static final Logger log = LoggerFactory.getLogger("console");
    
    public static final int items = 20;
    
    public static void main(String[] args) {
        SolvingBy3Variables();
        SolvingByArray();
        for(int i = 1; i <= items; i++) {
            log.info(i + "：\t" + SolvingByRecursive(i));
        }
    }
    
    /**
     * 3个变量的方式
     */
    public static void SolvingBy3Variables() {
        
        int a = 1;
        int b = 1;
        int c = 0;
        
        log.info("1：\t" + a);
        log.info("2：\t" + a);
        
        for(int i = 3; i <= items; i++) {
            c = a + b;
            b = a;
            a = c;
            log.info(i + "：\t" + a);
        }
    }
    
    /**
     * 数组的方式
     */
    public static void SolvingByArray() {
        int[] arr = new int[items];
        arr[0] = 1;
        arr[1] = 1;
        for(int i = 3; i <= items; i++) {
            arr[i-1] = arr[i-2] + arr[i-3];
        }
        for( int i = 0; i < arr.length; i++) {
            log.info(i+1 + "：\t" + arr[i]);
        }
    }
    
    /**
     * 递归的方式
     * @param n
     * @return
     */
    public static int SolvingByRecursive(int n) {
        if(n == 1 || n == 2) {
            return 1;
        }
        int r = SolvingByRecursive(n-1) + SolvingByRecursive(n-2);
        return r;
    }
}
