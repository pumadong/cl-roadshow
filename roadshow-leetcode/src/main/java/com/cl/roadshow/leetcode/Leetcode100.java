package com.cl.roadshow.leetcode;

/**
 * 判断两棵树是否相等
 * https://leetcode.com/problems/same-tree/
 * @author dongyongjin
 *
 */
public class Leetcode100 {
	
	public static void main(String[] args) {
		Leetcode100 lc = new Leetcode100();
		Solution solution = lc.new Solution();
		System.out.println(solution.isSameTree(TreeNode.BinaryTree.getRoot(), TreeNode.BinaryTree.getRoot()));
		System.out.println(solution.isSameTree(TreeNode.BinaryTree.getP(), TreeNode.BinaryTree.getQ()));
	}

	public class Solution {
	    public boolean isSameTree(TreeNode p, TreeNode q) {
			 if(p == q) {
				 return true;
			 }
			 if(p != null && q != null) {
				 return p.val == q.val && isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
			 }
			 return false;
	    }
	}
}
