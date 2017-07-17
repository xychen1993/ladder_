/*
4.9 You are given a binary tree in which each node contains a value. Design an algorithm to print all paths which sum to a given value. The path does not need to start or end at the root or a leaf.
*/
public class Solution{
	public void findSum(TreeNode root, int sum, ArrayList<Integer> path) {
		if (root == null) return;
		
		path.add(root.value);

		/*Look for paths with sum that end at current node*/
		int s = 0;
		int level = path.size();
		for (int i = level - 1; i--; i >= 0) {
			s += path.get(i);
			if (s == sum) {
				printPath(path, i, level);
			}
		}

		/*Search nodes beneath this one*/
		findSum(root.left, sum, path);
		findSum(root.right, sum, path);

		/*Remove current node from the path*/
		path.remove(level - 1);
	}

	public void printPath(ArrayList<Integer> path, int start, int end) {
		for (int i = start; i < end; i++) {
			System.out.print(path.get(i) +  " ");
		}
		System.out.println();
	}
}

/*
cc150 4.8 You have two very large binary trees: T1, with millions of nodes, and T2, with hundreds of nodes. Create an algorithm to decide if T2 is a subtree of T1.
A tree T2 is a subtree of T1 if there exists a node n in T1 such that the subtree of n is identical to T2. That is, if you cut off the tree at node n, the two trees would be identical.
*/
/*
we can use this law: inorder + pre/postorder uniquely indentify a tree
time O(M+N),space O(M+N)
matchTree(t1, t2):
	if t1 and t2 are null:
		return true;
	if t1 == null || t2 == null:
		return false;
	if t1 == t2:
		keep comparing the subtrees of t1 and t2
	else:
		search for the node on t1's subtree which == t2	
*/
/*
N nodes on T1 and M nodes on T2
worst case O(MN)(O(n^2)),space:O(logN + logM)
*/
public class Solution{
	public boolean matchTree(TreeNode t1, TreeNode t2) {
		if (t1 == null && t2 == null) 
			return true;
		if (t1.value == null || t2.value == null) 
			return false;
		if (t1.value == t2.value)
			return  matchTree(t1.left, t2.left) && matchTree(t1.right, t2.right);
		else
			return matchTree(t1.left, t2) || matchTree(t1.right, t2);
	}
}

/*
cc150 4.7 Design an algorithm and write code to find the first common ancestor of two nodes in a binary tree. Avoid storing additional nodes in a data structure. NOTE: This is not necessarily a binary search tree.
*/
/*
firstCommonAncestor(root, A, B):
	if A and B are on the same subtree:
		return firstCommonAncestor(root.thatSubtree, A, B)
	else they are not on the same side:
		return root
*/
/*O(N) cause it's not a bst, search for n times,
O(logN) ???? not sure*/
public class Solution{
	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		if (root == null) return null;
		if (root == p || root == q) return root;
		TreeNode left = lowestCommonAncestor(root.left, p, q);
		TreeNode right = lowestCommonAncestor(root.right, p, q);
		if (left == null && right == null) {
			return null; //first common ancestor not found
		}
		else if (left == null) {
			return right;//both on the right side
		}else if (right == null){
			return left;//both on the left side
		}else{
			return root;//not on the same side
		}
	}
}

/*
cc150 4.6 Write an algorithm to find the 'next'node (i.e., in-order successor) of a given node in a binary search tree. You may assume that each node has a link to its parent.
*/
/*
if the node has right subtree:
	return The leftmost node on its right subtree
while the node is on the right subtree of its parent:
	node = node.parent
return node
*/
/*O(h) where h is the height of the tree. O(1)*/
public class Solution{
	public TreeNode getInOrderSuccessor(TreeNode node) {
		if (node == null) return null;
		if (node.right != null) {
			return getLeftMostNode(node.right);
		}else {
			return getParentSuccessor(node);
		}
	}

	public TreeNode getLeftMostNode(TreeNode node) {
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	public TreeNode getParentSuccessor(TreeNode node) {
		if (node == null) return null;
		while (node.parent != null) {
			if (node.parent.right == node){
				node = node.parent;
			}else {
				return node;
			}
		}
		return null;
	}

}

/*
cc150 4.5 Implement a function to check if a binary tree is a binary search tree.
*/
/*
isBST(root, min, max):
	if (root.value <= min || root.value >= max) return false;
	return isBST(root.left, root.value, max) && isBST(root.right, min, root.value); 
*/
/*O(N), Due to the use of recursion space is O(logN)*/
public class Solution{
	public boolean isBST(TreeNode root) {
		return isBSTHelper(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	public boolean isBSTHelper(TreeNode root, int min, int max) {
		if (root == null) 
			return true;
		if (root.value <= min || root.value >= max) 
			return false;
		return isBSTHelper(root.left, min, root.value) && isBSTHelper(root.right, root.value, max);
	}
}
/*
cc150 4.4 Given a binary tree, design an algorithm which creates a linked list of all the nodes at each depth (e.g., if you have a tree with depth D, you'll have D linked lists).*/
/*
DFS and BFS: DFS requires extra space becasue there's O(logN) recursive calls, but BFS is iterative, no extra space needed. But both of them are O(N).

createLevelLists(root):
	BFS(root):
		n = number of nodes at this depth
		put these n nodes in the same list
*/
/*O(N), O(N)*/
public class Solution{
	public List<List<TreeNode>> createLevelLists(TreeNode root) {
		List<List<TreeNode>> results = new ArrayList<LinkedList<TreeNode>>;
		if (root == null) return results;
		Queue<TreeNode> q = new LinkedList<>();
		q.add(root);
		int curLevelNodesNum = 1;

		while(!q.isEmpty()) {
			int nextLevelNodesNum = 0;
			LinkedList<TreeNode> levelList = new LinkedList<>();
			results.add(levelList);
			for (int i = 0; i < curLevelNodesNum; i++) {
				Node cur = q.poll();
				levelList.add(cur);
				if (cur.left != null) {
					q.add(cur.left);
					nextLevelNodesNum++;
				}
				if (cur.right != null) {
					q.add(cur.right);
					nextLevelNodesNum++;
				}
			curLevelNodesNum = nextLevelNodesNum;
			}
		}
		return results;
	}
}

/*
CC150 4.3 Given a sorted (increasing order) array with unique integer elements, write an algorithm to create a binary search tree with minimal height.
*/
/*
createTree(array):
	root = array[middle]
	root.left = createTree(array[0 : middle])
	root.right = createTree(array[middle : 0])
	return root
*/
/*O(N),O(N)*/
class Node{
	public int value;
	public Node right;
	public Node left;

	public Node(int value) {
		this.value = value;
	}
}

public class Solution{
	public Node createTree(int[] array) {
		if (array == null) return null;
		return createTreeHelper(array,0,array.lenght - 1);
	}

	public Node createTreeHelper(int[] array, int start, int end) {
		if (start > end) return null;
		int middle = start + (start - end) / 2;
		Node root = new Node(array[middle]);
		root.left = createTreeHelper(array, start, middle - 1);
		root.right = createTreeHelper(array, middle + 1, end);
		return root;
	}
}
/*
CC150 4.2 Given a directed graph, design an algorithm to find out whether there is a route between two nodes.
*/
/*
/*
DFS or BFS: DFS can be implement easily by recursion, but it may traverse 1 adjancent node too deeply before ever going onto other immediate neighbors. BFS can also get the shortest path.  
so we choose BFS
route(Graph g, Node start, Node end):
	 BFS(g):
	 	if find end:
	 		reutrn true;
	 	else 
	 		return fasle;
*/
/*O(|V|+|E|), O(|V|)*/
public class Node{
	public boolean visted;
	public List<Node> adjacent;

	public List getAdjacent() {
		//return all the adjancent nodes
	}
}

public class Graph{
	public List<Node> nodes;

	public List getAllNodes() {
		//traverse all the nodes
	}
}
public class Solution {
	public boolean searchRoute(Graph g, Node start, Node end) {
		Queue<Node> q = new LinkedList<>();
		for (Node node : g.getAllNodes()) {
			node.visted = false;
		}
		q.offer(start);	
		start.visted = true;
		while (!q.isEmpty()) {
			Node n = q.poll();
			for (Node a : n.getAdjacent()) {
				if (a.visted == true) continue;
				if (a == end) return true;
				q.offer(a);
				a.visted = true;
			}
		}
		return false;
	}
}

/*
CC150, 4.1 Implement a function to check if a binary tree is balanced. For the purposes of this question, a balanced tree is defined to be a tree such that the heights of the two subtrees of any node never differ by more than one.
*/
/*
int check(node)
	if (node == null) return 0
	l = check(node.left)
	r = check(node.right)
	if (l == -1 || r == -1 || abs(l - r) > 1) return -1
	return Math.max(l,r) + 1
*/
/*O(N), O(H)*/
public class Solution {
	public boolean checkBalancedTree(TreeNode root){
		return getHeight(root) == -1 ? false : true;
	}

	public int getHeight(TreeNode root) {
		if (root == null) return 0;
		
		int leftHeight = getHeight(root.left);
		if (leftHeight == -1) return -1;
		
		int rightHeight = getHeight(root.right);
		if (rightHeight == -1) return -1;
		
		if (Math.abs(leftHeight - rightHeight) > 1) return -1;
		else return Math.max(leftHeight, rightHeight) + 1;
	}

}














