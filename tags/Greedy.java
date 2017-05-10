/*
45. Jump Game II
Given an array of non-negative integers, you are initially positioned at the first index of the array.

Each element in the array represents your maximum jump length at that position.

Your goal is to reach the last index in the minimum number of jumps.

For example:
Given array A = [2,3,1,1,4]

The minimum number of jumps to reach the last index is 2. (Jump 1 step from index 0 to 1, then 3 steps to the last index.)

Note:
You can assume that you can always reach the last index.
*/
/*
[start, end], farthest
*/
//Greedy
public class Solution{
	public int jump(int[] nums) {
		if (nums == null) return Integer.MAX_VALUE;
		int jumps = 0, end = 0, farthest = 0;
		//from 0 to the second last element, because if we reach at the last element we don't have to jump
		for (int i = 0; i < nums.length - 1; i++) {
			farthest = Math.max(farthest, nums[i] + i);
			if (i == end) {
				jumps++;
				end = farthest;
			}
		}
		return jumps;
	}
}

//DP solution but exceed time limits
public class Solution{
	public int jump(int[] nums) {
		if (nums == null) return Integer.MAX_VALUE;
		int[] jumps = new int[nums.length];
		for (int i = 1; i < nums.length; i++){
			jumps[i] = Integer.MAX_VALUE;
			for (int j = 0; j < i; j++) {
				if (j + nums[j] >= i && jumps[j] != Integer.MAX_VALUE)
					jumps[i] = Math.min(jumps[i], jumps[j] + 1);
			}
		}
		return jumps[nums.length - 1];
	}
}

/*
55. Jump Game
Given an array of non-negative integers, you are initially positioned at the first index of the array.

Each element in the array represents your maximum jump length at that position.

Determine if you are able to reach the last index.

For example:
A = [2,3,1,1,4], return true.

A = [3,2,1,0,4], return false.
*/
public class Solution{
	public boolean canJump(int[] nums) {
		int max = 0;
		for (int i = 0; i < nums.length; i++) {
			if (i > max) return false;
			max = Math.max(nums[i] + i, max);
		}
		return true;
	}
}




