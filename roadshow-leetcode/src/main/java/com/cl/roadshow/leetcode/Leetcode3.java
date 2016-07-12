package com.cl.roadshow.leetcode;

/**
 * 求最长的不重复子串长度
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/
 * @author dongyongjin
 *
 */
public class Leetcode3 {

	public static void main(String[] args) {
		Leetcode3 lc = new Leetcode3();
		Solution solution = lc.new Solution();
		System.out.println(solution.lengthOfLongestSubstring("abcabcabcd"));
	}
	
	public class Solution {
	    public int lengthOfLongestSubstring(String s) {
	        int res = 0,left = 0;
	        int[] prev = new int[256];
	        for(int i = 0; i < prev.length; i++) {
	            prev[i] = -1;
	        }
	        for(int i = 0; i < s.length(); i++) {
	        	// 说明出现了字符重复，所以从重复字符上一次出现的位置+1作为left
	            if(prev[s.charAt(i)] >= left) {
	                left = prev[s.charAt(i)] + 1;
	            }
	            prev[s.charAt(i)] = i;
	            if(i - left + 1 > res) {
	                res = i - left + 1;
	            }
	        }
	        return res;
	    }
	}
}
