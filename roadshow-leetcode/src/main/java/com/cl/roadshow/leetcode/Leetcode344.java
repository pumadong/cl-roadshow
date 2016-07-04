package com.cl.roadshow.leetcode;

/**
 * 反转字符串
 * https://leetcode.com/problems/reverse-string/
 * @author dongyongjin
 *
 */
public class Leetcode344 {
	public static void main(String[] args) {
		Leetcode344 lc = new Leetcode344();
		System.out.println(lc.reverseString("hello"));
	}
	
    public String reverseString(String s) {
        StringBuilder sb = new StringBuilder();
        for(int i=s.length()-1; i>=0; i--) {
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }
}
