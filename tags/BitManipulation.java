/*
5.8 A monochrome screen is stored as a single array of bytes, allowing eight consecutive pixels to be stored in one byte. The screen has width w, where w is divisible by 8 (that is, no byte will be split across rows). The height of the screen, of course, can be derived from the length of the array and the width. Implement a function drawHorizontalLine(byte[] screen,int width,int xl,int x2,int y) which draws a horizontal line from (x1, y) to (x2, y)
*/
/*
drawHorizontalLine(byte[] screen,int width,int xl,int x2,int y)

*/

/*
5.7 An array A contains all the integers from 0 through n, except for one number which is missing. In this problem, we cannot access an entire integer in A with a single operation. The elements of A are represented in binary, and the only operation we can use to access them is "fetch the jth bit of A[i]," which takes constant time. Write code to find the missing integer. Can you do it in O(n) time?
*/
/*
n = 3
A[0,1,3] 2 is missing
method 1: add all integers up, and n * (n + 1) / 2 - sum = the missing integer

000000 = 1

000001 = 5


if n % 2 == 1 then num(0s) = num(1s) + 1
if n % 2 == 0 then num(0s) = num(1s)
num(0s) <= nums(1s)

if num(0s) > nums(1s) then missing lsb = 1
if num(0s) <= nums(1s) then missing lsb = 0 

findMissingNumber(array):
	count the last bit in each number:
		if num(0s) > nums(1s) then missing bit = 1:
			exclude numbers ends with 0
		if num(0s) <= nums(1s) then missing bit = 0 :
			exclude numbers ends with 1
	return lefted numebr
*/
/*O(N), N + N/2 ... 
O(N)
*/
public class Solution{
	public int findMissingNumber(List<BitInteger> array){
		return findMissingNumber(array, 0);
	}

	public int findMissingNumber(List<BitInteger> array, int p){
		if (p >= BitInteger.SIZE) {
			return 0;
		}
		/*count 0s and 1s*/
		List<BitInteger> endsWith0 = new ArrayList<>();
		List<BitInteger> endsWith1 = new ArrayList<>();

		for (BitInteger a : array){
			if (a.fetch(p) == 0) {
				endsWith0.add(a);
			}else {
				endsWith1.add(a);
			}
		}
		
		if (endsWith0.size() > endsWith1.size()) {
			/*missing bit is 1*/
			int b = findMissingNumber(endsWith1, p + 1);
			return (b << 1) | 1;
		}else {
			/*missing bit is 0*/
			int b = findMissingNumber(endsWith0, p + 1);
			return (b << 1) & 0;
		}



	}
}

/*
5.6 Write a program to swap odd and even bits in an integer with as few instructions as possible (e.g., bit 0 and bit 1 are swapped, bit 2 and bit 3 are swapped, and so on).
*/
/*
011011
100111

swap:
	1. odd = mask all odd bits with 101010, then shift right by 1
	2. even = mask all even bits with 010101, then shift left by 1
	3. merge odd and even using OR
*/
public class Solution{
	public int swapOddEvenBits(int n) {
		return ((n & 0xAAAAAAAA) >> 1) | ((n & 0x55555555) << 1);
	}
}

/*5.5 Write a function to determine the number of bits required to convert integer A to integer B.*/
/*
1101
0011
----
1110

use XOR
method 1: simply count the number of bits in A^B that are 1
method 2: continuously filp the least significant bit in c= A^B, count how long it takes c to reach 0.
*/
public class Solution{
	public int bitSwapRequired(int a, int b) {
		int count = 0;
		for (int i = a ^ b; i != 0; i >>= 1) {
			count += i & 1;
		}
		return count;
	}

	public int bitSwapRequired2(int a, int b) {
		int count = 0;
		for (int i = a ^ b; i != 0; i &= i - 1) {
			count++;
		}
		return count;
	}


}

/*
5.4 Explain what the following code does: ((n & (n-1)) == 0).
*/
/*
1. This code checks if n is a power of 2.
2. Clears the least significant bit in n

*/

/*
5.3 Given a positive integer, print the next smallest and the next largest number that have the same number of 1 bits in their binary representation.
*/
/*
brute force:
new num = keep +1 to num:
	count the number of 1s in new num
	if there's the same number of 1s with the original given num:
		return the new num.

11001100 204
11001010 202

bit manipulation:
11001100
76543210 positions
The rightmost bits vary rapidly than the leftmost bits

https://stackoverflow.com/questions/18929306/efficient-way-to-find-next-smaller-number-with-same-number-of-1-bits

get the next higher number:
	Find the rightmost occurrence of "01" in the number and make it "10".
	Justify all following 1-bits as far to the right as possible.
get the next smaller number:
	Find the rightmost occurrence of "10" in the number and make it "01".
	Left-justify all following 1-bits (i.e. don't do anything if the bit you just set is already followed by a 1).
*/
/*
10000000
1 -> 10 

*/
public class Solution{
	public int getNextHigher(int num) {
		if (num <= 0) return -1;
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

		return num > 0 ? num : -1;//avoid overflow

	}
	
	11001110 11001110
	11001111 10111111 10111110
	//Find the rightmost occurrence of "10" in the number and make it "01".	
	//Left-justify all following 1-bits (i.e. don't do anything if the bit you just set is already followed by a 1).
	public int getNextSmaller(int num) {
		//p = the rightmost occurrence of "10" 
		//c1 = the number of trailing 0s during the previous step
		//set p = 1 and p + 1 = 0 (make it 01)
		//set the following bits to 1s
		//

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



