package com.cl.roadshow.leetcode;

/**
 * 加各个位上的数（找规律）
 * https://leetcode.com/problems/add-digits/
 * 参考：http://www.cnblogs.com/grandyang/p/4741028.html
 * @author dongyongjin
 *
 */
public class Leetcode258 {
	public static void main(String[] arsgs) {
		Leetcode258 lc = new Leetcode258();
		System.out.println(lc.addDigits(38));
		System.out.println(lc.addDigitsLoop(38));
	}
	
	public int addDigits(int num) {
        return 1 + (num-1) % 9;
    }
	
	public int addDigitsLoop(int num) {
        while(num >= 10) {
        	num = (num / 10) + (num % 10);
        }
        return num;
    }
}
