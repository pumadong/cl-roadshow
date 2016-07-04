package com.cl.roadshow.leetcode;

import java.util.Arrays;

/**
 * 移动数字数组中的0到一端
 * https://leetcode.com/problems/move-zeroes/
 * @author dongyongjin
 *
 */
public class Leetcode283 {
	public static void main(String[] args) {
		Leetcode283 lc = new Leetcode283();
		Solution solution = lc.new Solution();
		int[] nums = new int[]{0, 1, 0, 3, 12};
		solution.moveZeroes(nums);
		
		System.out.println(Arrays.toString(nums));
		
	}
	
	// 时间复杂度 Q(n)
	public class Solution {
	    public void moveZeroes(int[] nums) {
	        int currentZeroIndex = -1;
	        for(int i=0; i<nums.length; i++) {
	            if(currentZeroIndex == -1) {
	                if(nums[i] == 0) {
	                    currentZeroIndex = i;
	                } else {
	                    continue;
	                }
	            } else {
	                if(nums[i] == 0) {
	                    continue;
	                } else {
	                    nums[currentZeroIndex] = nums[i];
	                    currentZeroIndex++;
	                }
	            }
	        }
	        if(currentZeroIndex <= 0 || currentZeroIndex >= nums.length) {
	            return;
	        }
	        for(int i=currentZeroIndex; i<nums.length; i++) {
	            nums[i] = 0;
	        }
	    }
	}
}
