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
