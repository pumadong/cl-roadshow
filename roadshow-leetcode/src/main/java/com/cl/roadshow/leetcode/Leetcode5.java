package com.cl.roadshow.leetcode;

/**
 * 求最长回文字符串
 * https://leetcode.com/problems/longest-palindromic-substring/
 * @author dongyongjin
 *
 */
public class Leetcode5 {

	public static void main(String[] args) {
		Leetcode5 lc = new Leetcode5();
		Solution solution = lc.new Solution();
		System.out.println(solution.longestPalindrome("abcdcba"));
	}
	
	// http://www.cnblogs.com/TenosDoIt/p/3675788.html
	public class Solution {
	    public String longestPalindrome(String s) {
	        if(s == null || "".equals(s)) {
	            return "";
	        }
	        int maxLength = 0;
	        String result = "";
	        for(int i = 0; i < s.length(); i++) {
	            String str = calLength(s, i);
	            if(str.length() > maxLength) {
	                maxLength = str.length();
	                result = str;
	            }
	        }
	        return result;
	    }
	    
	    private String calLength(String s, int i) {
	        int left = i, right = i;
	        while(i+1 < s.length() && s.charAt(i)==s.charAt(i+1)) {
	            i++;        
	        }
	        right = i;
	        while(left > 0 && right < s.length()-1 && s.charAt(left-1)==s.charAt(right+1)) {
	            left--;
	            right++;
	        }
	        return s.substring(left, ++right);
	    }
	}
}
