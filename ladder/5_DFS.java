/*
R1. Combination Sum II 

Given a collection of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.

Each number in C may only be used once in the combination.

Notice

All numbers (including target) will be positive integers.
Elements in a combination (a1, a2, … , ak) must be in non-descending order. (ie, a1 ≤ a2 ≤ … ≤ ak).
The solution set must not contain duplicate combinations.
*/
//O(2^N * N)
public class Solution {
    /**
     * @param num: Given the candidate numbers
     * @param target: Given the target number
     * @return: All the combinations that sum to target
     */
    public List<List<Integer>> combinationSum2(int[] num, int target) {
        List<List<Integer>> solution = new ArrayList<>();
        if (num == null || target < 0) {
            return solution;
        }
        //sort num
        Arrays.sort(num);
        //iter num, do DFS to find all combinations
        dfsCombinations(solution, new ArrayList<Integer>(), num, target, 0);
        return solution;
    }
    private void dfsCombinations(List<List<Integer>> solution, ArrayList<Integer> combination, int[] num, int target, int index) {
        if (target == 0) {
            solution.add(new ArrayList<Integer>(combination));
        }
        for (int i = index; i < num.length; i++) {
            /*i != index就确保了不是同一个1可以被选中*/
            if (i != index && num[i] == num[i - 1] ) {
                continue;
            }
            if (num[i] > target) {
                break;
            }
            combination.add(num[i]); //go left, keep it
            dfsCombinations(solution, combination, num, target - num[i], i + 1);
            combination.remove(combination.size() - 1); //go right, don't keep it
        }
    }
}
/*
R2. Combination Sum 
Given a set of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.

The same repeated number may be chosen from C unlimited number of times.

Notice

All numbers (including target) will be positive integers.
Elements in a combination (a1, a2, … , ak) must be in non-descending order. (ie, a1 ≤ a2 ≤ … ≤ ak).
The solution set must not contain duplicate combinations.

Example
Given candidate set [2,3,6,7] and target 7, a solution set is:

[7]
[2, 2, 3]
*/
/*
这里注意，是每个元素可以重复使用，虽然从0开始，但靠传递是i不是i+1即可,不需要用used
 需要用used是permutations每个元素只能用一次，但是前面元素可能在后面元素后面。
*/
//O(2^N * N)
public class Solution {
    /**
     * @param candidates: A list of integers
     * @param target:An integer
     * @return: A list of lists of integers
     * [1,2],3
     * [1,1,1]
     * [1,2]
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> results = new ArrayList<>();
        if (candidates == null || target < 0) {
            return results;
        }
        //sort candidates
        Arrays.sort(candidates);
        //use dfs to find combinations
        dfsCombinations(results, new ArrayList<Integer>(), candidates, target, 0);
        return results;
    }
    private void dfsCombinations(List<List<Integer>> results, ArrayList<Integer> combination, int[] candidates, int target, int index) {
        if (target == 0) {
            results.add(new ArrayList<Integer>(combination));
        }
        for (int i = index; i < candidates.length; i++) {
            if (candidates[i] > target) {
                break;
            }
            if (i > index && candidates[i] == candidates[i - 1]) {
                continue;
            }
            //这里注意，是每个元素可以重复使用，虽然从0开始，但靠传递是i不是i+1即可,不需要用used
            //需要用used是permutations每个元素只能用一次，但是前面元素可能在后面元素后面。
            combination.add(candidates[i]);
            dfsCombinations(results, combination, candidates, target - candidates[i], i);
            combination.remove(combination.size() - 1);
        }
    }
}

/*
R3. Palindrome Partitioning 
Given a string s, partition s such that every substring of the partition is a palindrome.

Return all possible palindrome partitioning of s.

Example
Given s = "aab", return:

[
  ["aa","b"],
  ["a","a","b"]
]
*/
//O(N^2 * N)
public class Solution {

    public List<List<String>> partition(String s) {
        List<List<String>> results = new ArrayList<>();
        if (s == null) {
            return results;
        }
        //iter gaps, find all solutions
        dfsPartitions(results, new ArrayList<String>(), s, 0);
        return results;
    }
    private void dfsPartitions(List<List<String>> results, ArrayList<String> substrings, String s, int index) {
        if (index == s.length()) {
            results.add(new ArrayList<String>(substrings));
            return;
        }
        for (int i = index; i < s.length(); i++) {
            String substr = s.substring(index, i + 1);
            if (!isPalindrome(substr)) {
                continue;
            }
            substrings.add(substr);
            dfsPartitions(results, substrings, s, i + 1);
            substrings.remove(substrings.size() - 1);
        }
    }
    private boolean isPalindrome(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (str.charAt(i) != str.charAt(len - i - 1)) {
                return false;
            }
        }
        return true;
    }
}
/*
R4. Permutations
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
    //dfs
    //bfs
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
R5: Permutations II
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
/*
R6. Subsets II 
Given a list of numbers that may has duplicate numbers, return all possible subsets

Notice
Each element in a subset must be in non-descending order.
The ordering between two subsets is free.
The solution set must not contain duplicate subsets.

Example
If S = [1,2,2], a solution is:

[
  [2],
  [1],
  [1,2,2],
  [2,2],
  [1,2],
  []
]
*/
//同级别的重复数字只能用一次，遇见重复跳过，i+1来使得在下一级别可以继续用。
class Solution {
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
R7. Word Ladder II 
Given two words (start and end), and a dictionary, find all shortest transformation sequence(s) from start to end, such that:

Only one letter can be changed at a time
Each intermediate word must exist in the dictionary

Notice
All words have the same length.
All words contain only lowercase alphabetic characters.

Example
Given:
start = "hit"
end = "cog"
dict = ["hot","dot","dog","lot","log"]
Return
  [
    ["hit","hot","dot","dog","cog"],
    ["hit","hot","lot","log","cog"]
  ]
*/
/*
for word lenght k, and the depth of trasation is d
time: O(d*(k*26) * )？？？
*/
public class Solution {
    /**
      * @param start, a string
      * @param end, a string
      * @param dict, a set of string
      * @return a list of lists of string
      * 1. 注意每次add完继续dfs之后就要remove！
      * 2. map<word, list<next>> 因为dfs会继续访问所有map.get(next)，所以事先要存好所有dcit里word为key的map！
      * 3. 已经存过距离的(由于是bfs所以先存的距离肯定是最近的)跳过，并且获取parent的distance+1即可。
      * 4. 不可以map<word, list<next>>前面访问过的就不往next里存，因为一个child可以对应多个父亲！
      */
    public List<List<String>> findLadders(String start, String end, Set<String> dict) {
        List<List<String>> result = new ArrayList<>();
        if (start == null || end == null || dict == null) {
            return result;
        }
        
        Map<String, Integer> distanceMap = new HashMap<>();
        Map<String, ArrayList<String>> nextWordsMap = new HashMap<>();
        List<String> sequence = new ArrayList<>();
        
        //get shortest length, bfs
        //map<word, list of possible next words>
        //map2<word, distance from word to end>
        initial(start, end, dict, distanceMap, nextWordsMap);
        //search for all sequences with that length
        searchShortestSeq(start, end, distanceMap, nextWordsMap, result, sequence);
        return result;
    }
    private void initial(String start, String end, Set<String> dict, Map<String, Integer> distanceMap, Map<String, ArrayList<String>> nextWordsMap) {
        Queue<String> q = new LinkedList<>();
        q.offer(start);
        distanceMap.put(start, 0);
        dict.add(start);//有start/end不在dict里的情况...
        dict.add(end);
        for (String s: dict) {
            nextWordsMap.put(s, new ArrayList<String>());
        }
        while (!q.isEmpty()) {
            String curt = q.poll();
            if (curt.equals(end)) {
                break;
            }
            List<String> nextWords = getNextWords(curt, dict);
            for (String next : nextWords) {
                 nextWordsMap.get(curt).add(next);
                //已经存过距离的(由于是bfs所以先存的距离肯定是最近的)跳过
                if (!distanceMap.containsKey(next)) {
                    distanceMap.put(next, distanceMap.get(curt) + 1);
                    q.offer(next);
                }
                if (next.equals(end)) {
                    break;
                }
            }
        }
    }
    private void searchShortestSeq(String start, String end, Map<String, Integer> distanceMap, Map<String, ArrayList<String>> nextWordsMap, List<List<String>> result, List<String> sequence) {
        sequence.add(start);
        if (start.equals(end)) {
            result.add(new ArrayList<String>(sequence));
            return;
        }
        List<String> nextWords = nextWordsMap.get(start);
        for (String next : nextWords) {
            if (distanceMap.containsKey(next) && distanceMap.get(start) + 1 == distanceMap.get(next)) {
                searchShortestSeq(next, end, distanceMap, nextWordsMap, result,sequence);
                sequence.remove(sequence.size() - 1);
            }
        }
    }
    private ArrayList<String> getNextWords(String curt, Set<String> dict) {
        ArrayList<String> nextWords = new ArrayList<>();
        int len = curt.length();
        for (int i = 0; i < len; i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                if (curt.charAt(i) == ch) {
                    continue;
                }
                String next = curt.substring(0, i) + ch + curt.substring(i + 1, len);
                if (dict.contains(next)) {
                    nextWords.add(next);
                }
            }
        }
        return nextWords;
    }
}














