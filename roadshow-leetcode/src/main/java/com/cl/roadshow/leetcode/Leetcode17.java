package com.cl.roadshow.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Letter Combinations of a Phone Number
 * https://leetcode.com/problems/letter-combinations-of-a-phone-number/
 * @author dongyongjin
 *
 */
public class Leetcode17 {

	public static void main(String[] args) {
		Leetcode17 lc = new Leetcode17();
		Solution solution = lc.new Solution();
		System.out.println(solution.letterCombinations("23"));
	}
	
	// 考察的是对递归的巧妙使用
	public class Solution {
	    public List<String> letterCombinations(String digits) {
	    	List<String> result = new ArrayList<String>();
	    	if(digits == null || digits.equals("")) {
	    		return result;
	    	}
	    	Map<Character, char[]> map = new HashMap<Character, char[]>();
	    	map.put('0', new char[] {});
	    	map.put('1', new char[] {});
	        map.put('2', new char[] { 'a', 'b', 'c' });
	        map.put('3', new char[] { 'd', 'e', 'f' });
	        map.put('4', new char[] { 'g', 'h', 'i' });
	        map.put('5', new char[] { 'j', 'k', 'l' });
	        map.put('6', new char[] { 'm', 'n', 'o' });
	        map.put('7', new char[] { 'p', 'q', 'r', 's' });
	        map.put('8', new char[] { 't', 'u', 'v'});
	        map.put('9', new char[] { 'w', 'x', 'y', 'z' });
	        StringBuilder sb = new StringBuilder();
	        handle(map, digits, sb, result);
	    	return result;
	    }
	    
	    public void handle(Map<Character, char[]> map, String digits, StringBuilder sb, List<String> result) {
	    	if(sb.length() == digits.length()) {
	    		result.add(sb.toString());
	    		return;
	    	}
	    	for(Character c : map.get(digits.charAt(sb.length()))) {
	    		sb.append(c);
	    		handle(map, digits, sb, result);
	    		sb.deleteCharAt(sb.length() - 1);
	    	}
	    }
	}
}
