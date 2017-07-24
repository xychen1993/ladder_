/*
Generate a list of primes.
*/
/*
1. start with a list of all numbers up through some value max;
2. cross off all the numbers divisible by 2;
3. get next prime number (next non-crossed off number)
4. cross all the numbers divisible by it. 
5. repeat step 3 to 4 until we can't get next prime, wind-up with a list of primes from 2 through x.
*/
/*O(NloglogN), O(N)*/
public class Solution{
	public boolean[] getPrimes(int max) {
		boolean primes = new boolean[max + 1];

		init(primes); //init all to true except for position 0,1
		int currPrime = 2;
		while (currPrime <= Math.sqrt(max)) {
			if (currPrime >= max) break;
			//cross off numbers divisible by currPrime
			for (int i = currPrime * currPrime; i <= max; i += prime) {
				primes[i] = false;
			}
			//get next prime
			currPrime = getNextPrime(primes);

		}
		return primes;

	}

	//init all to true except for position 0,1
	public void init(boolean[] primes) {

	}
	//get next non-crossed off number 
	public int getNextPrime(boolean[] primes) {

	}
}