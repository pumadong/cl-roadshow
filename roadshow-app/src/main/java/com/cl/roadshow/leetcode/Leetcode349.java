package com.cl.roadshow.leetcode;

import java.util.HashSet;

/**
 * 两个数组的交集：结果唯一
 * https://leetcode.com/problems/intersection-of-two-arrays/
 * @author dongyongjin
 *
 */
public class Leetcode349 {
	public static void main(String[] args) {
		int[] nums1 = new int[]{1, 2, 2, 1};
		int[] nums2 = new int[]{2, 2};
		
		Leetcode349 lc = new Leetcode349();
		int[] result = lc.intersection(nums1, nums2);
		
		for(int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}
	}
	
    public int[] intersection(int[] nums1, int[] nums2) {
    	HashSet<Integer> hsNums1 = new HashSet<Integer>();
    	HashSet<Integer> hsNums2 = new HashSet<Integer>();
    	for(int i = 0; i < nums1.length; i++) {
    		hsNums1.add(nums1[i]);
    	}
    	for(int i = 0; i < nums2.length; i++) {
    		hsNums2.add(nums2[i]);
    	}
    	hsNums1.retainAll(hsNums2);
    	int result[] = new int[hsNums1.size()];
    	int i = 0;
    	for(Integer v : hsNums1) {
    		result[i++] = v;
    	}
    	return result;
    }
}
