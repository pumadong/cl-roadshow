package com.cl.roadshow.leetcode;

/**
 * 反转整数
 * https://leetcode.com/problems/reverse-integer/
 * @author dongyongjin
 *
 */
public class Leetcode7 {
	
	public static void main(String[] args) {
		Leetcode7 lc = new Leetcode7();
		Solution solution = lc.new Solution();
		System.out.println(solution.reverse(12345));
		System.out.println(solution.reverse(-12345));
		System.out.println(solution.reverse(Integer.MAX_VALUE));
		System.out.println(solution.reverse(Integer.MIN_VALUE));
	}

	public class Solution {
	    public int reverse(int x) {
	    	long result = 0;
	    	while( x != 0) {
	    		result = result * 10 + (x % 10);
	    		x /= 10;
	    	}
	    	if(result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
	    		return 0;
	    	}
	        return (int)result;
	    }
	}
}
