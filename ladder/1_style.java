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
//O(2^N), O(N)
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
//O(2^N),O(N)
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

/*
O3. strStr II 
Implement strStr function in O(n + m) time.
strStr return the first index of the target string in a source string. The length of the target string is m and the length of the source string is n.
If target does not exist in source, just return -1.
*/
//Hard, need to implement KMP

/*
Re1. Permutations
Given a list of numbers, return all possible permutations.

Notice
You can assume that there is no duplicate numbers in the list.

Example
For nums = [1,2,3], the permutations are:
[
  [1,2,3],
  [1,3,2],
  [2,1,3],
  [2,3,1],
  [3,1,2],
  [3,2,1]
]
*/
/**
* @param nums: A list of integers.
* @return: A list of permutations.
* 
* examples
* input        permutations
* null         null
* []           [[]]
* [1]          [[1]]
* [1,2]        [[1,2],[2,1]]: we insert 2 into the previous premutations
* [1,2,3]      [[3,1,2],[1,3,2],[1,2,3],[3,2,1],[2,3,1],[2,1,3]]
* 
*1. recursive way
* permute(nums): 
*  pre = get all the previous premuations
*  result = insert current element into each permuation at any possible position
*  return result
* 
*2. Can use list to stimulate stack to do it in non-recursive way
*
* test cases:
* input        permutations
* null         null
* []           [[]]
* [1]          [[1]]
* [1,2]        [[2,1], [1,2]]
*/
//O
public class Solution {
	//recursion 从N-1推导出N的思路。还有直接排列组合traverse的思路，就是直接排列组合
    public List<List<Integer>> permute(int[] nums) {
        if (nums == null) {
            return null;
        }
        return permute(nums, nums.length - 1);
    }
    private List<List<Integer>> permute(int[] nums, int index) {
        ArrayList<List<Integer>> results = new ArrayList<List<Integer>>();
        if (index == -1) {
            results.add(new ArrayList<Integer>());
            return results;
        }
        List<List<Integer>> previous = permute(nums, index - 1);
        for (List<Integer> prePermute : previous) {
            for (int i = 0; i <= prePermute.size(); i++) {
                prePermute.add(i, nums[index]);
                results.add(new ArrayList<Integer>(prePermute));
                prePermute.remove(i);
            }
        }
        return results;
    }

    //non-recursive: using list to stimulate stack 
    public List<List<Integer>> permute(int[] nums) {
        if (nums == null) {
            return null;
        }
        ArrayList<List<Integer>> permutations = new ArrayList<List<Integer>>();
        permutations.add(new ArrayList<Integer>());
        for (int i = 0; i < nums.length; i++) {
            int size = permutations.size();
            for (int j = 0; j < size; j++) {
                List<Integer> permutation = permutations.get(0);
                permutations.remove(0);
                for (int k = 0; k <= permutation.size(); k++) {
                    permutation.add(k, nums[i]);
                    permutations.add(new ArrayList<Integer>(permutation));
                    permutation.remove(k);
                }
            }
        }
        return permutations;
    }
    

}

/*
RE2: Permutations II
Given a list of numbers with duplicate number in it. Find all unique permutations.

Example
For numbers [1,2,2] the unique permutations are:
[
  [1,2,2],
  [2,1,2],
  [2,2,1]
]
*/
/**
* @param nums: A list of integers.
* @return: A list of unique permutations.
* examples
* input        output
* null         null
* []           [[]]
* [1]          [[1]]
* [1,2]        [[2,1] [1,2]]
* [1,2,2]      [[2,2,1] [2,1,2], [2,1,2]]
* 
* permute(nums):
*  do the combinations, make sure the previous elem that is the same as cur is used before we use cur to avoid duplicates.
* 
*/
class Solution {

    public List<List<Integer>> permuteUnique(int[] nums) {
            ArrayList<List<Integer>> rst = new ArrayList<List<Integer>>();
        if (nums == null) {
            return rst;
        }
        Arrays.sort(nums);
        boolean[] used = new boolean[nums.length];
        permuteUnique(rst, new ArrayList<Integer>(), used, nums);
        return rst;
    } 
    public void permuteUnique(ArrayList<List<Integer>> rst,  ArrayList<Integer> permutation, boolean[] used, int[] nums) {
        if (permutation.size() == nums.length) {
            rst.add(new ArrayList<Integer>(permutation));
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i] == true || (i > 0 && nums[i] == nums[i - 1] && used[i - 1] == false)) {
                continue;//[1,2,2]第一个2用了才可用第二个2，也就是利用used使他们看起来不是同一个elem，确保无重复
            }
            used[i] = true;
            permutation.add(nums[i]);
            permuteUnique(rst, permutation, used, nums);
            used[i] = false;
            permutation.remove(permutation.size() - 1);
        }
        
    }
    
    
}















