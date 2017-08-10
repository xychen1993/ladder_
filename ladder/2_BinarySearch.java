/*
R1. Closest Number in Sorted Array
Given a target number and an integer array A sorted in ascending order, find the index i in A such that A[i] is closest to the given target. Return -1 if there is no element in the array.
Notice: There can be duplicate elements in the array, and we can return any of the indices with same value.

Example
Given [1, 2, 3] and target = 2, return 1.
Given [1, 4, 6] and target = 3, return 1.
Given [1, 4, 6] and target = 5, return 1 or 2.
Given [1, 3, 3, 4] and target = 2, return 0 or 1 or 2.
*/
//O(logN)
public class Solution {
    /**
     * @param A an integer array sorted in ascending order
     * @param target an integer
     * @return an integer
     * Use binary search 
     */
    public int closestNumber(int[] A, int target) {
        if (A == null) {
            return -1;
        }
        int start = 0;
        int end = A.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (A[mid] == target) {
                return mid;
            } else if (A[mid] < target) {
                start = mid;//不要写mid+1，因为mid还是可能会被返回，不能完全排除
            } else {
                end = mid;//不要写mid-1，因为mid还是可能会被返回，不能完全排除
            }
        }
        if (Math.abs(target - A[start]) < Math.abs(A[end] - target)) {
            return start;
        } else {
            return end;
        }
    }
}

/*
R2. Last Position of Target
Find the last position of a target number in a sorted array. Return -1 if target does not exist.
Example
Given [1, 2, 2, 4, 5, 5].
For target = 2, return 2.
For target = 5, return 5.
For target = 6, return -1.
*/
public class Solution {
    /**
     * @param nums: An integer array sorted in ascending order
     * @param target: An integer
     * @return an integer
     */
    public int lastPosition(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int start = 0;
        int end = nums.length - 1;
        //这里start,end内是target的话start一直往前移动,end不动,end是最后那个
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                start = mid;
            } else if (nums[mid] < target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        /*如果没有进入上面的循环会直接调用end,start,所以必须在开头判断是否len==0*/
        if (nums[end] == target) {
            return end;
        }
        if (nums[start] == target){
            return start;
        }
        return -1;
    }
}

/*
R3. Search a 2D Matrix 
Write an efficient algorithm that searches for a value in an m x n matrix. 
This matrix has the following properties: 
Integers in each row are sorted from left to right. 
The first integer of each row is greater than the last integer of the previous row.
*/
//O(logM + logN) = O(logMN), 还有一种直接全局binary search的时间复杂度一样是O(logMN)
public class Solution {
    /**
     * @param matrix, a list of lists of integers
     * @param target, an integer
     * @return a boolean, indicate whether matrix contains target
     * 
     * searchMatrix():
     *  1. use binary search to find the row
     *  2. use binary search to find the target in that row
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        /*Find target row number*/
        int lastCol = matrix[0].length - 1;
        int start = 0;
        int end = matrix.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (matrix[mid][lastCol] == target) {
                return true;
            } else if (matrix[mid][lastCol] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }
        int targetRow = 0;
        if (matrix[start][lastCol] >= target) {//这里注意要用小于等于，否则当等于时却选成了另外一行。
            targetRow = start;
        } else {
            targetRow = end;
        }
        /*Find target column number*/
        start = 0;
        end = lastCol;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (matrix[targetRow][mid] == target) {
                return true;
            } else if (matrix[targetRow][mid] < target) {
                start = mid + 1;
            } else {
                end =  mid - 1;
            }
        }
        if (matrix[targetRow][start] == target || matrix[targetRow][end] == target) {
            return true;
        }
        return false;
    }
}

/*
R4. Maximum Number in Mountain Sequence 
Given a mountain sequence of n integers which increase firstly and then decrease, find the mountain top.
Given nums = [1, 2, 4, 8, 6, 3] return 8
Given nums = [10, 9, 8, 7], return 10
*/
public class Solution {
    /**
     * @param nums a mountain sequence which increase firstly and then decrease
     * @return then mountain top
     * examples
     * input    output
     * null     -1
     * []       -1
     * [1,2]    2
     * [1,2,1]  2
     * [2,1]    2
     */
    public int NOT_FOUND = -1;
    public int mountainSequence(int[] nums) {
        if (nums == null || nums.length == 0) {
            return NOT_FOUND;
        }
        int start = 0;
        int end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (mid < nums.length - 1 && nums[mid] > nums[mid + 1]) {
                end = mid;//这里直接合并成两种情况，而不是三种情况
            } else {
                start = mid;
            }
        }
        /*直接返回大的那个就行，因为确保一定有montain top*/
        return Math.max(nums[start], nums[end]);
    }
}
/*
R5. Search in a Big Sorted Array 
Given a big sorted array with positive integers sorted by ascending order. The array is so big so that you can not get the length of the whole array directly, and you can only access the kth number by ArrayReader.get(k) (or ArrayReader->get(k) for C++). Find the first index of a target number. Your algorithm should be in O(log k), where k is the first index of the target number.
Return -1, if the number doesn't exist in the array.
Notice: If you accessed an inaccessible index (outside of the array), ArrayReader.get will return 2,147,483,647.
*/
/**
 * Definition of ArrayReader:
 * 
 * class ArrayReader {
 *      // get the number at index, return -1 if index is less than zero.
 *      public int get(int index);
 * }
 */
/**
 * Definition of ArrayReader:
 * 
 * class ArrayReader {
 *      // get the number at index, return -1 if index is less than zero.
 *      public int get(int index);
 * }
 */
public class Solution {
    /**
     * @param reader: An instance of ArrayReader.
     * @param target: An integer
     * @return : An integer which is the index of the target number
     * examples
     * intput       target       output
     * null                         -1
     * []                           -1
     * [1, 3, ..]   3               1
     * [1,3, ..]    2               -1
     */
    public int NOT_FOUND = -1;
    public int searchBigSortedArray(ArrayReader reader, int target) {
        if (reader == null) {
            return NOT_FOUND;
        }
        int start = 0;
        int end = 2;
        /*Find the end such that reader.get(end) >= target*/
        while (reader.get(end) < target) {
            end *= 2;
        }
        /*Binary search to find target*/
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            int midNum = reader.get(mid);
            if (midNum == target) {
                end = mid;//这里注意是找第一个出现，end挪动start不动
            } else if (midNum < target) {
                start = mid;
            } else {
                end = mid;
            }
        }
        if (reader.get(start) == target) {
            return start;
        }
        if (reader.get(end) == target) {
            return end;
        }
        return NOT_FOUND;
    }
}
/*
R6. Find Minimum in Rotated Sorted Array 
Suppose a sorted array is rotated at some pivot unknown to you beforehand. (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2). Find the minimum element.
Notice
You may assume no duplicate exists in the array.
*/
public class Solution {
    /**
     * @param nums: a rotated sorted array
     * @return: the minimum number in the array
     * examples
     * nums         output
     * null         -1
     * []           -1
     * [1]          1
     * [1,2,3]      1
     * [3,2,1]      1
     * [3,1,2]      1
     */
    public int NOT_FOUND = -1;
    public int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            return NOT_FOUND;
        }
        int start = 0;
        int end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < nums[end]) {
                end = mid;//这里记得用和最后一个ele比较而不是判断上下坡
            } else {
                start = mid;
            }
        }
        return Math.min(nums[start], nums[end]);
    }
}

/*
R7. Find Peak Element
There is an integer array which has the following features:
The numbers in adjacent positions are different.
A[0] < A[1] && A[A.length - 2] > A[A.length - 1].
We define a position P is a peek if:
A[P] > A[P-1] && A[P] > A[P+1]
Find a peak element in this array. Return the index of the peak.

Notice
The array may contains multiple peeks, find any of them.

Example
Given [1, 2, 1, 3, 4, 5, 7, 6]
Return index 1 (which is number 2) or 6 (which is number 7)
*/
class Solution {
    /**
     * @param A: An integers array.
     * @return: return any of peek positions.
     * examples
     * nums         output
     * null         -1
     * []           -1
     * [1,2,1]      1
     * [1,2,1,2,1]  1/4
     * 
     */
    public int NOT_FOUND = -1;
    public int findPeak(int[] A) {
        if (A == null || A.length < 3) {
            return NOT_FOUND;
        }
        int start = 0;
        int end = A.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (A[mid - 1] > A[mid]) {
                end = mid;//只要在下坡路就是end移动
            } else {
                start = mid;//其他三种情况包括找到peak都是start往那儿移动
            }
        }
        return start;//Cause we move start to mid that is peak, so only need to return start
    }
}
/*
R8. First Bad Version
The code base version is an integer start from 1 to n. One day, someone committed a bad version in the code case, so it caused this version and the following versions are all failed in the unit tests. Find the first bad version.

You can call isBadVersion to help you determine which version is the first bad one. The details interface can be found in the code's annotation part.

Notice
Please read the annotation in code area to get the correct way to call isBadVersion in different language. For example, Java is SVNRepo.isBadVersion(v)

Given n = 5:
isBadVersion(3) -> false
isBadVersion(5) -> true
isBadVersion(4) -> true
Here we are 100% sure that the 4th version is the first bad version.
*/
/**
 * public class SVNRepo {
 *     public static boolean isBadVersion(int k);
 * }
 * you can use SVNRepo.isBadVersion(k) to judge whether 
 * the kth code version is bad or not.
*/
class Solution {
    /**
     * @param n: An integers.
     * @return: An integer which is the first bad version.
     */
    public int findFirstBadVersion(int n) {
        int start = 1;
        int end = n;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (SVNRepo.isBadVersion(mid) == true) {
                end = mid;//移动end，这样可以确保答案在start与end之间
            } else {
                start = mid;//但是这里不能保证start是bad version，最后要判断
            }
        }
        if (SVNRepo.isBadVersion(start) == true) {
            return start;
        }
        return end;
    }
}
/*
R9. Search in Rotated Sorted Array 
*/




















