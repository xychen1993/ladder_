/*
121. Best Time to Buy and Sell Stock
Say you have an array for which the ith element is the price of a given stock on day i.

If you were only permitted to complete at most one transaction (ie, buy one and sell one share of the stock), design an algorithm to find the maximum profit.

Example 1:
Input: [7, 1, 5, 3, 6, 4]
Output: 5

max. difference = 6-1 = 5 (not 7-1 = 6, as selling price needs to be larger than buying price)
Example 2:
Input: [7, 6, 4, 3, 1]
Output: 0

In this case, no transaction is done, i.e. max profit = 0.
*/
[7, 1, 5, 3, 6, 4]
currMax += prices[i] - prices[i - 1] 

public class Solution{
	public int maxProfit(int[] prices) {
		if (prices == null) return 0;
		int currMax = 0;
		int maxSoFar = 0;

		for (int i = 1; i < prices.length; i++) {
			currMax = Math.max(0, currMax + prices[i] - prices[i-1]);
			maxSoFar = Math.max(maxSoFar, currMax);
		}
		return maxSoFar;

	}
}

/*
198. House Robber
You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security system connected and it will automatically contact the police if two adjacent houses were broken into on the same night.

Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob tonight without alerting the police.
*/
public class Solution {
    public int rob(int[] nums) {
        if (nums == null) return 0;
        //int[] dp = new int[nums.length];
        int prevMax = 0;
        int currMax = 0;
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            max = Math.max(prevMax + nums[i], currMax); 
            prevMax = currMax;
            currMax = max;
        }
        return max; 
    }
}



/*
70. Climbing Stairs
You are climbing a stair case. It takes n steps to reach to the top.

Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

Note: Given n will be a positive integer.
*/
/*
i steps:
1. take 1 step from the i -1 step
2. take 1 step to cross 2 steps, from the i-2 step
https://leetcode.com/articles/climbing-stairs/#approach-3-dynamic-programming-accepted
ways[i] = ways[i-1] + ways[i-2]

(i, n)
[1,1,2,3,5,8]
*/

public class Solution{
	public int climbStairs(int n) {
		if (n < 1) return 0;
		int[] ways = new int[n + 1];
		ways[0] = 1;
		ways[1] = 1;
		for (int i = 2; i <= n; i++) {
			ways[i] = ways[i - 2] + ways[i - 1];
		}
		return ways[n];
	}
}


/*
413. Arithmetic Slices
A sequence of number is called arithmetic if it consists of at least three elements and if the difference between any two consecutive elements is the same.

For example, these are arithmetic sequence:

1, 3, 5, 7, 9
7, 7, 7, 7
3, -1, -5, -9
The following sequence is not arithmetic.

1, 1, 2, 5, 7

A zero-indexed array A consisting of N numbers is given. A slice of that array is any pair of integers (P, Q) such that 0 <= P < Q < N.

A slice (P, Q) of array A is called arithmetic if the sequence:
A[P], A[p + 1], ..., A[Q - 1], A[Q] is arithmetic. In particular, this means that P + 1 < Q.

The function should return the number of arithmetic slices in the array A.

Example:

A = [1, 2, 3, 4]

return: 3, for 3 arithmetic slices in A: [1, 2, 3], [2, 3, 4] and [1, 2, 3, 4] itself.
*/
/*[1, 2, 3, 4], sum = 3, cur = 2, add 5 to the sequecne, we created a new arithmetic array[3, 4, 5]
also each old arithmetic array can create a new a array, because old ones + new element is a new one
so update the sum = sum + cur; */
public class Solution{
	public int numberOfArithmeticSlices(int[] A) {
		if (A == null) return 0;
		int cur = 0, sum = 0;
		for (int i = 2; i < A.length; i++) {
			if (A[i] - A[i - 1] == A[i - 1] - A[i - 2]) {
				cur += 1;//cur stands for the arithmetic array at the end of the current subarry
				sum += cur; //key trick
			}else {
				cur = 0;
			}
		}
		return sum;
	}
}

/*
338. Counting Bits
Given a non negative integer number num. For every numbers i in the range 0 ≤ i ≤ num calculate the number of 1's in their binary representation and return them as an array.

Example:
For num = 5 you should return [0,1,1,2,1,2].

Follow up:

It is very easy to come up with a solution with run time O(n*sizeof(integer)). But can you do it in linear time O(n) /possibly in a single pass?
Space complexity should be O(n).
Can you do it like a boss? Do it without using any builtin function like __builtin_popcount in c++ or in any other language.
*/
//Dynamic Programming: f[i] = f[i / 2] + i % 2;
//The last set bit always contributes one in the counter, then we can unset the bit and solve the problem recursively.
//Right shit by 1 bit, compare to previously, the number of set bit would either reduce by 1(when number is odd) or no change(when number is even).
//O(N),O(N)
public class Solution{
	public int[] countBits(int num) {
		if (num < 0) return null;
		int[] dp = new int[num + 1];
		for (int i = 1; i <= num; i++) {
			dp[i] = dp[i >>> 1] + (i & 1);
		}
		return dp;
	}
}
