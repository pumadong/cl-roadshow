package com.cl.roadshow.leetcode;

/**
 * Definition for singly-linked list.
 * 
 * @author dongyongjin
 *
 */
public class ListNode {
	int val;
	ListNode next;

	ListNode(int x) {
		val = x;
		next = null;
	}
	
	@Override
	public String toString() {
		return "ListNode [val=" + val + ", next=" + next + "]";
	}

	static class Intersection {
		
		/*
		 * 
	 	A:          a1 → a2
		                   ↘
		                     c1 → c2 → c3
		                   ↗            
		B:     b1 → b2 → b3
		 * */
		
		static ListNode[] nodeA;
		static ListNode[] nodeB;
		
		static {
			nodeA = new ListNode[5];
			for(int i = 4; i >= 0; i--) {
				nodeA[i] = new ListNode(i);
				if( i != 4) {
					nodeA[i].next = nodeA[i+1];
				}
			}
			nodeB = new ListNode[3];
			for(int i = 2; i >= 0; i--) {
				nodeB[i] = new ListNode(i);
				if( i != 2) {
					nodeB[i].next = nodeB[i+1];
				}
			}
			nodeB[2].next = nodeA[2];
		}
		
		static ListNode getHeadA() {
			return nodeA[0];
		}
		
		static ListNode getHeadB() {
			return nodeB[0];
		}
	}
}
