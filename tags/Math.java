/*
537. Complex Number Multiplication
Given two strings representing two complex numbers.

You need to return a string representing their multiplication. Note i2 = -1 according to the definition.

Example 1:
Input: "1+1i", "1+1i"
Output: "0+2i"
Explanation: (1 + i) * (1 + i) = 1 + i2 + 2 * i = 2i, and you need convert it to the form of 0+2i.
Example 2:
Input: "1+-1i", "1+-1i"
Output: "0+-2i"
Explanation: (1 - i) * (1 - i) = 1 + i2 - 2 * i = -2i, and you need convert it to the form of 0+-2i.
Note:

The input strings will not have extra blank.
The input strings will be given in the form of a+bi, where the integer a and b will both belong to the range of [-100, 100]. And the output should be also in this form.
*/
//(a1+b1i)*(a2+b2i) = (a1a2-b1*b2) + (a1b2 + a2b1)i
public class Solution{
	public String complexNumberMultiply(String a, String b) {
		if (a == null || b == null) return "";
		String[] A = a.split("\\+");
		String[] B = b.split("\\+");

		int a1 = Integer.parseInt(A[0]);
		int a2 = Integer.parseInt(B[0]);

		int b1 = Integer.parseInt(A[1].replace("i",""));
		int b2 = Integer.parseInt(B[1].replace("i",""));

		return (a1*a2 - b1*b2)+ "+" + (a1*b2 + a2*b1) + "i";

	}
}