/*
R1. Insert into a Cyclic Sorted List 

Given a node from a cyclic linked list which has been sorted, write a function to insert a value into the list such that it remains a cyclic sorted list. The given node can be any single node in the list. Return the inserted new node.

Notice
3->5->1 is a cyclic list, so 3 is next node of 1.
3->5->1 is same with 5->1->3
*/
//O(N)
public class Solution {

    public ListNode insert(ListNode node, int x) {
        ListNode inserted = new ListNode(x);
        if (node == null) {
            inserted.next = inserted;
            return inserted;
        }
        ListNode curt = node;
        while (curt.next != node) {
            //要加等号因为可能有duplicates！
            if (curt.val <= x && curt.next.val >= x) {
                inserted.next = curt.next;
                curt.next = inserted;
                break;
            }
            curt = curt.next;
        }
        if (curt.next == node) {
            inserted.next = node;
            curt.next = inserted;
        }
        return inserted;
    }
}
/*
R2. Merge Two Sorted Lists 
Merge two sorted (ascending) linked lists and return it as a new sorted list. The new sorted list should be made by splicing together the nodes of the two lists and sorted in ascending order.
Example
Given 1->3->8->11->15->null, 2->null , return 1->2->3->8->11->15->null.
*/
//O(N)
public class Solution {
    
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode dummy = new ListNode(0);
        ListNode curt = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val > l2.val) {
                curt.next = l2; 
                l2 = l2.next;
            } else {
                curt.next = l1;
                l1 = l1.next;
            }
            curt = curt.next;
        }
        if (l1 == null) {
            curt.next = l2;
        }
        if (l2 == null) {
            curt.next = l1;
        }
        return dummy.next;
    }
}
/*
R3. Subarray Sum 
Given an integer array, find a subarray where the sum of numbers is zero. Your code should return the index of the first number and the index of the last number.

Notice
There is at least one subarray that it's sum equals to zero.

*/
//O(N),O(N)
public class Solution {

    public ArrayList<Integer> subarraySum(int[] nums) {
        ArrayList<Integer> result = new ArrayList<>();
        if (nums == null) {
            return result;
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);//必须要存0,0入map！因为可能第一个元素为0直接可以返回
        int[] sum = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            sum[i + 1] = sum[i] + nums[i];
            if (map.containsKey(sum[i + 1])) {
                result.add(map.get(sum[i + 1]));
                result.add(i);
                return result;
            }
            map.put(sum[i + 1], i + 1);
        }
        return result;
    }
}










