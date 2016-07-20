package com.cl.roadshow.leetcode;

/**
 * Median of Two Sorted Arrays
 * https://leetcode.com/problems/median-of-two-sorted-arrays/
 * @author dongyongjin
 *
 */
public class Leetcode4 {
	
	public static void main(String[] args) {
		Leetcode4 lc = new Leetcode4();
		Solution solution = lc.new Solution();
		System.out.println(solution.findMedianSortedArrays(new int[]{1,2,3,4,5,6,7}, new int[]{1,2,3,4,5,6,7}));
		System.out.println(solution.findMedianSortedArrays(new int[]{1,2,3,4,5,6,7}, new int[]{8,9,10,11,12,13,14}));
	}
	
	// http://www.jiuzhang.com/solutions/median-of-two-sorted-arrays/
	// https://www.youtube.com/watch?v=_H50Ir-Tves
	// http://blog.csdn.net/linhuanmars/article/details/19905515
	public class Solution {
	    public double findMedianSortedArrays(int A[], int B[]) {
	        int len = A.length + B.length;
	        if (len % 2 == 1) {
	            return findKth(A, 0, B, 0, len / 2 + 1);
	        }
	        return (
	            findKth(A, 0, B, 0, len / 2) + findKth(A, 0, B, 0, len / 2 + 1)
	        ) / 2.0;
	    }

	    // find kth number of two sorted array
	    public int findKth(int[] A, int A_start,
	                              int[] B, int B_start,
	                              int k){		
			if (A_start >= A.length) {
				return B[B_start + k - 1];
			}
			if (B_start >= B.length) {
				return A[A_start + k - 1];
			}

			if (k == 1) {
				return Math.min(A[A_start], B[B_start]);
			}
			
			int A_key = A_start + k / 2 - 1 < A.length
			            ? A[A_start + k / 2 - 1]
			            : Integer.MAX_VALUE;
			int B_key = B_start + k / 2 - 1 < B.length
			            ? B[B_start + k / 2 - 1]
			            : Integer.MAX_VALUE; 
			
			if (A_key < B_key) {
				return findKth(A, A_start + k / 2, B, B_start, k - k / 2);
			} else {
				return findKth(A, A_start, B, B_start + k / 2, k - k / 2);
			}
		}
	}
}
