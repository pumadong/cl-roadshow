package com.cl.roadshow.leetcode;

/**
 * 实现查找第一个出现的字符
 * https://leetcode.com/problems/implement-strstr/
 * @author dongyongjin
 *
 */
public class Leetcode28 {
	public static void main(String[] args) {
		Leetcode28 lc = new Leetcode28();
		Solution solution = lc.new Solution();
		System.out.println(solution.strStr("hello world", "world"));
		System.out.println(solution.strStr("hello world", "how"));
	}
	
	public class Solution {
	    public int strStr(String haystack, String needle) {
	    	if(haystack == null || needle == null) {
	    		return -1;
	    	}
	    	if(haystack.equals(needle)) {
	    		return 0;
	    	}
	    	for(int i=0; i<haystack.length(); i++) {
	    		if(i + needle.length() > haystack.length()) {
	    			return -1;
	    		}
	    		if(haystack.substring(i, i+needle.length()).equals(needle)) {
	    			return i;
	    		}
	    	}
	        return -1;
	    }
	}
}
