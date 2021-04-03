package problems;

import datastructures.IntTree;
// Checkstyle will complain that this is an unused import until you use it in your code.
import datastructures.IntTree.IntTreeNode;

/**
 * See the spec on the website for tips and example behavior.
 *
 * Also note: you may want to use private helper methods to help you solve these problems.
 * YOU MUST MAKE THE PRIVATE HELPER METHODS STATIC, or else your code will not compile.
 * This happens for reasons that aren't the focus of this assignment and are mostly skimmed over in
 * 142 and 143. If you want to know more, you can ask on the discussion board or at office hours.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `IntTree` objects
 * - do not construct new `IntTreeNode` objects (though you may have as many `IntTreeNode` variables
 *      as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the tree only by modifying
 *      links between nodes.
 */

public class IntTreeProblems {

    /**
     * Computes and returns the sum of the values multiplied by their depths in the given tree.
     * (The root node is treated as having depth 1.)
     */
    public static int depthSum(IntTree tree) {

        return dfs(tree.overallRoot, 1);

    }

    private static int dfs(IntTreeNode root, int depth) {

        if (root == null) {
            return 0;
        } else {

            return depth * root.data + dfs(root.left, depth + 1) + dfs(root.right, depth + 1);

        }

    }


    /**
     * Removes all leaf nodes from the given tree.
     */
    public static void removeLeaves(IntTree tree) {

        if (tree.overallRoot == null) {
            return;
        } else if (tree.overallRoot.left == null && tree.overallRoot.right == null) {
            tree.overallRoot = null;
        }
        removeLeaves(tree.overallRoot);
    }

    private static void removeLeaves(IntTreeNode tree) {
        if (tree == null) {
            return;
        } else {

            IntTreeNode left = tree.left;
            boolean leftChildIsALeaf = left != null && left.left == null && left.right == null;
            if (leftChildIsALeaf) {
                tree.left = null;
            }

            IntTreeNode right = tree.right;
            boolean rightChildIsALeaf = right != null && right.left == null && right.right == null;
            if (rightChildIsALeaf) {
                tree.right = null;
            }

            removeLeaves(tree.left);
            removeLeaves(tree.right);

        }
    }

    /**
     * Removes from the given BST all values less than `min` or greater than `max`.
     * (The resulting tree is still a BST.)
     */
    public static void trim(IntTree tree, int min, int max) {

        tree.overallRoot = traverse(tree.overallRoot, min, max);

    }

    private static IntTreeNode traverse(IntTreeNode node, int min, int max) {

        if (node == null) {
            return null;
        } else if (node.data < min) { //push ourselves closer to range
            return traverse(node.right, min, max);
        } else if (max < node.data) {
            return traverse(node.left, min, max);
        } else {
            //if we are in range, trim the left and right subtrees
            node.left = traverse(node.left, min, max);
            node.right = traverse(node.right, min, max);
            return node;
        }


    }








}
