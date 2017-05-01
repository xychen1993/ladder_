/*
485. Max Consecutive Ones
Given a binary array, find the maximum number of consecutive 1s in this array.
Example 1:
Input: [1,1,0,1,1,1]
Output: 3
Explanation: The first two digits or the last three digits are consecutive 1s.
    The maximum number of consecutive 1s is 3.

Note:
The input array will only contain 0 and 1.
The length of input array is a positive integer and will not exceed 10,000
*/
/*
max = count = 0;
iterate arr:
	if (arr[i] = 0)
		maxLen = max(maxLen, count)
		count = 0;
	else
		count++;
return max
*/
//O(N)
public class Solution{
	public int findMaxConsecutiveOnes(int[] nums) {
		int max = 0, curMax = 0;
		for (int num : nums) {
			if (num == 0) curMax = 0;
			else curMax++;
			//Need to put math here because if it is in num==0 track
			//Will miss the last comparison
			max = Math.max(curMax, max);
		}
		return max;
	}
}

/*
370. Range Addition
Assume you have an array of length n initialized with all 0's and are given k update operations.
Each operation is represented as a triplet: [startIndex, endIndex, inc] which increments each element of subarray A[startIndex ... endIndex] (startIndex and endIndex inclusive) with inc.
Return the modified array after all k operations were executed.
Example:
Given:
    length = 5,
    updates = [
        [1,  3,  2],
        [2,  4,  3],
        [0,  2, -2]
    ]
Output:

    [-2, 0, 3, 5, 3]
Explanation:

Initial state:
[ 0, 0, 0, 0, 0 ]

After applying operation [1, 3, 2]:
[ 0, 2, 2, 2, 0 ]

After applying operation [2, 4, 3]:
[ 0, 2, 5, 5, 3 ]

After applying operation [0, 2, -2]:
[-2, 0, 3, 5, 3 ]

*/
/*
1. Generate marked array: Iterate through the k update operations and mark them in the original array(for each operation, only update startIndex and endIndex); O(K)
	1.Store every start index for each value(allows the inc to be carried to next index)
	2.At end index+1, store the minus(cancel out the previous carry)
2. Generate final array: Iterate through the marked array and transform it to the final array. O(N)
	1.Iterate through marked array, arr[i] = sum of all the previous elements;
[0,0,0,0] [1,3,1] and [2,3,2]
1st : [0,0+1=1,0,0,0-1=-1,0] = [0,1,0,0,-1,0]
2nd: [0,1,0+2=2,0,-1-2=-3,0] = [0,1,2,0,-3,0]
iterate:
	arr[i] = sum of all the previous elements
[0,0+1=1,0+1+2=3,0+1+2+0=3, 0+1+2+0+-3=0,0] = [0,1,3,3,0,0]
[0,1,3,3,0]
*/
//O(N+K)
public class Solution{
	public int[] getModifiedArray(int length, int[][] updates) {
		if (updates == null || length < 0) return null;
		int[] arr = new int[length];
		
		//Generate marked array
		for (int[] ope : updates) {
			int start = ope[0];
			int end = ope[1];
			int value = ope[2];
			
			arr[start] += value;
			if (end < length - 1) arr[end + 1] -= value;

		}

		//Generate final arr from marked arr
		// arr[i] = sum of previous elements
		int sum = 0;
		for (int i = 0; i < length; i++) {
			sum += arr[i];
			arr[i] = sum;
		}
		
		return arr;

	}
}

/*280. wiggle sort
Given an unsorted array nums, reorder it in-place such that nums[0] <= nums[1] >= nums[2] <= nums[3]....

For example, given nums = [3, 5, 2, 1, 6, 4], one possible answer is [1, 6, 2, 5, 3, 4].
*/
//Go through this array, chech each element, if dosen't satisfy given conditions, swap it. 
public class Solution{
	public void wiggleSort(int[] nums) { 
		if (nums == null) return;
		for (int i = 0; i < nums.length; i++) {
			if (i % 2 == 1 && nums[i - 1] > nums[i]) swap(nums, i);
			else if (i % 2 == 0 && i != 0 && nums[i - 1] < nums[i]) swap(nums, i);
		}
	}

	public void swap(int[] nums, int i) {
		int tmp = nums[i];
		nums[i] = nums[i - 1];
		nums[i - 1] = tmp;
	}
}

/*
169. Majority Element
Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊ n/2 ⌋ times.

You may assume that the array is non-empty and the majority element always exist in the array.

*/
/* majority vote algorithm
1. Store the candidate and its frequency
2. If nums[i] == candidate, frequency++, else frequency--;
3. If its frequency == 0, change the candidate to current element
4. Return candidate
*/
public class Solution{
	public int majorityElement(int[] nums) {
		if (nums == null) return 0;
		int candidate = nums[0], count = 1;
		
		for (int i = 1; i < nums.length; i++) {
			if (count == 0) {
				candidate = nums[i];
				count++; // be careful, count should set to 1
			}
			else if (candidate != nums[i]) count--;
			else count++;
		}

		return candidate;
	}
}















