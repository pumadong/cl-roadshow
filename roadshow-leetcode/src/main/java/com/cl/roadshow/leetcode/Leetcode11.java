package com.cl.roadshow.leetcode;

/**
 * 包含最多水的容器
 * https://leetcode.com/problems/container-with-most-water/
 * @author dongyongjin
 *
 */
public class Leetcode11 {

	public static void main(String[] args) {
		Leetcode11 lc = new Leetcode11();
		Solution solution = lc.new Solution();
		System.out.println(solution.maxArea(new int[]{1,3,6,11,99,3,4,7}));
	}
	
	// http://blog.csdn.net/ljiabin/article/details/41673753
	public class Solution {
	    public int maxArea(int[] height) {
	        if(height.length < 2) {
	            return 0;
	        }
	        int area = 0;
	        int left = 0;
	        int right = height.length -1;
	        while(left < right) {
	            int tempArea = (right-left) * Math.min(height[left],height[right]);
	            if(tempArea > area) {
	                area = tempArea;
	            }
	            // 木桶能盛多少水，取决于最短的那个线段
	            if(height[left] < height[right]) {
	                left++;
	            } else {
	                right--;
	            }
	        }
	        return area;
	    }
	}
}
