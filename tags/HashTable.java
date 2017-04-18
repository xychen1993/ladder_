/*
TinyURL is a URL shortening service where you enter a URL such as https://leetcode.com/problems/design-tinyurl and it returns a short URL such as http://tinyurl.com/4e9iAk.

Design the encode and decode methods for the TinyURL service. There is no restriction on how your encode/decode algorithm should work. You just need to ensure that a URL can be encoded to a tiny URL and the tiny URL can be decoded to the original URL.
*/
//use a list to store long urls, and the index of long urls can be the short url.
public class Codec{
	private List<String> urls = new ArrayList<>();
	public String encode(String longUrl) {
		urls.add(longUrl);
		return String.valueOf(urls.size() - 1);

	}

	public String decode(String shortUrl) {
		int index = Integer.valueOf(shortUrl);
		return index < urls.size() ? urls.get(index) : "";

	}
}

/*
349. Intersection of Two Arrays
Given two arrays, write a function to compute their intersection.

Example:
Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2].

Note:
Each element in the result must be unique.
The result can be in any order.
*/
//Use set to store elements and check intersection
//O(N),O(N)
public class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
    	if (nums1 == null || nums2 == null) return null;
        Set<Integer> nums = new HashSet<>();
        Set<Integer> intersection = new HashSet<>();

        for (Integer num : nums1) {
        	nums.add(num);
        }
        for (Integer num : nums2) {
        	if (nums.contains(num)) 
        		intersection.add(num);
        }
        
        int[] res = new int[intersection.size()];
        int i = 0;
        for (Integer num : intersection) {
        	res[i++] = num;
        }
        return res;

    }
}











