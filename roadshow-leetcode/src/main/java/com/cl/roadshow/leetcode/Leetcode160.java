package com.cl.roadshow.leetcode;

/**
 * 求两个链表是否有交点
 * https://leetcode.com/problems/intersection-of-two-linked-lists/
 * @author dongyongjin
 *
 */
public class Leetcode160 {

	public static void main(String[] args) {
		Leetcode160 lc = new Leetcode160();
		Solution solution = lc.new Solution();
		ListNode ln = solution.getIntersectionNode(ListNode.Intersection.getHeadA(), ListNode.Intersection.getHeadB());
		System.out.println(ln);
		System.out.println(ListNode.Intersection.getHeadA());
		System.out.println(ListNode.Intersection.getHeadB());
	}
	
	public class Solution {
	    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
			if(headA == null | headB == null) {
				return null;
			}
			ListNode l1 = headA;
			ListNode l2 = headB;
			int c1 = 1, c2 = 1; 
			while(l1.next != null) {
				c1++;
				l1 = l1.next;
			}
			while(l2.next != null) {
				c2++;
				l2 = l2.next;
			}
			if(l1 != l2) {
				return null;
			}
			l1 = headA;
			l2 = headB;
			int step = Math.abs(c1 - c2);
			if(c1 > c2) {
				for(int i = 0; i < step; i++) {
					l1 = l1.next;
				}
			} else {
				for(int i = 0; i  < step; i++) {
					l2 = l2.next;
				}
			}
			if(l1 == l2) {
				return l1;
			}
			while(l1.next != null && l2.next != null) {
				l1 = l1.next;
				l2 = l2.next;
				if(l1 == l2) {
					return l1;
				}
			}
			return null;
	    }
	}
}
