package com.cl.roadshow.leetcode;

/**
 * 链表中的节点相加
 * https://leetcode.com/problems/add-two-numbers/
 * @author dongyongjin
 *
 */
public class Leetcode2 {
	
	public static void main(String[] args) {
		Leetcode2 lc = new Leetcode2();
		Solution solution = lc.new Solution();
		System.out.println(solution.addTwoNumbers(ListNode.General.getHeadA(), ListNode.General.getHeadB()));
	}
	
	public class Solution {
	    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
	        // 进位
	        int carry = 0;
	        ListNode resultHead = new ListNode(-1);
	        ListNode p1 = l1, p2 = l2, p3 = resultHead;
	        while(p1 != null || p2 != null) {
	            if(p1 != null) {
	                carry += p1.val;
	                p1 = p1.next;
	            }
	            if(p2 != null) {
	                carry += p2.val;
	                p2 = p2.next;
	            }
	            p3.next = new ListNode(carry % 10);
	            p3 = p3.next;
	            carry = carry / 10;
	        }
	        if(carry == 1) {
	            p3.next = new ListNode(1);
	        }
	        return resultHead.next;
	    }
	}

}
