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
/*
CC150 4.2 Given a directed graph, design an algorithm to find out whether there is a route between two nodes.
*/
/*
route(Graph g, Node start, Node end):
	 BFS(g):
	 	if find end:
	 		reutrn true;
	 	else 
	 		return fasle;
*/
public class Node{
	public boolean visted;
	public List<Node> adjacent;

	public List getAdjacent() {

	}
}

public class Graph{
	public List<Node> nodes;

	public List getAllNodes() {

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














