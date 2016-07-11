package com.cl.roadshow.leetcode;

/**
 * 回文数字
 * https://leetcode.com/problems/palindrome-number/
 * @author dongyongjin
 *
 */
public class Leetcode9 {

	public static void main(String[] args) {
		Leetcode9 lc = new Leetcode9();
		Solution solution = lc.new Solution();
		System.out.println(solution.isPalindrome(900009));
	}
	
	// http://www.programcreek.com/2013/02/leetcode-palindrome-number-java/
	public class Solution {
	    public boolean isPalindrome(int x) {
	        if(x < 0) {
	            return false;
	        }
	        int div = 1;
	        while(x / div >= 10) {
	            div = div * 10;
	        }
	        while(x != 0) {
	            int left = x / div;
	            int right = x % 10;
	            if(left != right) {
	                return false;
	            }
	            x = (x % div) / 10;
	            div /= 100;
	        }
	        return true;
	    }
	}
}
