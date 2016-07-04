package com.cl.roadshow.leetcode;

/**
 * Sum of Two Integers
 * https://leetcode.com/problems/sum-of-two-integers/
 * @author dongyongjin
 *
 */
public class Leetcode371 {
	public static void main(String[] args) {
		Leetcode371 lc = new Leetcode371();
		System.out.println(lc.getSum(3, 8));
	}
	public int getSum(int a, int b) {
		int result = a ^ b;	// 异或，相同取0，相反取1
		int carry = (a & b) << 1;	// 与操作后左移，相当于所有的进位
		if(carry != 0) {
			return getSum(result, carry);
		}
		return result;
    }

}
