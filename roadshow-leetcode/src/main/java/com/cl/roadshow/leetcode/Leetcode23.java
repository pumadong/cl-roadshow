package com.cl.roadshow.leetcode;

/**
 * Merge k Sorted Lists 
 * https://leetcode.com/problems/merge-k-sorted-lists/
 * 
 * @author dongyongjin
 *
 */
public class Leetcode23 {

	public static void main(String[] args) {
		Leetcode23 lc = new Leetcode23();
		Solution solution = lc.new Solution();
		ListNode[] lists = new ListNode[2];
		lists[0] = ListNode.General.getHeadA();
		lists[1] = ListNode.General.getHeadB();
		System.out.println(solution.mergeKLists(lists));
	}

	// http://www.tuicool.com/articles/ZnuEVfJ
	public class Solution {
		public ListNode mergeKLists(ListNode[] lists) {
			if (lists == null || lists.length == 0) {
				return null;
			}
			return helper(lists, 0, lists.length - 1);
		}

		private ListNode helper(ListNode[] lists, int l, int r) {
			if (l < r) {
				int m = (l + r) / 2;
				return merge(helper(lists, l, m), helper(lists, m + 1, r));
			}
			return lists[l];
		}

		private ListNode merge(ListNode l1, ListNode l2) {
			ListNode dummy = new ListNode(0);
			dummy.next = l1;
			ListNode cur = dummy;
			while (l1 != null && l2 != null) {
				if (l1.val < l2.val) {
					l1 = l1.next;
				} else {
					ListNode next = l2.next;
					cur.next = l2;
					l2.next = l1;
					l2 = next;
				}
				cur = cur.next;
			}
			if (l2 != null)
				cur.next = l2;
			return dummy.next;
		}
	}
}
