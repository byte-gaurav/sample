package org.sample.dsa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class LowestCommonAncestor {

    public static void main(String[] args) {
        // Create the binary tree:
        //       3
        //     /   \
        //    5     1
        //   / \   / \
        //  6   2 0   8
        //     / \
        //    7   4
        TreeNode root = createLargeBinaryTree();
        Long start = System.currentTimeMillis();
        TreeNode ancestor = new LowestCommonAncestor().lowestCommonAncestor(root, 7, 100, root);
        System.out.printf("Result : %s, Result took %d ms%n", ancestor == null ? "null" : ancestor.val, System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        TreeNode ancestorIterative = new LowestCommonAncestor().lowestCommonAncestorIterative(root, 7, 100);
        System.out.printf("Result : %s, Result took %d ms%n", ancestorIterative == null ? "null" : ancestorIterative.val, System.currentTimeMillis() - start);
    }

    private TreeNode lowestCommonAncestor(TreeNode root, int p, int q, TreeNode head) {
        if (root == null || root.val == p || root.val == q) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q, head);
        TreeNode right = lowestCommonAncestor(root.right, p, q, head);

        if (left != null && right != null) {
            return root;
        } else {
            if (root == head) {
                return null;
            } else {
                return left != null ? left : right;
            }
        }
    }

    private TreeNode lowestCommonAncestorIterative(TreeNode root, int p, int q) {
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        Stack<TreeNode> stack = new Stack<>();
        parent.put(root, null);
        stack.push(root);

        // Find nodes and build parent pointers
        TreeNode pNode = null;
        TreeNode qNode = null;

        while (!stack.isEmpty() && (pNode == null || qNode == null)) {
            TreeNode node = stack.pop();

            if (node.val == p) pNode = node;
            if (node.val == q) qNode = node;

            if (node.right != null) {
                parent.put(node.right, node);
                stack.push(node.right);
            }
            if (node.left != null) {
                parent.put(node.left, node);
                stack.push(node.left);
            }
        }

        // If one of the nodes was not found
        if (pNode == null || qNode == null) return null;

        // Track ancestors of p
        Set<TreeNode> ancestors = new HashSet<>();
        while (pNode != null) {
            ancestors.add(pNode);
            pNode = parent.get(pNode);
        }

        // Find the first ancestor of q that is also an ancestor of p
        while (!ancestors.contains(qNode)) {
            qNode = parent.get(qNode);
        }

        return qNode;
    }

    static class TreeNode {
        protected int val;
        protected TreeNode left;
        protected TreeNode right;
        TreeNode(int x) { val = x; }
    }

    private static TreeNode createLargeBinaryTree() {
        // Create a large binary tree with at least 150 nodes
        TreeNode root = new TreeNode(50);

        // Create first level
        root.left = new TreeNode(25);
        root.right = new TreeNode(75);

        // Create second level
        root.left.left = new TreeNode(12);
        root.left.right = new TreeNode(37);
        root.right.left = new TreeNode(62);
        root.right.right = new TreeNode(87);

        // Create a complete binary tree with remaining nodes
        // Level 3
        root.left.left.left = new TreeNode(6);
        root.left.left.right = new TreeNode(18);
        root.left.right.left = new TreeNode(31);
        root.left.right.right = new TreeNode(43);
        root.right.left.left = new TreeNode(56);
        root.right.left.right = new TreeNode(68);
        root.right.right.left = new TreeNode(81);
        root.right.right.right = new TreeNode(93);

        // Level 4 (adding 16 more nodes)
        root.left.left.left.left = new TreeNode(3);
        root.left.left.left.right = new TreeNode(9);
        root.left.left.right.left = new TreeNode(15);
        root.left.left.right.right = new TreeNode(21);
        root.left.right.left.left = new TreeNode(28);
        root.left.right.left.right = new TreeNode(34);
        root.left.right.right.left = new TreeNode(40);
        root.left.right.right.right = new TreeNode(46);
        root.right.left.left.left = new TreeNode(53);
        root.right.left.left.right = new TreeNode(59);
        root.right.left.right.left = new TreeNode(65);
        root.right.left.right.right = new TreeNode(71);
        root.right.right.left.left = new TreeNode(78);
        root.right.right.left.right = new TreeNode(84);
        root.right.right.right.left = new TreeNode(90);
        root.right.right.right.right = new TreeNode(96);

        // Level 5 (adding 32 more nodes)
        root.left.left.left.left.left = new TreeNode(1);
        root.left.left.left.left.right = new TreeNode(4);
        root.left.left.left.right.left = new TreeNode(7);
        root.left.left.left.right.right = new TreeNode(10);
        root.left.left.right.left.left = new TreeNode(13);
        root.left.left.right.left.right = new TreeNode(16);
        root.left.left.right.right.left = new TreeNode(19);
        root.left.left.right.right.right = new TreeNode(22);
        root.left.right.left.left.left = new TreeNode(26);
        root.left.right.left.left.right = new TreeNode(29);
        root.left.right.left.right.left = new TreeNode(32);
        root.left.right.left.right.right = new TreeNode(35);
        root.left.right.right.left.left = new TreeNode(38);
        root.left.right.right.left.right = new TreeNode(41);
        root.left.right.right.right.left = new TreeNode(44);
        root.left.right.right.right.right = new TreeNode(47);

        root.right.left.left.left.left = new TreeNode(51);
        root.right.left.left.left.right = new TreeNode(54);
        root.right.left.left.right.left = new TreeNode(57);
        root.right.left.left.right.right = new TreeNode(60);
        root.right.left.right.left.left = new TreeNode(63);
        root.right.left.right.left.right = new TreeNode(66);
        root.right.left.right.right.left = new TreeNode(69);
        root.right.left.right.right.right = new TreeNode(72);
        root.right.right.left.left.left = new TreeNode(76);
        root.right.right.left.left.right = new TreeNode(79);
        root.right.right.left.right.left = new TreeNode(82);
        root.right.right.left.right.right = new TreeNode(85);
        root.right.right.right.left.left = new TreeNode(88);
        root.right.right.right.left.right = new TreeNode(91);
        root.right.right.right.right.left = new TreeNode(94);
        root.right.right.right.right.right = new TreeNode(97);

        // Level 6 (adding more nodes to reach at least 150)
        root.left.left.left.left.left.left = new TreeNode(0);
        root.left.left.left.left.left.right = new TreeNode(2);
        root.left.left.left.left.right.left = new TreeNode(3);
        root.left.left.left.left.right.right = new TreeNode(5);
        root.left.left.left.right.left.left = new TreeNode(6);
        root.left.left.left.right.left.right = new TreeNode(8);
        root.left.left.left.right.right.left = new TreeNode(9);
        root.left.left.left.right.right.right = new TreeNode(11);

        root.right.right.right.right.right.left = new TreeNode(95);
        root.right.right.right.right.right.right = new TreeNode(98);
        root.right.right.right.right.left.left = new TreeNode(92);
        root.right.right.right.right.left.right = new TreeNode(93);
        root.right.right.right.left.right.left = new TreeNode(89);
        root.right.right.right.left.right.right = new TreeNode(90);
        root.right.right.right.left.left.left = new TreeNode(86);
        root.right.right.right.left.left.right = new TreeNode(87);

        // Add more nodes to reach 150+ total
        root.left.left.left.left.left.left.left = new TreeNode(100);
        root.left.left.left.left.left.left.right = new TreeNode(101);
        root.left.left.left.left.left.right.left = new TreeNode(102);
        root.left.left.left.left.left.right.right = new TreeNode(103);
        root.left.left.left.left.right.left.left = new TreeNode(104);
        root.left.left.left.left.right.left.right = new TreeNode(105);
        root.left.left.left.left.right.right.left = new TreeNode(106);
        root.left.left.left.left.right.right.right = new TreeNode(107);

        return root;
    }
}
