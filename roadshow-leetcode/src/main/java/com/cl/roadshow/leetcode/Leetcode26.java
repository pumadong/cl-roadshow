package com.cl.roadshow.leetcode;

/**
 * 排序数组移除重复元素，返回新长度
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array/
 * @author dongyongjin
 *
 */
public class Leetcode26 {
	
	public static void main(String[] args) {
		Leetcode26 lc = new Leetcode26();
		Solution solution = lc.new Solution();
		System.out.println(solution.removeDuplicates(new int[]{1, 1, 2}));
	}
	
	public class Solution {
	    public int removeDuplicates(int[] nums) {
	    	if(nums.length < 2) {
	    		return nums.length;
	    	}
	    	int start = 0;
	    	for(int i=1; i<nums.length; i++) {
	    		if(nums[i] == nums[start]) {
	    			continue;
	    		}
	    		start++;
	    		nums[start] = nums[i];
	    	}
	        return start+1;
	    }
	}

}
