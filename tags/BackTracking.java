/*
294.Flip Game II
*/

/*
526. Beautiful Arrangement
Suppose you have N integers from 1 to N. We define a beautiful arrangement as an array that is constructed by these N numbers successfully if one of the following is true for the ith position (1 ≤ i ≤ N) in this array:

The number at the ith position is divisible by i.
i is divisible by the number at the ith position.
Now given N, how many beautiful arrangements can you construct?

Example 1:
Input: 2
Output: 2
Explanation: 

The first beautiful arrangement is [1, 2]:

Number at the 1st position (i=1) is 1, and 1 is divisible by i (i=1).

Number at the 2nd position (i=2) is 2, and 2 is divisible by i (i=2).

The second beautiful arrangement is [2, 1]:

Number at the 1st position (i=1) is 2, and 2 is divisible by i (i=1).

Number at the 2nd position (i=2) is 1, and i (i=2) is divisible by 1.
Note:
N is a positive integer and will not exceed 15.
*/
//Try all the possible combinations and use an array to track used elements;
public class Solution{
	int count = 0; //use gloable variable to keep results 
	public int countArrangement(int N) {
		if (N < 1) return 0;
		int[] used = new int[N + 1];
		countHelper(N, 1, used);
		return count;
	}
	public void countHelper(int N, int pos, int[] used) {
		if (pos > N) {
			count++; //means the whole combination array is beautiful;
			return;
		}
		for (int i = 1; i <= N; i++) {
			if (used[i] == 0 && (i % pos == 0 || pos % i == 0)){
				used[i] = 1;
				countHelper(N, pos + 1, used);
				used[i] = 0;
			}
		}
	}
}














