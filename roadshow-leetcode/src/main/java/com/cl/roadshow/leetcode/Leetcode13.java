package com.cl.roadshow.leetcode;

/**
 * 罗马数字转换成Integer：关键是知道罗马数字的转换规则
 * https://leetcode.com/problems/roman-to-integer/
 * @author dongyongjin
 *
 */
public class Leetcode13 {
	
	public static void main(String[] args) {
		Leetcode13 lc = new Leetcode13();
		Solution solution = lc.new Solution();
		System.out.println(solution.romanToInt("IIVIV"));;
	}

	// http://blog.csdn.net/beiyetengqing/article/details/8458778
	public class Solution {
	    public int romanToInt(String s) {  
	        if(s == null || s.length() == 0) {
	            return 0;
	        }
	        int result = 0;
	        int subValue = getRomanValue(s.charAt(0));
	        int lastValue = subValue;
	        for(int i = 1; i < s.length(); i++) {
	            char c = s.charAt(i);
	            int currentValue = getRomanValue(c);
	            if(currentValue == lastValue) {
	                subValue += currentValue;
	            } else if(currentValue < lastValue) {
	                result += subValue;
	                subValue = currentValue;
	            } else {
	                subValue = currentValue - subValue;
	            }
	            lastValue = currentValue;
	        }
	        result += subValue;
	        return result;
	    }  
	    public int getRomanValue(char c) {
	        switch(c) {
	            case 'I': return 1;
	            case 'V': return 5;
	            case 'X': return 10;
	            case 'L': return 50;
	            case 'C': return 100;
	            case 'D': return 500;
	            case 'M': return 1000;
	            default: return 0;
	        }
	    }
	}
}
