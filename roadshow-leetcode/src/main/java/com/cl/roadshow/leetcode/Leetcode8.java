package com.cl.roadshow.leetcode;

/**
 * 字符串转整数
 * https://leetcode.com/problems/string-to-integer-atoi/
 * @author dongyongjin
 *
 */
public class Leetcode8 {

	public static void main(String[] args) {
		Leetcode8 lc = new Leetcode8();
		Solution solution = lc.new Solution();
		System.out.println(solution.myAtoi("-12345"));
		System.out.println(solution.myAtoi("-123d45"));
		System.out.println(solution.myAtoi("-2147483648"));
		System.out.println(solution.myAtoi("-2147483649"));
	}
	
	public class Solution {
	    public int myAtoi(String str) {
	        if(str == null || str.equals("")) {
	            return 0;
	        }   
	        int max = Integer.MAX_VALUE;
	        int min = Integer.MIN_VALUE;
	        long result = 0;
	        str = str.trim();
	        boolean isNegative = false;
	        if(str.charAt(0) == '-') {
	            isNegative = true;
	        }
	        
	        for(int i = 0; i < str.length(); i++) {
	            char c = str.charAt(i);
	            if((c == '+' || c == '-') && i == 0) {
	                continue;
	            }
	            if(c < '0' || c > '9') {
	                break;
	            }
	            result = result * 10 + (c - '0');
	            if(isNegative) {
	                if(result * -1 < min) {
	                    return min;
	                }
	            } else {
	                if(result > max) {
	                    return max;
	                }
	            }
	        }
	        return isNegative ? (int)result*-1 : (int)result;
	    }
	}
}
