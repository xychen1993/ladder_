/*
R1. Search Graph Nodes 
Given a undirected graph, a node and a target, return the nearest node to given node which value of it is target, return NULL if you can't find.

There is a mapping store the nodes' values in the given parameters.

Notice
It's guaranteed there is only one available solution
*/
//O(N+M), N is the number of nodes, M is the number of edges
public class Solution {
     /*graph参数没用到。。*/
    public UndirectedGraphNode searchNode(ArrayList<UndirectedGraphNode> graph,
                                          Map<UndirectedGraphNode, Integer> values,
                                          UndirectedGraphNode node,
                                          int target) {
        if (graph == null || node == null || values == null) {
            return null;
        }
        //put given node into queue
        Queue<UndirectedGraphNode> q = new LinkedList<>();
        Set<UndirectedGraphNode> visited = new HashSet<>();
        q.offer(node);
        visited.add(node); // 这里注意要add node
        //Iter node in queue
        while (!q.isEmpty()) {
            UndirectedGraphNode curr = q.poll();
            //if we find target, return current node
            if (values.get(curr) == target) {
                return curr;
            }
            //put all the current node's neighbors into queue
            for (UndirectedGraphNode neighbor : curr.neighbors) {
                if (visited.contains(neighbor)) {
                    continue;
                }
                q.offer(neighbor);
                visited.add(neighbor);
            }
        }
        return null;
    }
}
/*
R2. Binary Tree Level Order Traversal 
Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).

Example
Given binary tree {3,9,20,#,#,15,7},

    3
   / \
  9  20
    /  \
   15   7
 
return its level order traversal as:

[
  [3],
  [9,20],
  [15,7]
]

*/
//O(N)
public class Solution {
    /*bfs太简单略过，用dfs*/
    public ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        levelOrder(result, root, 0);
        return result;
    }
    //using paramter level to track current depth
    private void  levelOrder(ArrayList<ArrayList<Integer>> result, TreeNode root, int level) {
        if (root == null) {
            return;
        }
        //store node's value in corresponding list
        if (result.size() < level + 1) {
            result.add(new ArrayList<Integer>(Arrays.asList(root.val)));
        } else {
            result.get(level).add(root.val);
        }
        levelOrder(result, root.left, level + 1);
        levelOrder(result, root.right, level + 1);
    }
}
/*
R3. Number of Islands 
Given a boolean 2D matrix, 0 is represented as the sea, 1 is represented as the island. If two 1 is adjacent, we consider them in the same island. We only consider up/down/left/right adjacent.

Find the number of islands.
*/
//O(C * R)
public class Solution {
    /**
     * @param grid a boolean 2D matrix
     * @return an integer
     */
    public int numIslands(boolean[][] grid) {
        //iter each node in grid
        //count how many 1s we've met
        if (grid == null) {
            return 0;
        }
        int num = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j]) {
                    num++;
                    markIslands(grid, i, j);
                }
            }
        }
        return num;
    }
    //if node == true, mark it to false and its true neighbors as false
    private void markIslands(boolean[][] grid, int i, int j) {
        if (!isBound(grid, i, j) || !grid[i][j]) {
            return;
        }
        grid[i][j] = false;
        int[] directionX = new int[]{0, 1, 0, -1};
        int[] directionY = new int[]{1, 0, -1, 0};
        for (int k = 0; k < 4; k++) {
            markIslands(grid, i + directionX[k], j + directionY[k]);
        }
    }
    /*determine if x and y are not out of bound*/
    private boolean isBound(boolean[][] grid, int x, int y) {
        int xBound = grid.length;
        if (xBound == 0) {
            return false;
        }
        int yBound = grid[0].length;
        return x >= 0 && x < xBound && y >= 0 && y < yBound;
    }
}
/*
R4. Clone Graph 
Clone an undirected graph. Each node in the graph contains a label and a list of its neighbors.

How we serialize an undirected graph:

Nodes are labeled uniquely.

We use # as a separator for each node, and , as a separator for node label and each neighbor of the node.

As an example, consider the serialized graph {0,1,2#1,2#2,2}.

The graph has a total of three nodes, and therefore contains three parts as separated by #.

First node is labeled as 0. Connect node 0 to both nodes 1 and 2.
Second node is labeled as 1. Connect node 1 to node 2.
Third node is labeled as 2. Connect node 2 to node 2 (itself), thus forming a self-cycle.
Visually, the graph looks like the following:

   1
  / \
 /   \
0 --- 2
     / \
     \_/
*/
//O(N+M)
public class Solution {
    /**
     * @param node: A undirected graph node
     * @return: A undirected graph node
     */
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) {
            return null;
        }
        //get old nodes
        //create new nodes
        //mapping the old->new
        Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();
        mappingOldAndNew(map, node);
        //connet the new nodes according to mapping relationship old->new
        connectNewNodes(map);
        return map.get(node); // retrun the new node corresponding to node
    }
    private void mappingOldAndNew(Map<UndirectedGraphNode, UndirectedGraphNode> map, UndirectedGraphNode node) {
        if (node == null) {
            return;
        }
        Queue<UndirectedGraphNode> queue = new LinkedList<>();
        queue.offer(node);
        map.put(node, new UndirectedGraphNode(node.label));
        while (!queue.isEmpty()) {
            UndirectedGraphNode curr = queue.poll();
            for (UndirectedGraphNode neighbor : curr.neighbors) {
                //create new neighbor nodes
                if (!map.containsKey(neighbor)) {
                    queue.offer(neighbor);
                    map.put(neighbor, new UndirectedGraphNode(neighbor.label));
                }
            }
        }
    }
    /*connet the new nodes according to mapping relationship old->new*/
    private void connectNewNodes(Map<UndirectedGraphNode, UndirectedGraphNode> map) {
        for (UndirectedGraphNode oldNode : map.keySet()) {
            UndirectedGraphNode newNode = map.get(oldNode);
            for (UndirectedGraphNode oldNeighbor : oldNode.neighbors) {
                UndirectedGraphNode newNeighbor = map.get(oldNeighbor);
                newNode.neighbors.add(newNeighbor);
            }
        }
    }
}
/*
R5. Zombie in Matrix 
Given a 2D grid, each cell is either a wall 2, a zombie 1 or people 0 (the number zero, one, two).Zombies can turn the nearest people(up/down/left/right) into zombies every day, but can not through wall. How long will it take to turn all people into zombies? Return -1 if can not turn all people into zombies.

*/
















