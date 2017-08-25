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
/*
R4. Maximum Subarray 
Given an array of integers, find a contiguous subarray which has the largest sum.

Notice
The subarray should contain at least one number.

Example
Given the array [−2,2,−3,4,−1,2,1,−5,3], the contiguous subarray [4,−1,2,1] has the largest sum = 6.
*/
/*
kadane's algorithm变体，本质是greedy
kadane:
遍历元素，计算结束于这个元素的max subarray sum（每个元素有三个选择，在这三个里选择最大的：
1. 只选择自己作为subarray（在这里不会选择这个，因为这个算法保证了前面的sum>=0，肯定自己+上一个元素是比较大的）; 2.选择自己+上一个元素的最大sum（也就是这个和第三选择0去比较）。
3.如果前两个都是负数，那还不如不选，空子集 = 0.）然后记录全局最大。

改版：
上面的方法要求至少有一个正数才可以，而本题有全是负数的输入，而且subarray至少得有一个数字。也即为去掉最后一个选择即可。
基本思想仍然是遍历元素，计算结束于这个元素的max subarray sum（每个元素有两个选择，在这两个里选择最大的：
1. 只选择自己作为subarray（前面不能保证sum>=0，还得比较）; 
2. 选择自己+上一个元素的最大sum。）然后记录全局最大。
*/
public class Solution {

    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int curtMax = nums[0];
        int maxSoFar = nums[0];
        for (int i = 1; i < nums.length; i++) {
            curtMax = Math.max(nums[i], nums[i] + curtMax); //两个选择里选其一
            maxSoFar = Math.max(curtMax, maxSoFar);
        }
        return maxSoFar;
    }
}
/*
R5. Subarray Sum Closest 
Given an integer array, find a subarray with sum closest to zero. Return the indexes of the first number and last number.

Example
Given [-3, 1, 1, -3, 5], return [0, 2], [1, 3], [1, 1], [2, 2] or [0, 4].
*/
/*
这里不能用greedy，因为 求closest，如果你只记录当前closest的可能会错，比如前面 -100, 1之间选了1因为1更接近，但是下一个元素是100，其实应该选前面那个-100，这样就错了。

用sum[]，排序后找sum[]里最接近的两个元素。
*/
class Sum {
    public int sum;
    public int index;
    public Sum(int sum, int index) {
        this.sum = sum;
        this.index = index;
    }
}
public class Solution {

    public int[] subarraySumClosest(int[] nums) {
        int[] result = new int[2];
        if (nums == null || nums.length == 0) {
            return result;
        }
        Sum[] sum = new Sum[nums.length + 1];
        sum[0] = new Sum(0, 0);
        //get sum[], sum[i] = nums[0] + nums[1] + ... nums[i]
        int curtSum = 0;
        for (int i = 0; i < nums.length; i++) {
            curtSum += nums[i];
            sum[i + 1] = new Sum(curtSum, i + 1);
        }
        //sort sum[] in ascending order
        Arrays.sort(sum, new Comparator<Sum>() {
            public int compare(Sum a, Sum b) {
                return a.sum - b.sum;
            }
        });
        //find closest two elements in sum[]
        int closest = Integer.MAX_VALUE;
        for (int i = 1; i < sum.length; i++) {
            if (closest > sum[i].sum - sum[i - 1].sum) {
                closest = sum[i].sum - sum[i - 1].sum;
                //sum[i+1,j] = sum[j] - sum[i]
                result[0] = Math.min(sum[i].index, sum[i - 1].index);
                result[1] = Math.max(sum[i].index, sum[i - 1].index) - 1;
            }
        }
        return result;
    }
}
/*
R6. Copy List with Random Pointer 
A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.

Return a deep copy of the list.
*/
//这里注意random Pointer可能指向null，所以在curt.random.next前要判断一下random是不是指向null
public class Solution {

    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return null;
        }
        //get new linked list: old->new->old->new 
        head = insertNewNodes(head);
        //iter nodes and clone random pointers
        head = cloneRandomPointers(head);
        //get the new linekd list that only contains new nodes
        return getNewLinkedList(head);
    }
    private RandomListNode insertNewNodes(RandomListNode head) {
        RandomListNode curt = head;
        while (curt != null) {
            RandomListNode curt2 = new RandomListNode(curt.label);
            curt2.next = curt.next;
            curt.next = curt2;
            curt = curt2.next;
        }
        return head;
    }
    private RandomListNode cloneRandomPointers(RandomListNode head) {
        RandomListNode curt = head;
        while (curt != null) {
            RandomListNode curt2 = curt.next;
            if (curt.random != null) {
                curt2.random = curt.random.next;
            } else {
                curt2.random = null;
            }
            curt = curt2.next;
        }
        return head;
    }
    private RandomListNode getNewLinkedList(RandomListNode head) {
        if (head == null) {
            return null;
        }
        RandomListNode newHead = head.next;
        RandomListNode curt = newHead;
        head = curt.next;
        while (head != null) {
            curt.next = head.next;
            curt = curt.next;
            head = curt.next;
        }
        return newHead;
    }
}
/*
R7. Linked List Cycle 
Given a linked list, determine if it has a cycle in it.
*/
public class Solution {
    public boolean hasCycle(ListNode head) { 
        if (head == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }
}
/*
R8. Sort List 
Sort a linked list in O(n log n) time using constant space complexity.
*/
/*
merge sort, 每次分裂成两半，然后merge，靠merge来排序（两个node merge就会排序，然后一层层变有序）
注意如果是偶数个node，一定要取前面那个middle，否则2个node无限循环
*/
public class Solution {
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        //split in half with O(N) time, using merge sort
        ListNode middle = getMiddle(head);
        
        ListNode right = sortList(middle.next);
        middle.next = null;//split
        //注意如果是偶数个node，一定要取前面那个middle，否则2个node无限循环
        ListNode left = sortList(head);
        
        return merge(left, right);
    }
    private ListNode merge(ListNode left, ListNode right) {
        ListNode dummy = new ListNode(0);
        ListNode curt = dummy;
        while (left != null && right != null) {
            if (left.val < right.val) {
                curt.next = left;
                left = left.next;
            } else {
                curt.next = right;
                right = right.next;
            }
            curt = curt.next;
        }
        if (left == null) {
            curt.next = right;
        }
        if (right == null) {
            curt.next = left;
        }
        return dummy.next;
    }
    private ListNode getMiddle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

}




