/*
5.3 Given a positive integer, print the next smallest and the next largest number that have the same number of 1 bits in their binary representation.
*/
/*
brute force:
new num = keep +1 to num:
	count the number of 1s in new num
	if there's the same number of 1s with the original given num:
		return the new num.

bit manipulation:
11001100
76543210 positions
The rightmost bits vary rapidly than the leftmost bits

get the next higher number:
	sub = the rightmost subsequence of 1s (3,2)
	move the leftmost bit 1 of sub by 1: 11010100 
	move all other bits of sub to the right extreme: 11010001
get the next smaller number:
	sub = the leftmost subsequence of 1s (bits 3 through 2)
	subLeft = all other bits left on sub (bits 7 through 4)
	move the rightmost bit 1 of subLeft by 1 : 10101100
	move sub next to the bit we moved previously: 10111000
*/
public class Solution{
	public int getNextHigher(int num) {
		//find the rightmost non-trailing 0 (at position p)
		int c1 = 0; //number of rightmost 1s
		int c0 = 0; //number of trailing 0s
		int c = num;
		while ((c & 1) == 0){
			c >>= 1;
			c0++;
		}
		while ((c & 1) == 1) {
			c >>= 1;
			c1++;
		}
		//filp rightmost non-trailing 0
		int mask = 1 << (c0 + c1);
		num = num | mask;
		
		//clear all the bits on p's right
		mask = ~0 << (c0 + c1);
		num = num & mask;

		//insert enough 1s on the right extreme
		mask = 1 << (c1 - 1) - 1;
		num = num | mask;

		return num;

	}

	public int getNextSmaller(int num) {
		

	}



}

/*
5.2 Given a real number between 0 and 1 (e.g., 0.72) that is passed in as a double, print the binary representation. If the number cannot be represented accurately in binary with at most 32 characters, print "ERROR."
*/
/*
decimalToBinary(d):
	1. d = d * 2
	2. append the integer part of d to result string
	3. d = (d*2) - i
	4. repeat step 1 to 3 until d = 0 or string.length() > 32 
	
*/
/*O(len(N)), O(len(N)) becasue of StringBuilder*/
public class Solution{
	public String decimalToBinary(double d) {
		if (d >= 1 || d <= 0) return "ERROR";
		StringBuilder binary = new StringBuilder();
		binary.append("0.");
		
		while (d > 0) {
			if (binary.length() >= 32) 
				return "ERROR";
			double r = d * 2;
			if (r >= 1) {
				binary.append("1");
				d = r - 1;
			}
			else{
				binary.append("0");
				d = r;
			}
		}

		return binary.toString();

	}
}

/*
You are given two 32-bit numbers,N and M, and two bit positions, i and j. Write a method to insert M into N such that M starts at bit j and ends at bit i. You can assume that the bits j through i have enough space to fit all of M. That is,if M = 10011, you can assume that there are at least 5 bits between j and i. You would not, for example,have j=3 andi=2,because M could not fully fit between bit 3 and bit 2.
EXAMPLE:
Input:N=1000000000, M=10011, i = 2, j = 6 Output: N = 10001001100
*/
/*
1. newN = clear the bits j through i in N
2. newM = shift M to line up with bits j through i 
3. merge newM and newN by performing OR
*/
/*1100011 mask1

1100000 all bits before j must be one, after j must be 0
shift 1s by j + 1
OR
0000011 all bits after i must be 1, before i must be 0
shif 1 by i then -1 

10011 -> 1001100*/
/*O(N),O(1)*/

public class Solution{
	public int updateBits(int m, int n, int i, int j) {
		//clear the bits j through i in n
		int mask = (~0 << (j + 1)) | (1 << i - 1);
		int nCleared = n & mask;

		//shift M to line up with bits j through i
		int mShifted = m << i;

		//merge newM and nCleared by performing OR
		return mShifted | nCleared;
	}
}



