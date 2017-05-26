/*
CC150: STACK 3.2  How would you design a stack which, in addition to push and pop, also has a function min which returns the minimum element? Push, pop and min should all operate in 0(1) time.
--
Also LeetCode 155. Min Stack
Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

push(x) -- Push element x onto stack.
pop() -- Removes the element on top of the stack.
top() -- Get the top element.
getMin() -- Retrieve the minimum element in the stack.
Example:
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin();   --> Returns -3.
minStack.pop();
minStack.top();      --> Returns 0.
minStack.getMin();   --> Returns -2.
*/
/*
In the stack, we store the (new element - current min)

min = Integer.MAX_VALUE
s{}
Insert e into s:
	s.push(e - min)
	if (e < min) min = e;
Pop from s:
	top = s.peek();
	if (top >= 0) return pop + min
	else {
		minV = min
		min = min - top
		return minV
	}
*/
/*
push:
s:{}, m:MAX_VALUE
6-> s:{0}, m:6
7-> s:{0,1}, m:6
3-> s:{0,1,-3},m:3

pop:
s:{0,1,-3},m:3
s:{0,1},m:3 - (-3) = 6, ->3
s:{0}, m:6, ->6 + 1 = 7
s:{}, m:6, ->6 + 0 = 6

*/

public class MinStack{
	List<Integer> s;
	int curMin;
	public MinStack() {
		s = new ArrayList<>();
		curMin = Integer.MAX_VALUE;
	}

    public void push(int x) {
    	if (curMin == Integer.MAX_VALUE)
    		curMin = x;
    	int gap = x - curMin;
    	s.add(gap);
    	if (gap < 0) {
    		curMin = x; //update the minimum
    	}
        
    }
    
    public void pop() {
        if (s == null || s.size() == 0) return;
        int top = s.get(s.size() - 1);
        if (top < 0) {
        	curMin -= top;
        }
    }
    
    public int top() {
        if (s.size() != 0)
        	return s.get(s.size() - 1);
    }
    
    public int getMin() {
        return curMin;
    }
}
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












