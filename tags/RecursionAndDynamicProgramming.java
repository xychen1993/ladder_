/*
9.2 Imagine a robot sitting on the upper left corner of an X by Y grid. The robot can only move in two directions: right and down. How many possible paths are there for the robot to go from (0, 0) to (X, Y) ?

FOLLOW UP
Imagine certain spots are "off limits," such that the robot cannot step on them. Design an algorithm to find a path for the robot from the top left to the bottom right.
*/
/*
1,1 = 2
(0,0) (0,1) (0,2)
(1,0) (1,1) (1,2)

(n,n) = 1 from (n-1,n) or 1 from (n, n-1)
	  = ways(n-1,n) + ways(n,n-1)

countWays(x, y):
	if (x <= 0 || y <= 0) return 0;
	if (x == 0 && y == 1) return 1;
	if (x == 1 && y == 0) return 1;
	return  countWays(x - 1, y) + countWays(x, y - 1)

0,0 = 0
0,1 = 1
1,0 = 1
1,1 = 0,1 + 1,0 = 2
1,2 = 0,2 + 1,1 = 0,1 + 1,1 = 1+2=3

follow up:

(0,0) (0,1) (0,2)
(1,0) (1,1) (1,2)

(x,y) = from (x-1,y) or from (x,y-1)

boolean getPath(x, y, points, path, cache):
	if x and y are 0
		return ture;
	if x < 0 || y < 0:
		return fasle;
	if (x,y) is in points:
		return false;
	if (x,y) is in cache:
		return cache.get((x,y);

	success =  getPath(x - 1,y, points) || getPath(x,y - 1, points)
	if success is true:
		path.add(x,y);
		cache.add((x,y), true);
	else 
		cache.add((x,y), false)
	return success

*/
class Point{
	public int x;
	public int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

public class Solution{
	public List getPath(int x, int y){
		int[][] cache = new int[x + 1][y + 1]; //store visited points result
		List<Point> path = new ArrayList<>();//store result path
		boolean success = getPath(int x, int y, List<Point> offLimits, cache, path);
		return success ? path : null;
	}

	public boolean getPath(int x, int y, int[][] cache, List<Point> path){
		Point p = new Point(x,y);
		
		/*Start point always true*/
		if (x == 0 && y == 0) {
			path.add(p);
			return true;
		}
		/*exceed edges or is off limits, false*/
		if (x < 0 || y < 0 || isOffLimits(P)) 
			return false;

		/*already visited (x,y) before*/
		if (cache[x][y] != 0) {
			return cache[x][y] == 1 ? true : false;
		}

		boolean success = getPath(x - 1, y, cache, path) || getPath(x, y - 1, cache, path);
		if (success) {
			path.add(p);
			cache[x][y] = 1;
		}else {
			cache[x][y] = -1;
		}
		return success;

	}

	public boolean isOffLimits(Point p) {

	}

}


/*
A child is running up a staircase with n steps, and can hop either 1 step, 2 steps, or 3 steps at a time. Implement a method to count how many possible ways the child can run up the stairs.
*/
/*
approach this problem from top down
nth stair: 
1 from n-1
2 from n-2
3 from n-3
the sum of the number of ways of reaching each of the last three steps.


waysToRunUpStairs(n):
	if n < 1 return 0;
	if n == 1 return 1;
	return waysToRunUpStairs(n - 1) + waysToRunUpStairs(n - 2) + waysToRunUpStairs(n - 3)  

waysToRunUpStairs(n):
	if n < 0 return 0;
	ways = [0, 1, 2, 0 ... 0];
			0, 1, 2, 3 ... n
	for i from 3 to n:
		ways[i] = ways[i-1] + ways[i-2] +ways[i-3]
	return ways[n];

n result
0 0
1 1
2 2 = 1 + 1 or 2
3 2 + 1 + 0 = 3 1. 1 + 1 + 1
				2. 1 + 2
				3. 2 + 1

*/
public class Solution{
	public int countWaysRunStairs(int n) {
		if (n <= 0) return 0;
		if (n == 1) return 1;
		int[] ways = new int[n + 1];
		ways[0] = 0;
		ways[1] = 1;
		ways[2] = 2;

		for (int i = 3; i <=n; i++) {
			ways[i] = ways[i - 1] + ways[i - 2] + ways[i - 3];
		}
		return ways[n];
	}
}






