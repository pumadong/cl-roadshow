package com.cl.roadshow.leetcode;

/**
 * Definition for a binary tree node.
 * @author dongyongjin
 *
 */
public class TreeNode {
	int val;
	TreeNode left;
	TreeNode right;

	TreeNode(int x) {
		val = x;
	}
	
	@Override
	public String toString() {
		return "TreeNode [val=" + val + ", left=" + left + ", right=" + right + "]";
	}

	static class BinaryTree {
		/*
		 * 
				_______6______
		       /              \
		    ___2__          ___8__
		   /      \        /      \
		   0      _4       7       9
		         /  \
		         3   5
		 * 
		 * */
		
		static TreeNode[] nodes;
		
		static {
			int[] nums = new int[]{0,2,3,4,5,6,7,8,9};
			nodes = new TreeNode[10];
			for(int i = 0; i < nums.length; i++) {
				nodes[i] = new TreeNode(i);
			}
			nodes[6].left = nodes[2]; nodes[6].right = nodes[8];
			nodes[2].left = nodes[0]; nodes[2].right = nodes[4];
			nodes[4].left = nodes[3]; nodes[4].right = nodes[5];
			nodes[8].left = nodes[7]; nodes[8].right = nodes[9];
		}
		
		static TreeNode getRoot() {
			return nodes[6];
		}
		
		static TreeNode getP() {
			return nodes[0];
		}
		
		static TreeNode getQ() {
			return nodes[3];
		}
	}
}
