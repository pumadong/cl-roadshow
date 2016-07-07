package com.cl.roadshow.leetcode;

/**
 * 不用/和%，计算两个数相除
 * 注意各种临界值的处理
 * https://leetcode.com/problems/divide-two-integers/
 * @author dongyongjin
 *
 */
public class Leetcode29 {
	public static void main(String[] args) {
		Leetcode29 lc = new Leetcode29();
		Solution solution = lc.new Solution();
		System.out.println(solution.divide(20, 3));
		System.out.println(solution.divide(2147483647, 1));
	}
	// http://www.tuicool.com/articles/FNrUvyE
	// O(N^2)->O(N) 分解成有多少个：divisor * 2^N + divisor * 2^(N-1) + ...  + divisor * 2^0s
	public class Solution {
	    public int divide(int dividend, int divisor) {
			if(divisor == 0) {
				return Integer.MAX_VALUE;
			}
			int res = 0;
			if(dividend == Integer.MIN_VALUE) {
				if(divisor == -1) {
					return Integer.MAX_VALUE;
				}
				// 下面都要转成整数进行计算，"最小值"这个负数无法变成对应的正数，超出"最大值"
				res = 1;
				dividend += Math.abs(divisor);
			}
			if(divisor == Integer.MIN_VALUE) {
				return res;
			}
			boolean isNeg = (dividend^divisor)>>>31 == 1;
			dividend = Math.abs(dividend);
			divisor = Math.abs(divisor);
			int digit = 0;
			// dividend>>1的目的是避免溢出，如果本次计算divisor>dividend也没关系，下面的循环会进行判断
			while(divisor <= (dividend>>1)) {
				digit++;
				divisor <<= 1;
			}
			while(digit >= 0) {
				if(dividend >= divisor) {
					dividend -= divisor;
					res += 1<<digit;
				}
				divisor >>= 1;
				digit--;
			}
			return isNeg ? -res : res;
	    }
	}
}
