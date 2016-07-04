package com.cl.roadshow.leetcode;

import java.util.Arrays;
import java.util.List;

/**
 * 交互字符串中的元音字母
 * https://leetcode.com/problems/reverse-vowels-of-a-string/
 * @author dongyongjin
 *
 */
public class Leetcode345 {
	public static void main(String[] args) {
		Leetcode345 lc = new Leetcode345();
		Solution solution = lc.new Solution();
		System.out.println(solution.reverseVowels("hello"));
	}
	
	public class Solution {
	    public String reverseVowels(String s) {
	        List<Character> cList = Arrays.asList('a','e','i','o','u','A','E','I','O','U');
	        char[] cInput = s.toCharArray();
	        
	        int i = 0;
	        int j = s.length() -1;
	        
	        while(i < j) {
	            if(!cList.contains(cInput[i])) {
	                i++;
	                continue;
	            }
	            if(!cList.contains(cInput[j])) {
	                j--;
	                continue;
	            }
	            char c = cInput[i];
	            cInput[i] = cInput[j];
	            cInput[j] = c;
	            
	            i++;
	            j--;
	        }
	        
	        return new String(cInput);
	    }
	}
}
