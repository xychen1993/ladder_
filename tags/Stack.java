/*
496. Next Greater Element I
You are given two arrays (without duplicates) nums1 and nums2 where nums1’s elements are subset of nums2. Find all the next greater numbers for nums1's elements in the corresponding places of nums2.

The Next Greater Number of a number x in nums1 is the first greater number to its right in nums2. If it does not exist, output -1 for this number.

Example 1:
Input: nums1 = [4,1,2], nums2 = [1,3,4,2].
Output: [-1,3,-1]
Explanation:
    For number 4 in the first array, you cannot find the next greater number for it in the second array, so output -1.
    For number 1 in the first array, the next greater number for it in the second array is 3.
    For number 2 in the first array, there is no next greater number for it in the second array, so output -1.
Example 2:
Input: nums1 = [2,4], nums2 = [1,2,3,4].
Output: [3,-1]
Explanation:
    For number 2 in the first array, the next greater number for it in the second array is 3.
    For number 4 in the first array, there is no next greater number for it in the second array, so output -1.
Note:
All elements in nums1 and nums2 are unique.
The length of both nums1 and nums2 would not exceed 1000.
*/
/*
[5,4,3,6,2]
stack: [5, 4, 3] 6, and 6 > 3 => 5,4,3 -> 6
s : [6, 2]

Iterate arr:
	use a stack to keep track of decreasing subsequence
	if arr[i] > stack.peek():
		pop all the elements less than arr[i]
		for all the poped ones -> arr[i] map(popped, arr[i])
	put arr[i] in stack
Iterate subarr:
	if !map.containsKey(subarr[i])
		subarr[i] = -1
	else
		subarr[i] = map.get(subarr[i])
*/

public class Solution{ 
	public int[] nextGreaterElement(int[] findNum, int[] nums) {
		if (findNum == null || nums == null) return null;
		Map<Integer, Integer> map = new HashMap<>();
		Stack<Integer> stack = new Stack<>();
		
		for (int x : nums) {
			while (!stack.isEmpty() && stack.peek() < x) {
				map.put(stack.pop(), x);
			}
			stack.push(x);
		}
		
		for (int i = 0; i < findNum.length; i++) {
			findNum[i] = map.containsKey(findNum[i]) ? map.get(findNum[i]) : -1;
		}

		return findNum;

	}
}












