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