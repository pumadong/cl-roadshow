package com.cl.roadshow.leetcode;

/**
 * 求一个链接是否存在循环
 * https://leetcode.com/problems/linked-list-cycle-ii/
 * @author dongyongjin
 *
 */
public class Leetcode142 {
	public static void main(String[] args) {
		Leetcode142 lc = new Leetcode142();
		Solution solution = lc.new Solution();
		System.out.println(solution.detectCycle(ListNode.Cycle.getHead()));
	}
	// http://blog.sina.com.cn/s/blog_6f611c300101fs1l.html
	// 实际 a = c + n圈，但是从重合点，多跑n圈，相遇点不变
	public class Solution {
	    public ListNode detectCycle(ListNode head) {
	        if(head == null) {
	            return null;
	        }
	        ListNode fast = head;
	        ListNode slow = head;
	        // 重合前走的次数（处理只有两步的特殊情况）
	        int num = 0;
	        while(fast != null) {
	            if(fast.next == null || fast.next.next == null) {
	                return null;
	            }
	            fast = fast.next.next;
	            slow = slow.next;
	            num++;
	            if(fast == slow) {
	                slow = head;
	                break;
	            }   
	        }
	        if(num == 2) {
	            return head;
	        }
	        while(true) {
	            fast = fast.next;
	            slow = slow.next;
	            if(fast == slow) {
	                return fast;
	            }
	        }
	    }
	}
}
