/*
344. Reverse String
Write a function that takes a string as input and returns the string reversed.

Example:
Given s = "hello", return "olleh".
*/
//Use two pointers to go through from both front and end, then swap the value of two pointers
public class Solution{
	public String reverseString(String s) {
		if (s == null) return "";
		char[] chars = s.toCharArray();
		int i = 0, j = s.length() - 1;
		
		while (i < j) {
			char tmp = chars[i];
			chars[i] = chars[j];
			chars[j] = tmp;
			i++;
			j--;
		}

		return new String(chars);
	}

}

/*
167. Two Sum II - Input array is sorted
Given an array of integers that is already sorted in ascending order, find two numbers such that they add up to a specific target number.

The function twoSum should return indices of the two numbers such that they add up to the target, where index1 must be less than index2. Please note that your returned answers (both index1 and index2) are not zero-based.

You may assume that each input would have exactly one solution and you may not use the same element twice.

Input: numbers={2, 7, 11, 15}, target=9
Output: index1=1, index2=2

Show Company Tags
Show Tags
Show Similar Problems
*/
//Use two pointers go through the array from both front and end, shrink the scope based on the sum and target comparison.
public class Solution{
	public int[] twoSum(int[] nums, int target) {
		if (nums.length < 2) return new int[2];
		int i = 0, j = nums.length - 1;

		while (i < j) {
			int sum = nums[i] + nums[j];
			if (sum == target) {
				return new int[]{i + 1,j + 1};
			}else if (sum > target) {
				j--;
			}else {
				i++;
			}
		}

		return new int[2];
	}
}












