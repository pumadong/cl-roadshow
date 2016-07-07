package com.cl.roadshow.leetcode;

/**
 * 数组移除元素，返回新长度
 * https://leetcode.com/problems/remove-element/
 * @author dongyongjin
 *
 */
public class Leetcode27 {
	
	public static void main(String[] args) {
		Leetcode27 lc = new Leetcode27();
		Solution solution = lc.new Solution();
		System.out.println(solution.removeElement(new int[]{3,2,2,3}, 3));
	}
	
	public class Solution {
	    public int removeElement(int[] nums, int val) {
	        int pos = 0;
	        for(int i=0; i<nums.length; i++) {
	            if(nums[i] == val) {
	                continue;
	            }
	            nums[pos++] = nums[i];
	        }
	        return pos;
	    }
	}
}
