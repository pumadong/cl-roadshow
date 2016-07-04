package com.cl.roadshow.leetcode;

/**
 * 数组范围求和
 * https://leetcode.com/problems/range-sum-query-immutable/
 * @author dongyongjin
 *
 */
public class Leetcode303 {
	
	public static void main(String[] args) {
		Leetcode303 lc = new Leetcode303();
		NumArray na = lc.new NumArray(new int[]{-2, 0, 3, -5, 2, -1});
		System.out.println(na.sumRange(0, 2));
	}
	
	class NumArray {

		private int[] nums;
		
	    public NumArray(int[] nums) {
	        this.nums = nums;
	    }

	    public int sumRange(int i, int j) {
	        if(i < 0 || j >= nums.length) {
	        	return 0;
	        }
	        int result = 0;
	        for(int index = i; index <= j; index++) {
	        	result += nums[index];
	        }
	        return result;
	    }
	}
}
