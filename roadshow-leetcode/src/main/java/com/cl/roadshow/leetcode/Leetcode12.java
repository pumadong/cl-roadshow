package com.cl.roadshow.leetcode;

/**
 * Integer to Roman
 * https://leetcode.com/problems/integer-to-roman/
 * @author dongyongjin
 *
 */
public class Leetcode12 {

	public static void main(String[] args) {
		Leetcode12 lc = new Leetcode12();
		Solution solution = lc.new Solution();
		System.out.println(solution.intToRoman(521));
		System.out.println(solution.intToRoman(12521));
	}
	
	// http://www.jiuzhang.com/solutions/integer-to-roman/
	public class Solution {
		public String intToRoman(int num) {
			if(num <= 0) {
				return "";
			}
		    int[] nums = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
		    String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
		    StringBuilder res = new StringBuilder();
		    int digit=0;
		    while (num > 0) {
		        int times = num / nums[digit];
		        num -= nums[digit] * times;
		        for ( ; times > 0; times--) {
		            res.append(symbols[digit]);
		        }
		        digit++;
		    }
		    return res.toString();
		}
	}
}
