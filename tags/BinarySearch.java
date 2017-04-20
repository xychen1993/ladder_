/*
367. Valid Perfect Square
Given a positive integer num, write a function which returns True if num is a perfect square else False.

Note: Do not use any built-in library function such as sqrt.

Example 1:

Input: 16
Returns: True
Example 2:

Input: 14
Returns: False
*/
//Need to use long to aviod overflow
//O(logN),O(1)
public class Solution{
	public boolean isPerfectSquare(int num) {
		if (num <= 0) return false;
		int low = 1, high = num;
		
		while (low <= high) {
			long mid = (high + low) >>> 1;
			long square = mid * mid;
			if (square == num) return true;
			else if (square > num) high = (int)mid - 1;
			else low = (int)mid + 1;
		}
		
		return false;
	}
}