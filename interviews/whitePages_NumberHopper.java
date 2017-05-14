/*
类似LC.45 Jump Game II, 不过需要判断是否可以到最后，可以的话返回step序列
Given an array of non-negative integers, you are initially positioned at the first index of the array.

Each element in the array represents your maximum jump length at that position.

Your goal is to reach the last index in the minimum number of jumps.

For example:
Given array A = [2,3,1,1,4]

The minimum number of jumps to reach the last index is 2. (Jump 1 step from index 0 to 1, then 3 steps to the last index.)
*/
//List version
import java.util.*;

public class whitePages_NumberHopper{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<Integer> nums = new ArrayList<>();

		try {
			while (sc.hasNext()){
				nums.add(sc.nextInt());
            }
		}catch (NumberFormatException e) {
        	System.out.println("NumberFormatException");
        }

        System.out.println(jump(nums));
	}
	public static List jump(List<Integer> nums) {
		List<Integer> jumps = new ArrayList<>();
		if (nums == null || nums.size() == 0) return jumps;
		int end = 0, farthest = 0;
		
		for (int i = 0; i < nums.size(); i++) {
			if (i > farthest) return new ArrayList<>();
			farthest = Math.max(farthest, nums.get(i) + i);
			//System.out.println(i +" "+ farthest + " " + end);
			//Reach the end
			if (farthest >= nums.size() - 1 && i != 0) {
				jumps.add(i);
				jumps.add(nums.size() - 1);
				break;
			}
			//Keep going
			if (i == end) {
				jumps.add(i);
				end = farthest;
			}

		}
		return jumps;
	}
}

//int[] version
public class Solution{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<Integer> nums = new ArrayList<>();

        System.out.println(jump(nums(new int[]{})));
	}
	public static List jump(int[] nums) {
		List<Integer> jumps = new ArrayList<>();
		if (nums == null || nums.length == 0) return jumps;
		int end = 0, farthest = 0;
		
		for (int i = 0; i < nums.length; i++) {
			if (i > farthest) return new ArrayList<>();
			farthest = Math.max(farthest, nums[i] + i);
			//System.out.println(i +" "+ farthest + " " + end);
			//Reach the end
			if (farthest >= nums.length - 1 && i != 0) {
				jumps.add(i);
				jumps.add(nums.length - 1);
				break;
			}
			//Keep going
			if (i == end) {
				jumps.add(i);
				end = farthest;
			}

		}
		return jumps;
	}
}