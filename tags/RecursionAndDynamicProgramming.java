/*
9.6 Implement an algorithm to print all valid (i.e., properly opened and closed) combinations of n-pairs of parentheses.
*/
/*
base cases:
0	""
1	()
2	(()),()()
3 	((())),(()()),(())(),()(()),()()()

gerPairs(n):
	if n is 0 return "";
	pre = gerPairs(n - 1);
	insert new pair of parenthese into 1. middle of each existing pair of parenteses
									   2. begining of the string
							  
	check duplicate using set

test cases:
0	""
1	"" -> ()
2	() -> (()),()()
3	(()),()() -> ((())),(()()),(())(),()(()),()()()
*/

public class Solution{
	public Set<String> getPairs(int n){
		Set<String> pairs = new HashSet<>();
		if (n < 1) {
			pairs.add("");
			return pairs;
		}

		Set<String> pre = getPairs(n - 1);
		for(String pair : pre) {
			
			
		}
	}
}


/*
9.5 Write a method to compute all permutations of a string
*/
/*
ABC -> ABC ACB BAC BCA CBA CAB

n = len of string
base cases:
0 -> ""
1 -> "a1"
2 -> "a1,a2", "a2,a1"
3 -> "a1,a2,a3", "a2,a1,a3", "a3,a1,a2", "a3,a2,a1", "a1,a3,a2", "a2,a3,a1"

getPermutations(str):
	if str is empty return "";
	previous = getPermutations(str - 1);
	add cur into everyspace of each previous permutations

test cases:
null	null
""		[""]
"a1"	[""] -> ["a1"]
"a1,a2"	[""] -> ["a1"] -> ["a2,a1","a1,a2"]
*/
/*O(N!) becasue there are N! permutations
O(N!) becasue there are N! permutations*/
public class Solution{
	public List<String> getPermutations(String str) {
		if (str == null)
			return null;
		return getPermutations(str, str.length());
	}

	public List<String> getPermutations(String str, int index) {
		List<String> permutations = new ArrayList<>();
		if (index == 0) {
			permutations.add("");
			return permutations;
		}

		List<String> previous = getPermutations(str, index - 1);
		char curChar = str.charAt(index - 1);
		for (String s : previous) {
			for (int i = 0; i <= s.length(); i++) {
				StringBuilder sb = new StringBuilder(s);
				sb.insert(i,curChar);
				permutations.add(sb.toString());
			}

		}
		return permutations;
		
	}
}

/*
9.4 Write a method to return all subsets of a set.
*/
/*
set: [1, 2, .. n]
base case:
0 {}
1 {}, {1}
2 {}, {1}, {1,2}, {2}
3 {}, {1}, {1,2}, {2}, {3}, {1,3}, {1,2,3}, {2,3}

subsets(set):
	if set is empty:
		return {}
	preSub = subsets(set - last element)
	return preSub + each elem in preSub.append(last element)

subsets(set):
	list.add({});
	iter e in set:
		pre = all elements in list
		newSubsets = add e into each pre
		list.add(newSubsets)
	return list;
*/
public class Solution{
	public List<List<Integer>> getSubsets(List<Integer> set) {
		/*Recursion*/
		recursiveGetSubsets(set, set.size() - 1); //start from the first element
		/*Iteration*/
		iterateGetSubsets(set);

	}
	public List<List<Integer>> recursiveGetSubsets(List<Integer> set, int index) {
		if (set == null) return null;
		List<ArrayList<Integer>> allSubsets = new ArrayList<ArrayList<Integer>>();
		
		if (index == 0) {
			allSubsets.add(new ArrayList<Integer>());
			return allSubsets;
		}

		int curInt = set.get(index - 1);
		List<ArrayList<Integer>> preSubsets = recursiveGetSubsets(set, index - 1);
		List<ArrayList<Integer>> newSubsets = new ArrayList<ArrayList<Integer>>();
		for (List<Integer> preSub : preSubsets) {
			ArrayList<Integer> newSub = new ArrayList<>();
			newSub.addAll(preSub);
			newSub.add(curInt);
			newSubsets.add(newSub);
		}
		newSubsets.addAll(preSubsets); 
		
		return newSubsets;

	}

}

/*
9.3 A magic index in an array A[1...n-1] is defined to be an index such that A[i] = i. Given a sorted array of distinct integers, write a method to find a magic index, if one exists, in array A.

FOLLOW UP
What if the values are not distinct?
*/
/*
A = [-1,0, 1, 3, 5, 7]
	[0, 1, 2, 3, 4, 5]

getMagicIndex(A):
	if i > A[i]:
		search left part
	if i < A[i]:
		search right part
	if i == A[i] 
		return i;

follow up:
A = [0, 0, 4, 4, 4, 4]
	[0, 1, 2, 3, 4, 5]

getMagicIndex(A):
	if (midIndex == midValue) return midIndex;
	check left part from max(midIndex, midValue);
	check right part from min(midIndex, midValue);
*/
public class Solution{
	public int getMagicIndex(int[] A) {
		if (A == null) return -1;
		return getMagicIndex(A, 0, A.length - 1);
	}

	public int getMagicIndex(int[] A, int start, int end) {
		if (start > end || start < 0 || end >= A.length) {
			return -1;
		}
		
		int midIndex = start + (end - start) / 2;
		int midValue = A[midIndex];

		if (midIndex == midValue) {
			return midValue;
		}

		int left = getMagicIndex(A, start, Math.min(midValue, midIndex - 1));
		if (left != -1) {
			return left;
		}
		
		int right = getMagicIndex(A, Math.max(midValue, midIndex + 1), end);

		return right;
	}
}

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

//time is exponet O(3^N) cuz each step branches out 3 more calls
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






