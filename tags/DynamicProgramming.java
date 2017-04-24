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
/*[1, 2, 3, 4], sum = 3, cur = 3, add 5 to the sequecne, we created a new arithmetic array[3, 4, 5]
also each old arithmetic array can create a new a array, because old ones + new element is a new one
so update the sum = sum + cur; */
public class Solution{
	public int numberOfArithmeticSlices(int[] A) {
		if (A == null || A.length < 3) return 0;
		int cur = 0, sum = 0;
		for (int i = 2; i < A.length; i++) {
			if (A[i] - A[i - 1] == A[i - 1] - A[i - 2]) {
				cur += 1;
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
