package com.cl.roadshow.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 两个数组的交集：结果不唯一
 * https://leetcode.com/problems/intersection-of-two-arrays-ii/
 * @author dongyongjin
 *
 */
public class Leetcode350 {
	public static void main(String[] args) {
		int[] nums1 = new int[]{1, 2, 2, 1};
		int[] nums2 = new int[]{2};
		
		Leetcode350 lc = new Leetcode350();
		int[] result = lc.intersection(nums1, nums2);
		
		for(int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}
	}
	
    public int[] intersection(int[] nums1, int[] nums2) {
    	HashMap<Integer,Integer> hmNums1 = new HashMap<Integer,Integer>();
    	HashMap<Integer,Integer> hmNums2 = new HashMap<Integer,Integer>();
    	for(int i = 0; i < nums1.length; i++) {
    		Integer count = hmNums1.get(nums1[i]);
    		if(count == null) {
    			count = 0;
    		}
    		count++;
    		hmNums1.put(nums1[i], count);
    	}
    	for(int i = 0; i < nums2.length; i++) {
    		Integer count = hmNums2.get(nums2[i]);
    		if(count == null) {
    			count = 0;
    		}
    		count++;
    		hmNums2.put(nums2[i], count);
    	}
    	int length = 0;
    	for(Map.Entry<Integer, Integer> entry : hmNums1.entrySet()) {
    		Integer value = hmNums2.get(entry.getKey());
    		if(value == null) {
    			hmNums1.put(entry.getKey(), 0);
    			continue;
    		}
    		value = value < entry.getValue() ? value : entry.getValue();
    		length += value;
    		hmNums1.put(entry.getKey(), value);
    	}
    	int[] result = new int[length];
    	int pos = 0;
    	for(Map.Entry<Integer, Integer> entry : hmNums1.entrySet()) {
    		for(int i = 0; i < entry.getValue(); i++) {
    			result[pos++] = entry.getKey();
    		}
    	}
    	return result;
    }
}
