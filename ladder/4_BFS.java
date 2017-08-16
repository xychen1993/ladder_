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
//O(ROW * COL)
class Cell {
    int x;
    int y;
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
public class Solution {
    public int PEOPLE = 0;
    public int ZOMBIE = 1;
    public int WALL = 2;
    public int peopleNum = 0;
    public int zombie(int[][] grid) {
        if (grid == null) {
            return -1;
        }
        //Know the num of people, also put zombies into queue
        Queue<Cell> queue = new LinkedList<>();
        initial(grid, queue);
        if (peopleNum == 0) {
            return 0; //No people in the maxtrix
        }
        //iter cell in grid, turn people into new zombies, get num of days used
        int days = turnPeopleIntoZombie(grid, queue);
        //check if there's no people left in the matrix
        if (peopleNum == 0) {
            return days;
        }
        return -1;
    }
    /*Know the num of people, also put zombies into queue*/
    private void initial(int[][] grid, Queue<Cell> queue) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == PEOPLE) {
                    peopleNum++;
                } else if (grid[i][j] == ZOMBIE) {
                    queue.offer(new Cell(i, j));
                }
            }
        }
    }
    /*turn people into zombies and return num of days used*/
    private int turnPeopleIntoZombie(int[][] grid, Queue<Cell> queue) {
        int days = 0;
        int[] directionX = new int[]{0, 1, 0, -1};
        int[] directionY = new int[]{1, 0, -1, 0};
        while (!queue.isEmpty()) {
            int size = queue.size();
            days++;
            for (int i = 0; i < size; i++) {
                Cell curr = queue.poll();
                for (int j = 0; j < 4; j++) {
                    int x = curr.x + directionX[j];
                    int y = curr.y + directionY[j];
                    Cell cell = new Cell(x, y);
                    if (isPeople(cell, grid)) {
                        grid[x][y] = ZOMBIE;
                        peopleNum--;
                        queue.offer(cell);
                    }
                }
            }
            if (peopleNum == 0) {
                break;
            }
        }
        return days;
    }
    private boolean isPeople(Cell cell, int[][] grid) {
        int x = cell.x;
        int y = cell.y;
        if (grid.length == 0) {
            return false;
        }
        boolean inBound = x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
        if (inBound) {
            return grid[x][y] == PEOPLE;
        }
        return false;
    }
}
/*
R6. Knight Shortest Path 
Given a knight in a chessboard (a binary matrix with 0 as empty and 1 as barrier) with a source position, find the shortest path to a destination position, return the length of the route. 
Return -1 if knight can not reached.

Notice
source and destination must be empty.
Knight can not enter the barrier.

Clarification
If the knight is at (x, y), he can get to the following positions in one step:
(x + 1, y + 2)
(x + 1, y - 2)
(x - 1, y + 2)
(x - 1, y - 2)
(x + 2, y + 1)
(x + 2, y - 1)
(x - 2, y + 1)
(x - 2, y - 1)

Example
[[0,0,0],
 [0,0,0],
 [0,0,0]]
source = [2, 0] destination = [2, 2] return 2

[[0,1,0],
 [0,0,0],
 [0,0,0]]
source = [2, 0] destination = [2, 2] return 6

[[0,1,0],
 [0,0,1],
 [0,0,0]]
source = [2, 0] destination = [2, 2] return -1
*/
/**
 * Definition for a point.
 * public class Point {
 *     publoc int x, y;
 *     public Point() { x = 0; y = 0; }
 *     public Point(int a, int b) { x = a; y = b; }
 * }
 */
//O(COL * ROW)
public class Solution {
    public boolean EMPTY = false;
    public boolean BARRIER = true;
    public int shortestPath(boolean[][] grid, Point source, Point destination) {
        if (grid == null || source == null || destination == null) {
            return -1;
        }
        //put source into queue
        int steps = 0;
        Queue<Point> q = new LinkedList<>();
        q.offer(source);
        grid[source.x][source.y] = BARRIER; //mark as visited
        int[] directionX = new int[]{1, 1, -1, -1, 2, 2, -2, -2};
        int[] directionY = new int[]{2, -2, 2, -2, 1, -1, 1, -1};
        //check next positons until reach at destination
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                Point curr = q.poll();
                if (curr.x == destination.x && curr.y == destination.y) {
                    return steps;
                }
                for (int j = 0; j < 8; j++) {
                    int x = curr.x + directionX[j];
                    int y = curr.y + directionY[j];
                    if (!inBound(x, y, grid) || grid[x][y] == BARRIER) {
                        continue;
                    }
                    q.offer(new Point(x, y));
                    grid[x][y] = BARRIER;
                }
            }
            steps++;

        }
        return -1;
    }
    private boolean inBound(int x, int y, boolean[][] grid) {
        if (grid.length == 0) {
            return false;
        }
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
    }
}
/*
R7. Course Schedule II 
There are a total of n courses you have to take, labeled from 0 to n - 1.
Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]

Given the total number of courses and a list of prerequisite pairs, return the ordering of courses you should take to finish all courses.

There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all courses, return an empty array.

Example
Given n = 2, prerequisites = [[1,0]]
Return [0,1]

Given n = 4, prerequisites = [1,0],[2,0],[3,1],[3,2]]
Return [0,1,2,3] or [0,2,1,3]
*/
/*
注意一定要先给每个课程建立空的list，否则有的课不在prerequisites数组里出现，其实就是indegree = 0，但会导致上不了！
*/
//O(N + E)
public class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        if (numCourses < 1 || prerequisites == null) {
            return new int[0];
        }
        //get mapping(course -> following courses), indegree[] to keep track of indegrees of courses
        int[] order = new int[numCourses];
        Map<Integer, ArrayList<Integer>> map = new HashMap<>();
        int[] indegrees = initial(numCourses, prerequisites, map);
        //Do bfs to take courses, return num of taken courses
        int courseTaken = takeCourse(map, indegrees, order);
        //check if taken course = n
        if (courseTaken == numCourses) {
            return order;
        }
        return new int[0];
    }
    /*get mapping(course -> following courses), indegree[] to keep track of indegrees of courses*/
    private int[] initial(int numCourses, int[][] prerequisites, Map<Integer, ArrayList<Integer>> map) {
        int[] indegrees = new int[numCourses];
        /*一定要先给每个课建list，否则有的课不在数组里出现，其实就是indegree = 0，但会导致上不了*/
        for (int i = 0; i < numCourses; i++) {
            map.put(i, new ArrayList<Integer>());
        }
        for (int[] pair : prerequisites) {
            int pre = pair[1];
            int post = pair[0];
            indegrees[post]++;
            ArrayList<Integer> following = map.get(pre);
            following.add(post);
            map.put(pre, following);
        }
        return indegrees;
    }
    /* mark course in queue as taken, and indegree of their next following course - 1, put next untaken courses which have 0 prerequisite into queue*/
    private int takeCourse(Map<Integer, ArrayList<Integer>> map, int[] indegrees, int[] order) {
        int courseTaken = 0;
        Queue<Integer> q = new LinkedList<>();
        // put courses which have 0 prerequisite into queue
        for (int i = 0; i < indegrees.length; i++) {
            if (indegrees[i] == 0) {
                q.offer(i);
                order[courseTaken++] = i;
            }
        }
        while (!q.isEmpty()) {
            int curr = q.poll();
            for (int post : map.get(curr)) {
                indegrees[post]--;
                if (indegrees[post] == 0) {
                    q.offer(post);
                    order[courseTaken++] = post;
                }
            }
        }
        return courseTaken;
    }
}
/*
R8. Binary Tree Serialization 

Design an algorithm and write code to serialize and deserialize a binary tree. Writing the tree to a file is called 'serialization' and reading back from the file to reconstruct the exact same binary tree is 'deserialization'.

Notice
There is no limit of how you deserialize or serialize a binary tree, LintCode will take your output of serialize as the input of deserialize, it won't check the result of serialize.

Example
An example of testdata: Binary tree {3,9,20,#,#,15,7}, denote the following structure:

  3
 / \
9  20
  /  \
 15   7
Our data serialization use bfs traversal. This is just for when you got wrong answer and want to debug the input.

You can use other method to do serializaiton and deserialization.
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
class Solution {
    /*serialize binary tree into level order traversal values*/
    public String serialize(TreeNode root) {
        String serialized = "";
        if (root == null) {
            return serialized;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        serialized += root.val;
        while (!q.isEmpty()) {
            TreeNode curr = q.poll();
            if (curr.left != null) {
                q.offer(curr.left);
                serialized += "," + curr.left.val;
            } else {
                serialized += "," + "#";
            }
            if (curr.right != null) {
                q.offer(curr.right);
                serialized += "," + curr.right.val;
            } else {
                serialized += "," + "#";
            }
        }
        return serialized;
    }
     /*deserialize level order traversal into a binary tree*/
     /*遍历string数组，不是#就建立新node放入list，用index来记录当前走到第几个node，每遍历两个string index + 1*/
    public TreeNode deserialize(String data) {
        if (data == null || data.length() == 0) {
            return null;
        }
        String[] values = data.split(",");
        ArrayList<TreeNode> nodes = new ArrayList<>();
        int index = 0;
        boolean isLeftChild = true;
        nodes.add(new TreeNode(Integer.parseInt(values[0])));
        for (int i = 1; i < values.length; i++) {
            String value = values[i];
            if (!value.equals("#")) {
                TreeNode node = nodes.get(index);
                TreeNode child = new TreeNode(Integer.parseInt(value));
                nodes.add(child);
                if (isLeftChild) {
                    node.left = child;
                } else {
                    node.right = child;
                }
            }
            isLeftChild = !isLeftChild;
            if (isLeftChild) {
                index++;
            }
        }
        return nodes.get(0);
    }
}
/*
R9. Graph Valid Tree 
Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), write a function to check whether these edges make up a valid tree.

 Notice

You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.

Example
Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.

Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.
*/
public class Solution {
    public boolean validTree(int n, int[][] edges) {
        //1. num of edges = n - 1; 2. All the nodes are connected;
        if (edges == null) {
            return false;
        }
        //get the num of edges, check 1
        int edgesNum = edges.length;
        if (edgesNum != n - 1) {
            return false;
        }
        if (n == 1) {
            return true;
        }
        //get map<node->neighbors>
        Map<Integer, ArrayList<Integer>> map = initGraph(n, edges);
        //check if all the nodes are connected by doing BFS, check 2
        return isConnected(n, map);
    }
    private Map<Integer, ArrayList<Integer>> initGraph(int n, int[][] edges) {
        Map<Integer, ArrayList<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            int node1 = edge[0];
            int node2 = edge[1];
            if (!map.containsKey(node1)) {
                map.put(node1, new ArrayList<Integer>(Arrays.asList(node2)));
            } else {
                ArrayList<Integer> neighbors = map.get(node1);
                neighbors.add(node2);
                map.put(node1, neighbors);
            }
            if (!map.containsKey(node2)) {
                map.put(node2, new ArrayList<Integer>(Arrays.asList(node1)));
            } else {
                ArrayList<Integer> neighbors = map.get(node2);
                neighbors.add(node1);
                map.put(node2, neighbors);
            }
        }
        return map;
    }
    /*check if all the nodes are connected by doing BFS*/
    private boolean isConnected(int n, Map<Integer, ArrayList<Integer>> map) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> q = new LinkedList<>();
        q.offer(0);
        visited.add(0);
        while (!q.isEmpty()) {
            int curr = q.poll();
            if (!map.containsKey(curr)) {
                return false;
            }
            for (int neighbor : map.get(curr)) {
                if (visited.contains(neighbor)) {
                    continue;
                }
                visited.add(neighbor);
                q.offer(neighbor);
            }
        }
        return visited.size() == n;
    }
}
/*
R10. Build Post Office II 
Given a 2D grid, each cell is either a wall 2, an house 1 or empty 0 (the number zero, one, two), find a place to build a post office so that the sum of the distance from the post office to all the houses is smallest.

Return the smallest sum of distance. Return -1 if it is not possible.

Notice
You cannot pass through wall and house, but can pass through empty.
You only build post office on an empty.

Example
Given a grid:

0 1 0 0 0
1 0 0 2 1
0 1 0 0 0
return 8, You can build at (1,1). (Placing a post office at (1,1), the distance that post office to all the house sum is smallest.)
*/
/*
从每个empty出发去寻找，可能empty太多，所以从每个house出发去寻找答案
     * 1. 先拿到所有的list of houses 和 empties
     * 2. 遍历hosues，计算所有house到每个empty的距离，叠加起来，并且记录有多少hosue来visit过
     * 3. 遍历emties，找到既被所有house visited，也是叠加距离最小的
*/
class Cell {
    int x;
    int y;
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
public class Solution {
    public int HOUSE = 1;
    public int EMPTY = 0;
    public int WALL = 2;
    public int shortestDistance(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return -1;
        }
        //get all the hosues and empties
        List<Cell> houses = getList(HOUSE, grid);
        List<Cell> empties = getList(EMPTY, grid);
        //iter houses, search for the distance from current house to all empties
        int[][] distanceSum = new int[grid.length][grid[0].length];
        int[][] visitedTimes = new int[grid.length][grid[0].length];
        for (Cell house : houses) {
            getDistanceSum(house, grid, distanceSum, visitedTimes);
        }
        //iter houses empties, looking for the smallest sum, also all houses have visited this empty
        int houseNum = houses.size();
        int minDistanceSum = Integer.MAX_VALUE;
        for (Cell empty : empties) {
            if (visitedTimes[empty.x][empty.y] != houseNum) {
                continue;
            }
            minDistanceSum = Math.min(minDistanceSum, distanceSum[empty.x][empty.y]);
        }
        //check if there's a solution
        if (minDistanceSum == Integer.MAX_VALUE) {
            return -1;
        }
        return minDistanceSum;
    }
    private List<Cell> getList(int type, int[][] grid) {
        List<Cell> l = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == type) {
                    l.add(new Cell(i, j));
                }
            }
        }
        return l;
    }
    private void getDistanceSum(Cell cell, int[][] grid, int[][] distanceSum, int[][] visitedTimes) {
        Queue<Cell> q = new LinkedList<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int steps = 0;
        int[] directionX = new int[]{0, 1, 0, -1};
        int[] directionY = new int[]{1, 0, -1, 0};
        q.offer(cell);
        visited[cell.x][cell.y] = true;
        while (!q.isEmpty()) {
            int size = q.size();
            steps++;
            for (int i = 0; i < size; i++) {
                Cell curr = q.poll();
                for (int j = 0; j < 4; j++) {
                    int x = curr.x + directionX[j];
                    int y = curr.y + directionY[j];
                    if (!inBound(x, y, grid) || grid[x][y] != EMPTY || visited[x][y]) {
                        continue;
                    }
                    q.offer(new Cell(x, y));
                    distanceSum[x][y] += steps;
                    visitedTimes[x][y] += 1;
                    visited[x][y] = true;
                }
            }
        }
    }
    private boolean inBound(int x, int y, int[][] grid) {
        if (grid.length == 0) {
            return false;
        }
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
    }
}











