package com.cl.roadshow.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Generate Parentheses
 * https://leetcode.com/problems/generate-parentheses/
 * @author dongyongjin
 *
 */
public class Leetcode22 {
	
	public static void main(String[] args) {
		Leetcode22 lc = new Leetcode22();
		Solution solution = lc.new Solution();
		System.out.println(solution.generateParenthesis(3));
		System.out.println(solution.generateParenthesis2(3));
	}

	// left and right represents the remaining number of ( and ) that need to be added. 
	// When left > right, such cases are wrong and the method stops. 
	public class Solution {
		public List<String> generateParenthesis(int n) {
		    List<String> result = new ArrayList<String>();
		    dfs(result, "", n, n);
		    return result;
		}
		
		public List<String> generateParenthesis2(int n) {
		    List<String> result = new ArrayList<String>();
		    dfs2(result, "", n, n);
		    return result;
		}
		
		public void dfs(List<String> result, String s, int left, int right) {
			// 这种场景，右括号在左括号的前面，不符合要求，所以返回
			if(left > right) {
				return;
			}
			if(left == 0 && right == 0) {
				result.add(s);
				return;
			}
			if(left > 0) {
				dfs(result, s + "(", left-1, right);
			}
			if(right > 0) {
				dfs(result, s + ")", left, right-1);
			}
		}
		
		public void dfs2(List<String> result, String s, int left, int right) {
			if(left > right) {
				return;
			}
			if(left == 0 && right == 0) {
				result.add(s);
				return;
			}
			if(right > 0) {
				dfs2(result, s + ")", left, right-1);
			}
			if(left > 0) {
				dfs2(result, s + "(", left-1, right);
			}
		}
	}
}
