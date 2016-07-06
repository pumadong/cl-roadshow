package com.cl.roadshow.leetcode;

/**
 * 在二叉搜索树中，给定两个节点，求最低公共祖先
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
 * @author dongyongjin
 *
 */
public class Leetcode235 {

	public static void main(String[] args) {
		Leetcode235 lc = new Leetcode235();
		Solution solution = lc.new Solution();
		TreeNode ancestor = solution.lowestCommonAncestor(TreeNode.BinaryTree.getRoot(), TreeNode.BinaryTree.getP(), TreeNode.BinaryTree.getQ());
		System.out.println(ancestor);
	}
	
	public class Solution {
	    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
	        if(p.val <= root.val && q.val >= root.val || p.val >= root.val && q.val <= root.val) {
	            return root;
	        }
	        if(p.val < root.val && q.val < root.val) {
	            return lowestCommonAncestor(root.left, p, q);
	        } else {
	            return lowestCommonAncestor(root.right, p, q);
	        }
	    }
	}
}
