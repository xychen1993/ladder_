/*
A child is running up a staircase with n steps, and can hop either 1 step, 2 steps, or 3 steps at a time. Implement a method to count how many possible ways the child can run up the stairs.
*/
/*
stairs ways
1 1
2 2 = 1 + 1 or 2
3 3 = 1 + 1 + 1 or 2 + 1 -> the num of ways for 2 stairs if we divide into 2 + 1
	  1 + 2 - > num of ways for 1 stairs if we divid into 1 + 2

waysToRunUpStairs():
*/