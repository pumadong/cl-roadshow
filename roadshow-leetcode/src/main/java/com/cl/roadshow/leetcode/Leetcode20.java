package com.cl.roadshow.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Valid Parentheses
 * https://leetcode.com/problems/valid-parentheses/
 * @author dongyongjin
 *
 */
public class Leetcode20 {
	
	public static void main(String[] args) {
		Leetcode20 lc = new Leetcode20();
		Solution solution = lc.new Solution();
		System.out.println(solution.isValid("()[]{}1"));
		System.out.println(solution.isValid("()[]{}"));
		System.out.println(solution.isValid("([])"));
		System.out.println(solution.isValid(null));
		System.out.println(solution.isValid(""));
	}

	// 栈的考察
	// http://www.programcreek.com/2012/12/leetcode-valid-parentheses-java/
	public class Solution {
	    public boolean isValid(String s) {
	    	if(s == null || s.equals("")) {
	    		return false;
	    	}
	    	Map<Character, Character> map = new HashMap<Character, Character>();
	    	map.put('(', ')');
	    	map.put('[', ']');
	    	map.put('{', '}');
	    	
	    	Stack<Character> stack = new Stack<Character>();
	    	for(int i = 0; i < s.length(); i++) {
	    		Character curr = s.charAt(i);
	    		if(map.keySet().contains(curr)) {
	    			stack.push(curr);
	    		} else {
	    			if(!map.values().contains(curr) || stack.empty() || map.get(stack.pop()) != curr) {
	    				return false;
	    			}
	    		}
	    	}
	    	return stack.empty();
	    }
	}
}
