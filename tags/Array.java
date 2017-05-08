/*
238. Product of Array Except Self
Given an array of n integers where n > 1, nums, return an array output such that output[i] is equal to the product of all the elements of nums except nums[i].

Solve it without division and in O(n).

For example, given [1,2,3,4], return [24,12,8,6].

Follow up:
Could you solve it with constant space complexity? (Note: The output array does not count as extra space for the purpose of space complexity analysis.)
*/
/*
//left
Iter a, i > 0:
	a[i] = production of left elements
		 = a[i-1] * a[i-1] production of left elements;
//right
Iter a backwards, i < a.length - 1:
	a[i] *= production of right elements
		  = a[i + 1] * a[i+1] production of left elements;
return a[i];

Test Case:
a:[1,2,3,4]
res: [2*3*4, 1*3*4, 1*2*4, 1*2*3]

i:1, p *= nums[0] = 1 = 1, [0,1,0,0]
i:2, p *= 1*2 , [0,1,1*2,0]
i:3, p *= 1*2*3, [0,1,1*2,1*2*3]

i:2 p = 4, [0,1,1*2 * 4,1*2*3]
i:1 p = 4*3, [0,1 * 4*3,1*2 * 4,1*2*3]
i:0 p = 4*3*2, [4*3*2,1 * 4*3,1*2 * 4,1*2*3]
*/
public class Solution{
	public int[] productExceptSelf(int[] nums) {
		if (nums == null) return null;
		int[] res = new int[nums.length];
		res[0] = 1;
		
		//left
		int p = 1;
		for (int i = 1; i < nums.length; i++) {
			p *= nums[i - 1];
			res[i] = p;
		}
		//right
		p = 1;
		for (int i = nums.length - 2; i >= 0; i--) {
			p *= nums[i + 1];
			res[i] *= p;
		}
		return res;
    } 
}

/*
245. Shortest Word Distance III
This is a follow up of Shortest Word Distance. The only difference is now word1 could be the same as word2.

Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.

word1 and word2 may be the same and they represent two individual words in the list.

For example,
Assume that words = ["practice", "makes", "perfect", "coding", "makes"].

Given word1 = “makes”, word2 = “coding”, return 1.
Given word1 = "makes", word2 = "makes", return 3.

Note:
You may assume word1 and word2 are both in the list.
*/
/*
w1 = w2 = -1;
Iterate words:
	if words[i] == word1 and word2:
		min = Math.min(min, i - w1);
		w1 = i;
	else if words[i] == word1:
		w1 = i;
	else if words[i] == word2:
		w2 = i;
	else continue;

	if (w1 and w2 != -1)
		min = Math.min(min, w1- w2);

Test Case:
words = ["practice", "makes", "perfect", "coding", "makes"]
Given word1 = "makes", word2 = "makes", return 3.
i: 1, w1 = 1, w2 = -1, min = max;
i: 4, w1 = 4, w2 = -1, min = 4 - 1 = 3;
*/
public class Solution{
	public int shortestWordDistance(String[] words, String word1, String word2) {
		int min = Integer.MAX_VALUE;
		if (words == null) return min;
		int w1 = -1, w2 = -1;
		boolean same = word1.equals(word2);
		for (int i = 0; i < words.length; i++) {
			if (words[i].equals(word1) && same) {
				if (w1 != -1)
					min = Math.min(min, i - w1);
				w1 = i;
			}else if (words[i].equals(word1)) {
				w1 = i;
			}else if (words[i].equals(word2)) {
				w2 = i;
			}else continue;

			//If w1 or w2 has changed, update min
			if (w1 != -1 && w2 != -1)
				min = Math.min(min, Math.abs(w1 - w2));
		}
		return min;

	}
}

/*
243. Shortest Word Distance
Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.

For example,
Assume that words = ["practice", "makes", "perfect", "coding", "makes"].

Given word1 = “coding”, word2 = “practice”, return 3.
Given word1 = "makes", word2 = "coding", return 1.

Note:
You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
*/
/*
min = Integer.MAX_VALUE;
f1 = f2 = -1;
Iterate words:
	if words[i] == w1:
		f1 = i;
	if words[i] == w2:
		f2 = i;
	if (f1 & f2 > 0)
		min = Math.min(min, Math.abs(f1-f2));
return min;

Test Case:
words = ["practice", "makes", "perfect", "coding", "makes"]
w1: "makes", w2: "coding"

i:1, w1 = 1, w2 = -1 < 0, min = max;
i:3, w1 = 1, w2 = 3, min = 2;
i:4, w1 = 4, w2 = 3, min = 1;
return 1;
*/
public class Solution{
	public int shortestDistance(String[] words, String word1, String word2) {
		if (words == null) return 0;
		int minDistance = Integer.MAX_VALUE;
		int w1 = -1, w2 = -1;
		
		for (int i = 0; i < words.length; i++) {
			if (words[i].equals(word1)) 
				w1 = i;
			else if (words[i].equals(word2)) 
				w2 = i;
			else continue;
			//Once w1 or w2 changed, update the min distance
			if (w1 >= 0 && w2 >= 0)
				minDistance = Math.min(minDistance, Math.abs(w1 - w2));
		}
		return minDistance;
	}
}
/*
495. Teemo Attacking
In LLP world, there is a hero called Teemo and his attacking can make his enemy Ashe be in poisoned condition. Now, given the Teemo's attacking ascending time series towards Ashe and the poisoning time duration per Teemo's attacking, you need to output the total time that Ashe is in poisoned condition.

You may assume that Teemo attacks at the very beginning of a specific time point, and makes Ashe be in poisoned condition immediately.

Example 1:
Input: [1,4], 2
Output: 4
Explanation: At time point 1, Teemo starts attacking Ashe and makes Ashe be poisoned immediately. 
This poisoned status will last 2 seconds until the end of time point 2. 
And at time point 4, Teemo attacks Ashe again, and causes Ashe to be in poisoned status for another 2 seconds. 
So you finally need to output 4.
Example 2:
Input: [1,2], 2
Output: 3
Explanation: At time point 1, Teemo starts attacking Ashe and makes Ashe be poisoned. 
This poisoned status will last 2 seconds until the end of time point 2. 
However, at the beginning of time point 2, Teemo attacks Ashe again who is already in poisoned status. 
Since the poisoned status won't add up together, though the second poisoning attack will still work at time point 2, it will stop at the end of time point 3. 
So you finally need to output 3.
Note:
You may assume the length of given time series array won't exceed 10000.
You may assume the numbers in the Teemo's attacking time series and his poisoning time duration per attacking are non-negative integers, which won't exceed 10,000,000.
*/
/*
a: [1,2] 
t: 2
Iterate a:
	if a[i] - a[i - 1] > t:
		totalTime += t;
	else 
		totalTime+= a[i] - a[i - 1]
return totalTime + t;

Test Case:
a: [1,2] 
t: 2

i = 1, a[1]-a[0] = 2 - 1 = 1 < 2, total+= 1 = 1;
return total+2 = 3;
*/
public class Solution{
	public int findPoisonedDuration(int[] timeSeries, int duration) {
		if (timeSeries == null || timeSeries.length == 0 || duration < 1) return 0;
		int poisonedDuration = 0;
		for (int i = 1; i < timeSeries.length; i++) {
			int t = timeSeries[i] - timeSeries[i - 1];
			if (t < duration) poisonedDuration += t;
			else poisonedDuration += duration;
		}
		return poisonedDuration + duration;
	}
}

/*
448. Find All Numbers Disappread in an Array
Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.

Find all the elements of [1, n] inclusive that do not appear in this array.

Could you do it without extra space and in O(n) runtime? You may assume the returned list does not count as extra space.

Example:

Input:
[4,3,2,7,8,2,3,1]

Output:
[5,6]
*/
/*
Iterate a:
	flip a[a[i] - 1] to negative to mark a[i] appearance
Iterate marked a:
	if a[i] > 0:
		i+1 doesn't appear
*/
/*
Test case:
nums = [1,3,3]
i:0, nums[0] = 1 > 0, [-1,3,3]
i:2, nums[2] = 3 > 0, [-1,3,-3]
i:2, nums[2] = -3 < 0
[-1,3,-3]
i:1, nums[1] = 3 > 0, add 3
*/
public class Solution{
	public List<Integer> findDisappearedNumbers(int[] nums) {
		if (nums == null) return null;
		List<Integer> disappeared = new ArrayList<>();
		//Mark the appearance of each element
		for (int x : nums) {
			int index = Math.abs(x) - 1;//Get the real index
			if (nums[index] > 0)
				nums[index] = -nums[index];
		}
		//Find the missing elements
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] > 0)
				disappeared.add(i + 1);
		}
		return disappeared;
	}
}
/*
442. Find All Duplicates in an Array
Given an array of integers, 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.

Find all the elements that appear twice in this array.

Could you do it without extra space and in O(n) runtime?

Example:
Input:
[4,3,2,7,8,2,3,1]

Output:
[2,3]
*/
/*
Iterate a:
	if a[a[i]] already < 0:
		a[i] appears twice
	else :
		flip the element in position a[i] to negative
*/
/*
test case:
nums=[1,2,2,4]
i:1, nums[0] = 1 > 0, [-1,2,2,4] 
i:2, nums[1] = 2 > 0, [-1,-2,2,4]
i:2, nums[1] = -2 < 0, add 2 to duplicate
i:4, nums[3] = 4 > 0, [-1,-2,2,-4]

*/
public class Solution{
	public List<Integer> findDuplicates(int[] nums) {
		if (nums == null) return null;
		List<Integer> duplicates = new ArrayList<>();
		for (int x : nums) {
			int index = Math.abs(x); //Get the index
			if (nums[index - 1] < 0)
				duplicates.add(index);
			else
				nums[index - 1] = -nums[index - 1];
		}
		return duplicates;
	}
}
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















