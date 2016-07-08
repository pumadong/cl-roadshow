package com.cl.roadshow.leetcode;

/**
 * 从链表尾部开始移除第N个元素
 * https://leetcode.com/problems/remove-nth-node-from-end-of-list/
 * @author dongyongjin
 *
 */
public class Leetcode19 {

	public static void main(String[] args) {
		Leetcode19 lc = new Leetcode19();
		Solution solution = lc.new Solution();
		System.out.println(solution.removeNthFromEnd(ListNode.General.getHeadA(), 100));
		System.out.println(solution.removeNthFromEnd(ListNode.General.getHeadA(), 2));
		System.out.println(solution.removeNthFromEnd(ListNode.Intersection.getHeadA(), 100));
		System.out.println(solution.removeNthFromEnd(ListNode.Intersection.getHeadA(), 3));
	}
	
	public class Solution {
		// 代码依然有优化空间，如果是循环连接的话，要计算循环点，并进行特殊处理，防止进入死循环
	    public ListNode removeNthFromEnd(ListNode head, int n) {
	        if(head == null) {
	            return head;
	        }
	        int count = 1;
	        ListNode tempHead = head;
	        while(tempHead.next !=null) {
	        	count++;
	        	tempHead = tempHead.next;
	        }
	        // 正序的数目
	        n = count - n + 1;
	        if(n < 1 || n > count) {
	        	return head;
	        }
	        if (n == 1) {
	            return head.next;
	        }
	        int i = 1;
	        ListNode top = head;
	        while(head != null) {
	            if(++i == n) {
	                head.next = head.next == null ? null :head.next.next;
	                break;
	            }
	            head = head.next;
	        }
	        return top;
	    }
	}
}
