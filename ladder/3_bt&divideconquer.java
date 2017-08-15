/*
R1. Minimum Subtree 
Given a binary tree, find the subtree with minimum sum. Return the root of the subtree.

Example
Given a binary tree:

     1
   /   \
 -5     2
 / \   /  \
0   2 -4  -5 
return the node 1.
*/
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */
//O(N)
public class Solution {
    /**
     * @param root the root of binary tree
     * @return the root of the minimum subtree
     */
    public int minSum = Integer.MAX_VALUE;
    public TreeNode subtree = null;
    public TreeNode findSubtree(TreeNode root) {
        findSubtreeSum(root);
        return subtree;
    }
    public int findSubtreeSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftSum = findSubtreeSum(root.left);
        int rightSum = findSubtreeSum(root.right);
        int sum = leftSum + rightSum + root.val;
        if (sum < minSum) {
            subtree = root;
            minSum = sum;
        }
        return sum;
    }
}

/*
R2. Subtree with Maximum Average 
Given a binary tree, find the subtree with maximum average. Return the root of the subtree.

Notice
LintCode will print the subtree which root is your return node.
It's guaranteed that there is only one subtree with maximum average.

Example
Given a binary tree:
     1
   /   \
 -5     11
 / \   /  \
1   2 4    -2 
return the node 11.
*/
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 * 1. keep truck of sum and number of nodes, average = sum / nodes;
 * 2. Then keep track of the maximum average and corresponding root; 
 */
/**/
class ResultType {
    public int sum;
    public int nodesNum;
    public ResultType (int sum, int nodesNum){
        this.sum = sum;
        this.nodesNum = nodesNum;
    }
}
public class Solution {
    /**
     * @param root the root of binary tree
     * @return the root of the maximum average of subtree
     */
    public ResultType maxAverage = null; //必须存sum和nodes,直接存average精度会出问题
    public TreeNode maxRoot = null; //tack the max subtree root
    public TreeNode findSubtree2(TreeNode root) {
        findSubtree2Helper(root);
        return maxRoot;
    }
    public ResultType findSubtree2Helper(TreeNode root) {
        if (root == null) {
            return new ResultType(0, 0);
        }
        ResultType left = findSubtree2Helper(root.left);
        ResultType right = findSubtree2Helper(root.right);
        int sum = left.sum + right.sum + root.val;
        int nodesNum = left.nodesNum + right.nodesNum + 1;
        if (maxRoot == null || sum * maxAverage.nodesNum > maxAverage.sum * nodesNum) {
            maxRoot = root;
            maxAverage = new ResultType(sum, nodesNum);
        }
        return new ResultType(sum, nodesNum);
    }
}

/*
R3. Maximum Depth of Binary Tree 
Given a binary tree, find its maximum depth.

The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.

Example
Given a binary tree as follow:
  1
 / \ 
2   3
   / \
  4   5
The maximum depth is 3.
*/
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 * 
 */
//O(N)
public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: An integer.
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        if (left > right) {
            return left + 1;
        } else {
            return right + 1;
        }
    }
}
/*
R4. Binary Tree Inorder Traversal 
Given a binary tree, return the inorder traversal of its nodes' values.

Example
Given binary tree {1,#,2,3},
   1
    \
     2
    /
   3
return [1,3,2].
*/
public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: Inorder in ArrayList which contains node values.
     */
    /*Non-recursive*/
    public ArrayList<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> results = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        //一直向左走，放入stack，然后把最左放入result，到最左右子树，重复
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop(); //get leftmost
            results.add(curr.val);
            curr = curr.right; // go right
        }
        return results;
    }

    /*Recursively*/
    public ArrayList<Integer> results = new ArrayList<>();
    public ArrayList<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return results;
        }
        inorderTraversal(root.left);
        results.add(root.val);
        inorderTraversal(root.right);
        return results;
    }
    
}
/*
R5. Binary Tree Preorder Traversal 
Given a binary tree, return the preorder traversal of its nodes' values.
Example
Given:

    1
   / \
  2   3
 / \
4   5
return [1,2,4,5,3].
*/
public class Solution {
    /*Non-recursion, stack, 先right 后 left*/
    public ArrayList<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> results = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        if (root == null) {
            return results;
        }
        stack.push(root);
        while (!stack.isEmpty()) {
            root = stack.pop();
            results.add(root.val); //root
            /*必须先r再l,因为stack先入后出,不能用queue会导致变成层序*/
            if (root.right != null) {
                stack.push(root.right); 
            }
            if (root.left != null){
                stack.push(root.left);
            } 
        }
        return results;
    }
    /*Divide and Conquer*/
    public ArrayList<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> results = new ArrayList<>();
        if (root == null) {
            return results;
        }
        ArrayList<Integer> left = preorderTraversal(root.left);
        ArrayList<Integer> right = preorderTraversal(root.right);
        results.add(root.val); //root
        results.addAll(left); //left
        results.addAll(right); //right
        return results;
    }
}
/*
R6. Balanced Binary Tree 
Given a binary tree, determine if it is height-balanced.
For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.

Example
Given binary tree A = {3,9,20,#,#,15,7}, B = {3,#,20,15,7}

A)  3            B)    3 
   / \                  \
  9  20                 20
    /  \                / \
   15   7              15  7
The binary tree A is a height-balanced binary tree, but B is not.
*/
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 * recursion
 * at each recursion call:
 *  left = left subtree height;
 *  right = right subtree height;
 *  if is balanced:
 *      return max(left, right) + 1;
 *  else:
 *      return NOT_BALANCED.
 */
//O(N)
public class Solution {

    public int NOT_BALANCED = -1;
    public boolean isBalanced(TreeNode root) {
        int result = isBanlancedHelper(root);
        if (result == NOT_BALANCED) {
            return false;
        }
        return true;
    }
    private int isBanlancedHelper(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = isBanlancedHelper(root.left);
        int right = isBanlancedHelper(root.right);
        /*这里注意要检测左右是否balanced，如果不可以直接return not_balanced*/
        if (left == NOT_BALANCED || right == NOT_BALANCED) {
            return NOT_BALANCED;
        }
        if (Math.abs(left - right) > 1) {
            return NOT_BALANCED;
        }
        return Math.max(left, right) + 1;
    }
}
/*
R7. Flatten Binary Tree to Linked List 
Flatten a binary tree to a fake "linked list" in pre-order traversal. 
Here we use the right pointer in TreeNode as the next pointer in ListNode.
Notice
Don't forget to mark the left child of each node to null. Or you will get Time Limit Exceeded or Memory Limit Exceeded.

Example
              1
               \
     1          2
    / \          \
   2   5    =>    3
  / \   \          \
 3   4   6          4
                     \
                      5
                       \
                        6
*/
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 * 1. we first flatten the left subtree
 * 2. insert this flattened left linked list into root -> right
 * 
 */

/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 * 1. we first flatten the left subtree
 * 2. insert this flattened left linked list into root -> right
 * 
 */
public class Solution {

    public void flatten(TreeNode root) {
        flattenHelper(root);
    }
    private TreeNode flattenHelper(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode leftLast = flattenHelper(root.left);
        TreeNode rightLast = flattenHelper(root.right);
        /*这里注意leftlast返回的是左边flatten过后最后一个node
        rightLast是右边flatten后最后一个node所以是没用的
        */
        if (leftLast != null) {
            leftLast.right = root.right;
            root.right = root.left;
            root.left = null;
        }
        /*这里注意要return弄平后最后一个node,因为这个node是和下一个链接的
        rightLast如果不为空，最后一个就是rightLast，为空最后一个就是leftMost
        如果二者皆为空才是root是最后一个
        */
        if (rightLast != null) {
            return rightLast;
        }
        if (leftLast != null) {
            return leftLast;
        }
        return root;
    }
};
/*
R8. Binary Tree Paths 
Given a binary tree, return all root-to-leaf paths.

Example
Given the following binary tree:

   1
 /   \
2     3
 \
  5
All root-to-leaf paths are:

[
  "1->2->5",
  "1->3"
]
*/
public class Solution {
	/*Traverse*/
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> allPaths = new ArrayList<>();
        String path = "";
        binaryTreePathsHelper(allPaths, path, root);
        return allPaths;
    }
    private void binaryTreePathsHelper(List<String> allPaths, String path, TreeNode root) {
        if (root == null) {
            return;
        }
        path += root.val;
        if (root.left == null && root.right == null) {
            allPaths.add(path); // at leaf node
        }
        if (root.left != null) {
            binaryTreePathsHelper(allPaths, path + "->", root.left);
        }
        if (root.right != null) {
            binaryTreePathsHelper(allPaths, path + "->", root.right);
        }
    }
    /*Divide and Conquer*/
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> allPaths = new ArrayList<>();
        if (root == null) {
            return allPaths;
        }
        if (root.left == null && root.right == null) {
            allPaths.add(Integer.toString(root.val)); //at leaf,注意格式转换
        }
        List<String> leftPaths = binaryTreePaths(root.left);
        List<String> rightPaths = binaryTreePaths(root.right);
        for (String path : leftPaths) {
            allPaths.add(root.val + "->" + path);
        }
        for (String path : rightPaths) {
            allPaths.add(root.val + "->" + path);
        }
        return allPaths;
    }
}
/*
R9. Lowest Common Ancestor III 
Given the root and two nodes in a Binary Tree. Find the lowest common ancestor(LCA) of the two nodes.
The lowest common ancestor is the node with largest depth which is the ancestor of both nodes.
Return null if LCA does not exist.

Notice
node A or node B may not exist in tree.

Example
For the following binary tree:

  4
 / \
3   7
   / \
  5   6
LCA(3, 5) = 4

LCA(5, 6) = 7

LCA(6, 7) = 7
*/
/* 
If we find a node A or B, we should mark which one did we find.
*/
//O(N)
class ResultType {
    boolean findA;
    boolean findB;
    TreeNode root;
    public ResultType(boolean findA, boolean findB, TreeNode root) {
        this.findA = findA;
        this.findB = findB;
        this.root = root;
    }
}
public class Solution {

    public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode A, TreeNode B) {
       ResultType result = lowestCommonAncestor3Helper(root, A, B);
       if (result.findA && result.findB) {
           return result.root;
       }
       return null;
    }
    private ResultType lowestCommonAncestor3Helper(TreeNode root, TreeNode A, TreeNode B) {
        if (root == null) {
            return new ResultType(false, false, null);
        }
       
        ResultType left = lowestCommonAncestor3Helper(root.left, A, B);
        ResultType right = lowestCommonAncestor3Helper(root.right, A, B);
        boolean findA = left.findA || right.findA || root == A;
        boolean findB = left.findB || right.findB || root == B;
        if (root == A || root == B) {
            return new ResultType(findA, findB, root);//当前点一定是reutrn的
        }
        if (left.root != null && right.root != null) {
            return new ResultType(findA, findB, root); //分别在左右找到了两个点
        }
        if (left.root != null) {
            return new ResultType(findA, findB, left.root);
        }
        if (right.root != null) {
            return new ResultType(findA, findB, right.root);
        }
        return new ResultType(false, false, null);
    }
}
/*
R10. Validate Binary Search Tree 
Given a binary tree, determine if it is a valid binary search tree (BST).

Assume a BST is defined as follows:

The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees.
A single node tree is a BST


*/










