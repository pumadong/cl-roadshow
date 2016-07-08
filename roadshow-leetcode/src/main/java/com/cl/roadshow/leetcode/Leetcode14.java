package com.cl.roadshow.leetcode;

/**
 * 给一个字符数组，求最长公共前缀
 * https://leetcode.com/problems/longest-common-prefix/
 * @author dongyongjin
 *
 */
public class Leetcode14 {

	public static void main(String[] args) {
		Leetcode14 lc = new Leetcode14();
		Solution solution = lc.new Solution();
		System.out.println(solution.longestCommonPrefix(new String[]{"abcde","abc","ab"}));
	}
	
	public class Solution {
	    public String longestCommonPrefix(String[] strs) {
	        if(strs.length == 0) {
	    		return "";
	    	}
	    	String first = strs[0];
	    	boolean match = true;
	    	for(int i=0; i<first.length() && match; i++) {
	    		for(int j=0; j<strs.length; j++) {
	    			if(i>=strs[j].length() || first.charAt(i)!=strs[j].charAt(i)) {
	    				match = false;
	    				break;
	    			}
	    		}
	    		if(!match) {
	    			return first.substring(0, i);
	    		}
	    	}
	    	return first;
	    }
	}
}
