package com.cl.roadshow.leetcode;

/**
 * 数组中的两个数相加等于目标，求下标
 * https://leetcode.com/problems/two-sum/
 * @author dongyongjin
 *
 */
public class Leetcode1 {
	
	public static void main(String[] args) {
		Leetcode1 lc = new Leetcode1();
		Solution solution = lc.new Solution();
		int[] result = solution.twoSum(new int[]{2, 7, 11, 15}, 18);
		if(result != null) {
			for(int i : result) {
				System.out.println(String.format("%d", i));
			}
		}
	}

	public class Solution {
	    public int[] twoSum(int[] nums, int target) {
	        for(int i = 0; i < nums.length; i++) {
	            for(int j = i+1; j < nums.length; j++) {
	                if(nums[i] + nums[j] == target) {
	                    return new int[]{i,j};
	                }
	            }
	        }
	        return null;
	    }
	}
}
