package com.cl.roadshow.leetcode;

/**
 * 合并排序链表
 * https://leetcode.com/problems/merge-two-sorted-lists/
 * @author dongyongjin
 *
 */
public class Leetcode21 {
	
	public static void main(String[] args) {
		Leetcode21 lc = new Leetcode21();
		Solution solution = lc.new Solution();
		System.out.println(solution.mergeTwoLists(ListNode.General.getHeadA(), ListNode.General.getHeadB()));
	}
	
	public class Solution {
		// 两个链表有交点时，代码有问题，需要优化
	    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
	        ListNode res = new ListNode(0);
	        ListNode head = res;
	        while(l1!=null || l2!=null) {
	            if(l1!=null && l2!=null) {
	                if(l1.val > l2.val) {
	                    res.next = l2;
	                    l2 = l2.next;
	                } else {
	                    res.next = l1;
	                    l1 = l1.next;
	                }
	                res = res.next;
	            } else if(l1!=null) {
	                res.next = l1;
	                break;
	            } else if(l2!=null) {
	                res.next = l2;
	                break;
	            }
	        }
	        return head.next;
	    }
	}
}
