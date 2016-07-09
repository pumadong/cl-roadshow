package com.cl.roadshow.leetcode;

/**
 * 模拟正则表达式
 * https://leetcode.com/problems/regular-expression-matching/
 * @author dongyongjin
 *
 */
public class Leetcode10 {
	
	public static void main(String[] args) {
		Leetcode10 lc = new Leetcode10();
		Solution solution = lc.new Solution();
		System.out.println(solution.isMatch("abc", "a*"));
		System.out.println(solution.isMatch("abc", "1"));
	}

	// http://www.programcreek.com/2012/12/leetcode-regular-expression-matching-in-java/
	public class Solution {
	    public boolean isMatch(String s, String p) {
	    	// base case
	        if(p.length() == 0) {
	            return s.length() == 0;
	        }
	        // p.length()==1 is special base
	    	// p.charAt(1) != '*' is case 1: when the second char of p is not '*'
	        if(p.length() == 1 || p.charAt(1) != '*') {
	            if(s.length() < 1 || (p.charAt(0) != '.' && p.charAt(0) != s.charAt(0))) {
	                return false;
	            }
	            return isMatch(s.substring(1),p.substring(1));
	        } else {
	            int i = -1;
	            int len = s.length();
	            // case 2: when the second char of p is '*', complex case, stand for 0、1 or more preceding element
	            while(i<len && (i==-1 || p.charAt(0)=='.' || p.charAt(0)==s.charAt(i))) {
	                if(isMatch(s.substring(i+1), p.substring(2))) {
	                    return true;
	                }
	                i++;
	            }
	            return false;
	        }
	    }
	}
}
