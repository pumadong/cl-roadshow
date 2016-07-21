package com.cl.roadshow.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 3Sum
 * https://leetcode.com/problems/3sum/
 * @author dongyongjin
 *
 */
public class Leetcode15 {
	
	public static void main(String[] args) {
		Leetcode15 lc = new Leetcode15();
		Solution solution = lc.new Solution();
		System.out.println(solution.threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
	}
	
	// http://www.jiuzhang.com/solutions/3sum/
	// http://www.sigmainfy.com/blog/summary-of-ksum-problems.html
	public class Solution {
	    public List<List<Integer>> threeSum(int[] nums) {
	    	List<List<Integer>> result = new ArrayList<List<Integer>>();
	    	if(nums == null || nums.length < 3) {
	    		return result;
	    	}
	    	Arrays.sort(nums);
	    	for(int i = 0; i < nums.length - 2; i++) {
	    		if(i != 0 && nums[i] == nums[i-1]) {
	    			continue;	// to skip duplicate numbers; e.g [0,0,0,0]
	    		}
	    		int left = i + 1;
	    		int right = nums.length - 1;
	    		while(left < right) {
	    			int sum = nums[i] + nums[left] + nums[right];
	    			if(sum == 0) {
	    				List<Integer> tmp = new ArrayList<Integer>();
	    				tmp.add(nums[i]);
	    				tmp.add(nums[left]);
	    				tmp.add(nums[right]);
	    				result.add(tmp);
	    				left++;
	    				right--;
	    				while(left < right && nums[left] == nums[left-1]) {
	    					left++;	// to skip duplicates
	    				}
	    				while(left < right && nums[right] == nums[right+1]) {
	    					right--; // to skip duplicates
	    				}
	    				
	    			} else if(sum < 0) {
	    				left++;
	    			} else {
	    				right--;
	    			}
	    		}
	    	}
	    	return result;
	    }
	}
}
