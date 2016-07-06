package com.cl.roadshow.leetcode;

import java.util.HashMap;

/**
 * 两个字符串由相同的字符组成
 * https://leetcode.com/problems/valid-anagram/
 * @author dongyongjin
 *
 */
public class Leetcode242 {
	
	public static void main(String[] args) {
		Leetcode242 lc = new Leetcode242();
		Solution solution = lc.new Solution();
		System.out.println(solution.isAnagram("abbtt", "tttab"));
		System.out.println(solution.isAnagram("abbtt", "ttbab"));
	}
	
	public class Solution {
	    public boolean isAnagram(String s, String t) {
	        if(s.length() != t.length()) {
	            return false;
	        }
	        HashMap<Character,Integer> hs = new HashMap<Character,Integer>();
	        for(int i = 0; i < s.length(); i++) {
	            Integer c = hs.get(s.charAt(i));
	            if(c == null) {
	                c = 0;
	            }
	            c++;
	            hs.put(s.charAt(i),c);
	        }
	        for(int i = 0; i < t.length(); i++) {
	            Integer c = hs.get(t.charAt(i));
	            if(c == null) {
	                return false;
	            }
	            if(c > 1) {
	                hs.put(t.charAt(i),--c);
	            } else {
	                hs.remove(t.charAt(i));
	            }
	        }
	        return true;
	    }
	}
}
