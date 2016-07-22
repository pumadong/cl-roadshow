package com.cl.roadshow.leetcode;

import java.util.Arrays;

/**
 * 3Sum Closest
 * https://leetcode.com/problems/3sum-closest/
 * @author dongyongjin
 *
 */
public class Leetcode16 {

	public static void main(String[] args) {
		Leetcode16 lc = new Leetcode16();
		Solution solution = lc.new Solution();
		System.out.println(solution.threeSumClosest(new int[]{-1, 2, 1, -4, 1}, 1));
	}
	
	public class Solution {
	    public int threeSumClosest(int[] nums, int target) {
	    	if(nums == null || nums.length < 3) {
	    		return 0;
	    	}
	    	Arrays.sort(nums);
	    	int closestSum = Integer.MAX_VALUE;	// 最接近的3Sum的和
	    	int closestDiff  = Integer.MAX_VALUE;	// 最接近的3Sum和Target的Difference
	    	for(int i = 0; i < nums.length -2; i++) {
	    		if(i != 0 && nums[i] == nums[i-1]) {
	    			continue;
	    		}
	    		int left = i + 1;
	    		int right = nums.length - 1;
	    		while(left < right) {
	    			int sum = nums[i] + nums[left] + nums[right];
	    			if(sum < target) {
	    				if(target - sum < closestDiff) {
	    					closestDiff = target - sum;
	    					closestSum = sum;
	    				}
	    				left++;
	    			} else if(sum > target) {
	    				if(sum - target < closestDiff) {
	    					closestDiff = sum - target;
	    					closestSum = sum;
	    				}
	    				right--;
	    			} else {
	    				return sum;
	    			}
	    		}
	    	}
	        return closestSum;
	    }
	}
}
