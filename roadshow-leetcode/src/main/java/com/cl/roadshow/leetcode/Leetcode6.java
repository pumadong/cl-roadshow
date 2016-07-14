package com.cl.roadshow.leetcode;

/**
 * ZigZag转换
 * @author dongyongjin
 *
 */
public class Leetcode6 {

	public static void main(String[] args) {
		Leetcode6 lc = new Leetcode6();
		Solution solution = lc.new Solution();
		/*
			P   A   H   N
			A P L S I I G
			Y   I   R
		 * */
		System.out.println(solution.convert("PAYPALISHIRING", 3));
	}
	
	// http://www.tuicool.com/articles/bAbyqii
	public class Solution {
	    public String convert(String s, int numRows) {
	        if(numRows <= 1) {
	            return s;
	        }
	        String result = "";
	        int step = 2 * (numRows - 1);
	        for(int row = 0; row < numRows; row++) {
	            for(int j = row; j < s.length(); j += step) {
	                result += s.charAt(j);
	                if(row == 0 || row == numRows -1) {
	                    continue;
	                }
	                int slash = j + step - 2*row;
	                if(slash < s.length()) {
	                    result += s.charAt(slash);
	                }
	            }
	        }
	        return result;
	    }
	}
}
