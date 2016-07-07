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
	
	boolean hasCycle = false;
	
	@Override
	public String toString() {
		if(hasCycle) {
			return "ListNode [val=" + val + "]";
		} else {
			return "ListNode [val=" + val + ", next=" + next + "]";
		}
	}
	
	static class General {
		/*
		 * 
	 	A:          a1 → a2 → a3 → a4 → a4
		B:     b1 → b2 → b3 → b4 → b5 → b6
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
			nodeB = new ListNode[6];
			for(int i = 5; i >= 0; i--) {
				nodeB[i] = new ListNode(i);
				if( i != 5) {
					nodeB[i].next = nodeB[i+1];
				}
			}
		}
		
		static ListNode getHeadA() {
			return nodeA[0];
		}
		
		static ListNode getHeadB() {
			return nodeB[0];
		}
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
	
	static class Cycle {
		/**
		 0,1,2,3,4,5->3
		 */
		static ListNode getHead() {
			ListNode[] node = new ListNode[6];
			for(int i = 5; i >= 0; i--) {
				node[i] = new ListNode(i);
				node[i].hasCycle = true;
				if( i != 5) {
					node[i].next = node[i+1];
				}
			}
			node[5].next = node[3];
			return node[0];
		}
	}
}
