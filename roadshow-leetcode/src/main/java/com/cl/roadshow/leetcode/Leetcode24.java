package com.cl.roadshow.leetcode;

/**
 * 单链接交换相邻的节点
 * https://leetcode.com/problems/swap-nodes-in-pairs/
 * @author dongyongjin
 *
 */
public class Leetcode24 {

	public static void main(String[] args) {
		Leetcode24 lc = new Leetcode24();
		Solution solution = lc.new Solution();
		System.out.println(solution.swapPairs(ListNode.General.getHeadA()));
	}
	
	public class Solution {
	    public ListNode swapPairs(ListNode head) {
	        if(head == null || head.next == null) {
	            return head;
	        }
	        ListNode top = head.next;
	        ListNode first = new ListNode(0);
	        while(head != null) {
	            if(head.next == null) {
	            	first.next = head;
	            	first = head;
	                break;
	            }
	            // first代表上一组的最后一个元素，用来和本组的第一个元素链起来
	            ListNode next = head.next;
	            first.next = next;
	            first = head;
	            
	            // 本组的两个元素交互，并把head指向下一组的第一个元素
	            ListNode nextHead = next.next;
	            next.next = head;
	            head = nextHead;
	        }
	        first.next = null;
	        return top;
	    }
	}
}
