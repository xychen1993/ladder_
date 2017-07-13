/*
*/

/*
cc150: 3.7 An animal shelter holds only dogs and cats, and operates on a strictly "first in, first out" basis. People must adopt either the "oldest" (based on arrival time) of all animals at the shelter, or they can select whether they would prefer a dog or a cat (and will receive the oldest animal of that type). They cannot select which specific animal they would like. Create the data structures to maintain this system and implement operations such as enqueue, dequeueAny, dequeueDog and dequeueCat.You may use the built-in LinkedList data structure.
*/
abstract class Animal {
	private String name;
	private int order;

	public Animal(String name) {
		this.name = name;
	}
	//the smaller the order
	public void setOrder(int order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public int getOrder() {
		return order;
	}

	public boolean isOrderThan(Animal animal) {
		return order < animal.getOrder();
	}
}

class Dog extends Animal{
	public Dog(String name) {
		super(name);
	}
}

class Cat extends Animal {
	public Cat (String name) {
		super(name);
	}
}

class AnimalShelter {
	private LinkedList<Dog> dogs;
	private LinkedList<Cat> cats;
	private int allAnimalNum;

	public AnimalShelter() {
		dogs = new LinkedList<>();
		cats = new LinkedList<>();
		allAnimalNum = 0;
	}

	public void enqueue(Animal animal) {
		if (animal == null) return;
		animal.setOrder(allAnimalNum++);

		if (animal instanceof Dog) {
			dogs.add((Dog)animal);
		}else {
			cats.add((Cat)animal);
		}
	}

	public Cat dequeueCat() {
		if (!cats.isEmpty()) {
			return cats.poll();
		}else return null;
	}

	public Dog dequeueDog() {
		if (!dogs.isEmpty()) {
			return dogs.poll();
		}else return null;
	}

	public Animal dequeueAny() {
		if (!dogs.isEmpty() && !cats.isEmpty()) {
			if (cats.peek().isOrderThan(dogs.peek())) return cats.poll(); 
			else return dogs.poll(); 
		}else if (dogs.isEmpty()) {
			return dequeueCat();
		}else {
			return dequeueDog(); 
		} 
	}
}
/*
cc150: 3.6 Write a program to sort a stack in ascending order (with biggest items on top).
You may use at most one additional stack to hold items, but you may not copy the
elements into any other data structure (such as an array). The stack supports the
following operations: push, pop, peek, and isEmpty
*/
/*
s1:[2,3,1] ->[1,2,3]
s2:[]
----
[2,3]
[1,]

[2,1]
[3,]

[2]
[3,1]

[1]
[3,2,1]
----
*/
//T:O(N^2),S:O(N)
public class Solution{
	public Stack sortStack(Stack<Integer> s) {
		if (s == null) return null;
		Stack<Integer> res = new Stack<>();

		while (!s.isEmpty()) {
			int pop = s.pop();
			while (!res.isEmpty() && pop < res.peek()) {
				s.push(res.pop());
			}
			res.push(pop);
			
		}
		return res;
	}
}
/*
CC150:3.5 Implement a MyQueue class which implements a queue using two stacks
*/
class MyQueue {
	Stack<Integer> in;
	Stack<Integer> out;

	public MyQueue() {
		in = new Stack<>();
		out = new Stack<>();
	}

	public void offer(int n){
		in.push(n);
	}
	
	public void shiftStacks() {
		if (out.isEmpty()) {
			while (!in.isEmpty()) {
				out.push(in.pop());
			}
		}
	}

	public int peek() {
		shiftStacks();
		return out.peek();
		
	}

	//No need to worry about empty queue, because users should use isEmpty() to check before pull;
	public int pull() {
		shiftStacks();
		return out.pop();

	}

	public boolean isEmpty() {
		return in.isEmpty() && out.isEmpty();
	}

}

/*
In the classic problem of the Towers of Hanoi, you have 3 towers and Ndisks of different sizes which can slide onto any tower. The puzzle starts with disks sorted in ascending order of size from top to bottom (i.e., each disk sits on top of an even larger one). You have the following constraints:
(1) Only one disk can be moved at a time.
(2) A disk is slid off the top of one tower onto the next tower.
(3) A disk can only be placed on top of a larger disk.
Write a program to move the disksfrom the first tower to the last using stacks.
*/
class Tower {
	private Stack<Integer> tower;
	private int index;

	public Tower(int index) {
		tower = new Stack<>();
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void add(int i) {
		if (!tower.isEmpty() && tower.peek() <= i) {
			System.out.println("cannot add " + i + " into " + getIndex());
		}else {
			tower.push(i);
		}
	}
	//Don't worry about cannot add top into t because moveTopTo() in moveDisks() makes sure that top < t.peek()
	public void moveTopTo(Tower t) {
		int top = tower.pop();
		t.add(top);
		System.out.println("move " + top + " from " + getIndex() + " to " + t.getIndex());

	}

	public void moveDisks(int n, Tower destination, Tower buffer) {
		if (n > 0) {
			moveDisks(n - 1, buffer, destination);
			moveTopTo(destination);
			buffer.moveDisks(n - 1, destination, this);
		}
	}
}

/*
cc150: Imagine a (literal) stack of plates. If the stack gets too high, it migh t topple. Therefore, in real life, we would likely start a new stack when the previous stack exceeds some threshold. Implement a data structure SetOfStacks that mimics this. SetOfStacks should be composed of several stacks and should create a new stack once the previous one exceeds capacity. SetOfStacks.push() and SetOfStacks.pop () should behave identically to a single stack (that is, pop () should return the same values as it would if there were just a single stack).
FOLLOW UP
Implement a function popAt(int index) which performs a pop operation on a
specific sub-stack.
*/
class SetOfStacks{
	List<Stack> stackList;
	int capacity;
	
	//Constructor
	public SetOfStacks(int capacity) {
		stackList = new ArrayList<>();
		this.capacity = capacity;
	}

	public Stack getLastStack() {
		if (stackList.isEmpty()) return null;
		else return stackList.get(stackList.size() - 1);
	}

	public void insertNewStack() {
		stackList.add(new Stack<Integer>());
	}

	public void removeLastStack() {
		if (!stackList.isEmpty())
			stackList.remove(stackList.size() - 1);
	}

	public void push(int x) {
		if (getLastStack() == null){
			insertNewStack();
		}
		Stack<Integer> s = getLastStack();
		if (s.size() == capacity) {
			insertNewStack();
			s = getLastStack();
		}
		s.push(x);
		System.out.println(stackList.size());
	}

	public int pop() {
		if (getLastStack() == null) return 0;
		Stack<Integer> s = getLastStack();
		int top = s.pop();
		if (s.isEmpty()) 
			removeLastStack();
		System.out.println(stackList.size());
		return top;

	}
}
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
	List<Long> s;
	long curMin;
	public MinStack() {
		s = new ArrayList<>();
	}

    public void push(int x) {
    	if (s.size() == 0) {
    	    curMin = x;
    	    s.add(0L);
    	}else {
        	s.add(x - curMin);
        	if (x < curMin) {
        		curMin = x; //update the minimum
        	}
    	}
    }
    
    public void pop() {
        if (s.size() == 0) return;
        long top = s.get(s.size() - 1);
        if (top < 0L) {
        	curMin -= top;
        }
        s.remove(s.size() - 1);
    }
    
    public int top() {
        long top = 0L;
        if (s.size() != 0) {
            top = s.get(s.size() - 1);
            if (top > 0L) return (int)(top + curMin);
            return (int)(curMin);
        }
        return (int)top;
    }
    
    public int getMin() {
        return (int)curMin;
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












