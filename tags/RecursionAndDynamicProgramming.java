
/*
9.8 Given an infinite number of quarters (25 cents), dimes (10 cents), nickels (5 cents) and pennies (1 cent), write code to calculate the number of ways of representing n cents.
*/
/*
n 	ways
0	0
1	1
2	1 = 1 + 1
3	1 = 1 + 1 + 1
n 	sum of n - 1, n- 5, n-10 n-25 
	(1 cent from n - 1 cents or 
	5 cents from n - 5 cents or 
	10 ...
	25 ...)

recursion:
representCents(n):
	if n is 0:
		return 0;
	if n = 1:
		return 1;
	return representCents(n-1) + representCents(n-5) + representCents(n-10) + representCents(n - 25)

DP:
representCents(n):
	ways[0] = 0;
	ways[1] = 1;
	for i from 2 to n:
		if the index is not exceed the boundary:
			ways[i] = ways[i-1] + ways[i-5] + ways[i-10] + ways[i-25]
	return ways[n];

test cases:
0	1
1	1
2	1
3	1
4	1
5	ways[4] + ways[0] = 2
	1,1,1,1,1
	5
6	ways[5] + ways[1] = 2 + 1 = 3
	1,1,1,1,1,1
	5 + 1
	1 + 5
*/
public class Solution{
	public int representCents(int n){
		if (n < 0) return 0;
		if (n < 2) return 1;
		int[] ways = new int[n + 1];
		ways[0] = 1;
		ways[1] = 1;
		
		for (int i = 2; i <= n; i++) {
			ways[i] += ways[i - 1];
			if (i >= 5) {
				ways[i] += ways[i - 5];
			}
			if (i >= 10) {
				ways[i] += ways[i - 10];
			}
			if (i >= 25) {
				ways[i] += ways[i - 25];
			}
		}
		
		return ways[n];
	}
}

/*
9.7 Implement the "paint fill" function that one might see on many image editing programs. That is, given a screen (represented by a two-dimensional array of colors), a point, and a new color, fill in the surrounding area until the color changes from the original color.
*/
/*
paintFill(point):
	if color changes:
		return;
	change the color at this point
	paintFill(surrounding points);
*/
enum Color{
	RED, GREEN, YELLOW;
}

public class Solution{
	public void paintFill(Color[][] screen, int x, int y, Color newColor) {
		paintFill(screen, x, y, newColor, screen[x][y]);
	}
	public void paintFill(Color[][] screen, int x, int y, Color newColor, Color oldColor) {
		if (x < 0 || y < 0 || screen == null || x >= screen[0].length || y >= screen.length) {
			return;
		} 
		if (screen[x][y] != oldColor) {
			return;
		}
		screen[x][y] = newColor;
		paintFill(screen, x - 1, y, newColor, oldColor);
		paintFill(screen, x, y - 1, newColor, oldColor);
		paintFill(screen, x + 1, y, newColor, oldColor);
		paintFill(screen, x, y + 1, newColor, oldColor);
	}
}


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

optimize:
build the str from scratch, add left/right parens as long as str is valid
gerPairs(n):
	keep track of number of left/right parens, they both start with n
	if we run out of all parens:
		add current str to list
	if we still have left parens:
		append left parens into current str recursively;
	if number of left used > number of right used:
		append right parens into current str recursively;


test cases:
0	""
1	"" -> ()
2	() -> (()),()()
3	(()),()() -> ((())),(()()),(())(),()(()),()()()

(()), ()(), (())()
*/

public class Solution{
	//Duplicate issue
	public Set<String> getPairs(int n){
		Set<String> pairs = new HashSet<>();
		if (n < 1) {
			pairs.add("");
			return pairs;
		}

		Set<String> pre = getPairs(n - 1);
		for(String str : pre) {
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) == '(') {
					String newPair = insert(str, i);
					pairs.add(newPair);
				}
			}
			str = "()" + str;
			pairs.add(str);
		}
		return pairs;
	}

	public String insert(String str, int i) {
		String left = str.substring(0, i + 1);
		String right = str.substring(i + 1, str.length());
		return left + "()" + right;
	}

	//Build strs from scratch
	public List<String> getPairsFromScratch(int n) {
		List<String> pairs = new ArrayList<>();
		getPairs(pairs, n, n, new StringBuilder());
		return pairs;
	}

	public void getPairs(List<String> list, int left, int right, StringBuilder sb) {
		/*Not valid*/
		if (left < 0 || left > right) {
			return;
		}

		/*Run out of all the parens*/
		if (left == 0 && right == 0) {
			list.add(sb.toString());
			return;
		}

		if (left > 0) {
			sb.append("(");
			getPairs(list, left - 1, right, sb);
			sb.deleteCharAt(sb.length() - 1);
		}

		if (left < right) {
			sb.append(")");
			getPairs(list, left, right - 1, sb);
			sb.deleteCharAt(sb.length() - 1);
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






