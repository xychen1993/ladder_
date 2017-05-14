/*
CC150 2.6 Given a circular linked list, implement an algorithm which returns the node at the beginning of the loop. 
EXAMPLE Input:A ->B->C->D->E-> C[thesameCasearlier] 
Output:C
*/
/*
1. Two pointers, slow and fast;
2. Move fast at a rate of 2 steps, slow at a rate of 1 step
3. When the collide, move slow to the head, keep fast where it is.
4. Move fast and slow at a rate of 1 step,  return the new collide point.
*/
public class Solution{
	public LinkedListNode findLoopBeginning(LinkedListNode l) {
		LinkedListNode firstCollide = findFirstCollideNode(l);
		
		//If loop exists in this list
		if (firstCollide == null || firstCollide.next == null) {
			return null;//No loop
		}

		return findBeginningNode(l, firstCollide);
	}

	//find first collide node
	public LinkedListNode findFirstCollideNode(LinkedListNode l) {
		LinkedListNode fast = l;
		LinkedListNode slow = l;
		
		while (fast != null && fast.next != null) {
			fast = fast.next.next;
			slow = slow.next;
			if (slow == fast) break;
		}

		return fast;
	}
	//find second collide node which is the loop beginning
	public LinkedListNode findBeginningNode(LinkedListNode l, LinkedListNode firstCollide) {
		while (l != firstCollide) {
			l = l.next;
			firstCollide = firstCollide.next;
		}
		return l;
	}
}

/*
CC150/2.5 FOLLOW UP: Suppose the digits are stored in forward order. Repeat the above problem. 
EXAMPLE 
Input:(6 -> 1 -> 7) + (2 -> 9 -> 5).That is,617 + 295. 
Output: 9 -> 1 -> 2.That is, 912.
*/
/*
1. Comparing the length of two lists, padding the shorter list with 0s.
2. Since we need to add the result to the head, in each recursive call must return the reuslt and carry, we need to use a new class Partial Sum to store the current result and carry.
*/
class PartialSum{
	public LinkedListNode sum;
	public int carry;

	public PartialSum(LinkedListNode sum, int carry) {
		this.sum = sum;
		this.carry = carry;
	}
}
public class Solution{
	public LinkedListNode addLists(LinkedListNode l1, LinkedListNode l2) {
		int len1 = length(l1);
		int len2 = length(l2);
		
		if (len1 < len2) {
			l1 = paddingZeros(l1, len2 - len1);
		}else {
			l2 = paddingZeros(l2, len1 - len2);
		}

		PartialSum sum = addListsHelper(l1, l2);

		return sum.carry == 0 ? sum.sum : insertBefore(sum.sum, sum.carry); 
	}

	//Add two lists recursively
	public PartialSum addListsHelper(LinkedListNode l1, LinkedListNode l2) {
		if (l1 == null || l2 == null) {
			return new PartialSum(null, 0);
		}

		//Get the result and carry from next nodes
		PartialSum sum = addListsHelper(l1.next, l2.next);

		int val = sum.carry + l1.value + l2.value;

		sum.sum = insertBefore(sum.sum, val % 10);
		sum.carry = val / 10;

		return sum;

	}

	//insert a new node with value val before l
	public LinkedListNode insertBefore(LinkedListNode l, int val) {
		LinkedListNode node = new LinkedListNode(val);
		node.next = l;
		return node;
	}
	
	//Get the list length
	public int length(LinkedListNode l) {
		int length = 0;
		while (l != null) {
			length++;
			l = l.next;
		}
		return length;
	}
	//Padding the shorter list with 0s
	public LinkedListNode paddingZeros(LinkedListNode l, int zerosNum) {
		for (int i = 0; i < zerosNum; i++) {
			LinkedListNode tmp = new LinkedListNode(0);
			tmp.next = l;
			l = tmp;
		}
		return l;
	}

}

/*
369. Plus One Linked List
Given a non-negative integer represented as non-empty a singly linked list of digits, plus one to the integer.

You may assume the integer do not contain any leading zero, except the number 0 itself.

The digits are stored such that the most significant digit is at the head of the list.

Example:
Input:
1->2->3

Output:
1->2->4
*/
//Find the last not 9 digit, then check if we need to add carry
public class Solution{
	public ListNode plusOne(ListNode head) {
		ListNode dummy = new ListNode(0);
		dummy.next = head;
		ListNode slow = dummy, fast = dummy;

		while (fast.next != null) {
			fast = fast.next;
			if (fast.val != 9) slow = fast;
		}

		if (fast.val != 9) fast.val++;
		else {
			slow.val++;
			while (slow.next != null) {
				slow = slow.next;
				slow.val = 0;
			}
		}

		return dummy.val == 0 ? dummy.next : dummy;
	}
}
