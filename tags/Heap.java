/*
451. Sort Characters By Frequency
Given a string, sort it in decreasing order based on the frequency of characters.

Example 1:

Input:
"tree"

Output:
"eert"

Explanation:
'e' appears twice while 'r' and 't' both appear once.
So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.
Example 2:

Input:
"cccaaa"

Output:
"cccaaa"

Explanation:
Both 'c' and 'a' appear three times, so "aaaccc" is also a valid answer.
Note that "cacaca" is incorrect, as the same characters must be together.
Example 3:

Input:
"Aabb"

Output:
"bbAa"

Explanation:
"bbaA" is also a valid answer, but "Aabb" is incorrect.
Note that 'A' and 'a' are treated as two different characters.
*/
/*
//map(char, num of times)

//array, i -> num of times = i

//iterate array end-beginning, str.append(array[i]) i times

"tree"
map(t,1)(r,1)(e,2)
arr[1] = {t, r} arr[2] = e
"eert"
*/

//map(char, num of times)

//array, i -> char frequency is i

//array[i] = c, str.append(c) for i times

//return str
"tree"
map(t,1)(r,1)(e,2)
array[1] = {t,r}, array[2] = e
return str = "eert"
//bugs need to be fixed
public class Solution{
	//map(char, num of times)
	public String frequencySort(String s) {
		if (s == null) return "";
		Map<Character, Integer> map = new HashMap<>();
		int max = 0;
		for(Character c : s.toCharArray()) {
			if (!map.containsKey(c)) {
				map.put(c, 1);
			}else {
				map.put(c, map.get(c) + 1);
			}
			max = max < map.get(c) ? map.get(c) : max;
		}
		List<Character>[] arr = buildArray(map, max);
		return getString(arr);
	}
	//array, i -> char frequency is i
	public List<Character>[] buildArray(Map<Character, Integer> map, int max) {
		List<Character>[] arr = new ArrayList[max + 1];
		for (Character key : map.keySet()) {
			int freq = map.get(key);
			if (arr[freq] == null) {
				arr[freq] = new ArrayList<>();
			}
			arr[freq].add(key);
		}
		return arr;
	}

	//array[i] = c, str.append(c) for i times
	//Have to use StringBuilder to append to save time
	public String getString(List<Character>[] arr) {
		StringBuilder res = new StringBuilder();
		for (int i = arr.length - 1; i > 0; i--) {
			if (arr[i] != null) {
				for (Character c :  arr[i]) {
					for (int k = 0; k < i; k++)
						res.append(c);
				}
			}
		}
		return res.toString();
	}

}













