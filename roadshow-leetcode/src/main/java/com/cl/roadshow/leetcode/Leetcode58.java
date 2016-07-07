package com.cl.roadshow.leetcode;

/**
 * 求最后一个字符的长度
 * https://leetcode.com/problems/length-of-last-word/
 * @author dongyongjin
 *
 */
public class Leetcode58 {
	public static void main(String[] args) {
		Leetcode58 lc = new Leetcode58();
		Solution solution = lc.new Solution();
		System.out.println(solution.lengthOfLastWord("hello world"));
	}
	public class Solution {
	    public int lengthOfLastWord(String s) {
	        if(s.length()==0) {
	            return 0;
	        }
	        int prevWordSize = 0;
	        int currWordSize = 0;
	        boolean current = true;
	        for(int i=0; i < s.length(); i++) {
	            char c = s.charAt(i);
	            if(c == ' ') {
	            	if(current) {
	            		prevWordSize = currWordSize;
	            		current = false;
	            	}
	                currWordSize = 0;
	            } else {
	            	current = true;
	                currWordSize++;
	                prevWordSize = 0;
	            }
	        }
	        return prevWordSize+currWordSize;
	    }
	}
}
