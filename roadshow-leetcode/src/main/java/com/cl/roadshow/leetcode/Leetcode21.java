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
		System.out.println(solution.mergeTwoLists(ListNode.Intersection.getHeadA(), ListNode.Intersection.getHeadB()));
	}
	
	public class Solution {
	    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
	        ListNode res = new ListNode(0);
	        ListNode head = res;
	        while(l1!=null || l2!=null) {
	        	// 处理有交点的链表，这种情况如果不处理，会有死循环
            	if(l1 != null && l1.next == l2) {
            		res.next = l1;
            		break;
            	}
            	if(l2 != null & l2.next == l1) {
            		res.next = l2;
            		break;
            	}
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
