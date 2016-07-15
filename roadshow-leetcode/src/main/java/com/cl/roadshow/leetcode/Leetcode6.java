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
		/*
		 * 用数字表示，3行，是这样
			0   4   8
			1 3 5 7 9
			2   6   
		 * */
		/*
		 * 用数字表示，4行，是这样
			0    6
			2  5 7
			2 4  8
			3    9
		 * */
		// 所以，只是一个找规律的题，找出规律；或者知道ZigZag的规律，这个题就好做了，规则如下：
		// 1、任意一行，两个列之间的间隔为step，step = 2 * (总行数 - 1)
		// 2、除了第一行和最后一行，斜线上有数字，数字的下标 = j + step -2*row，j是上一个列的坐标，step是两个列的间隔，row是行号（0开始）
		System.out.println(solution.convert("PAYPALISHIRING", 1));
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
