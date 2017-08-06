/*
R1. strStr()
For a given source string and a target string, you should output the first index(from 0) of target string in surce string.

If target does not exist in source, just return -1.

Clarification
Do I need to implement KMP Algorithm in a real interview?
Not necessary. When you meet this problem in a real interview, the interviewer may just want to test your basic implementation ability. But make sure your confirm with the interviewer first.
Example
If source = "source" and target = "target", return -1.

If source = "abcdabcdefg" and target = "bcd", return 1.
*/
/*
source		target		result
null					-1
			null		-1
			""			0
""						-1
			
			
source.len < target.len	-1
source		target		-1
abcdabcdefg	bcd

strStr(t, s):
	iter i from 0 to t.len - s.len:
		iter j from 0 to s.len:
			if each t[i + j] == s[j], then return i;
	return -1;
*/
public class Solution {
	public int strStr(String source, String target) {
		if (source == null || target == null) {
			return -1;
		}
		if (target.length() == 0) {
			return 0;
		}
		
		char[] s = source.toCharArray();
		char[] t = target.toCharArray();
		int i = 0;
		while (i < s.length - t.length + 1) {
			int j = 0;
			while (j < t.length && s[i + j] == t[j]) {
				j++;
			}
			if (j == t.length) {
				return i;
			}
			i++;
		}
		return -1;
	}

}

/*
O1. Subsets
Given a set of distinct integers, return all possible subsets.

Notice
Elements in a subset must be in non-descending order.
The solution set must not contain duplicate subsets.

Example
If S = [1,2,3], a solution is:
[
  [3],
  [1],
  [2],
  [1,2,3],
  [1,3],
  [2,3],
  [1,2],
  []
]
*/
/*
S = [1, 2, 3]
use a tree to illustrate DFS: go left = include, go right = not include
http://3.bp.blogspot.com/-jUUvB0asyIc/Vi-PIJVKfPI/AAAAAAAAAm0/OZ4S-tNH9Wg/s1600/subsetTree.png

subsets():
	iter ele in array
		inlcude this ele then subsets(next element);
		don't include this ele then subsets(next element);


test cases:
null   		null
[]     		[[]]
[1,2,3]		[[], [1], [1, 2], [1, 2, 3], [1, 3], [2], [2, 3], [3]]
*/
class Solution {

    public ArrayList<ArrayList<Integer>> subsets(int[] nums) {
        // write your code here
        if (nums == null) {
            return null;
        }
        Arrays.sort(nums);
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        subsets(result, new ArrayList<Integer>(), nums, 0);
        return result;
    }
    
    private void subsets(ArrayList<ArrayList<Integer>> result, ArrayList<Integer> subset, int[] nums, int index) {
        result.add(new ArrayList<Integer>(subset));
        for (int i = index; i < nums.length; i++) {
            subset.add(nums[i]);
            subsets(result, subset, nums, i + 1); //这里不要忘记+1，从下一个element开始
            subset.remove(subset.size() - 1);
        }

    }
}
/*
O2: Subsets II
Given a list of numbers that may has duplicate numbers, return all possible subsets

Notice
Each element in a subset must be in non-descending order.
The ordering between two subsets is free.
The solution set must not contain duplicate subsets.

*/
/**
* @param nums: A set of numbers.
* @return: A list of lists. All valid subsets.
* examples
* intput           subsets
* null             null
* []               [[]]
* [1, 1]           [[], [1], [1, 1]]
* 
* test cases
* intput       subsets
* [1,1]        [[], [1,1]]
* [1,2,2]      [[], [1], [1,2], [1,2,2],[2], [2,2]]
*/
class Solution {

    public ArrayList<ArrayList<Integer>> subsetsWithDup(int[] nums) {
        // write your code here
        if (nums == null) {
            return null;
        }
        Arrays.sort(nums);
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        subsetsWithDup(result, new ArrayList<Integer>(), nums, 0);
        return result;
    }
    private void subsetsWithDup(ArrayList<ArrayList<Integer>> result, ArrayList<Integer> subset, int[] nums, int index) {
        result.add(new ArrayList<Integer>(subset));
        for (int i = index; i < nums.length; i++) {
            /*make sure i should > index so we can add the new element anyway*/
            if (i > index && nums[i - 1] == nums[i]) {
                continue;
            }
            subset.add(nums[i]);
            subsetsWithDup(result, subset, nums, i + 1);
            subset.remove(subset.size() - 1);
        }
    }
}














