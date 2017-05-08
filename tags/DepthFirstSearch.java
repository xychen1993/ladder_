/*
531. Lonely Pixel I
Given a picture consisting of black and white pixels, find the number of black lonely pixels.

The picture is represented by a 2D char array consisting of 'B' and 'W', which means black and white pixels respectively.

A black lonely pixel is character 'B' that located at a specific position where the same row and same column don't have any other black pixels.

Example:
Input: 
[['W', 'W', 'B'],
 ['W', 'B', 'W'],
 ['B', 'W', 'W']]

Output: 3
Explanation: All the three 'B's are black lonely pixels.
Note:
The range of width and height of the input 2D array is [1,500].
*/
/*
Iterate p:
	if p[i][j] = b:
		if DFS(i,j) = true:
			count+1
	return count;
DFS(i,j):
	Search the same column and line to check if p[i][j] != 'w'
		return false;
	else 
		return true;
*/
/*
[['W', 'W', 'B'],
['W', 'B', 'W'],
['B', 'W', 'W']]


*/
public class Solution{
	public int findLonelyPixel(char[][] picture) {
		if (picture == null) return 0;
		int count = 0;
		int lenX = picture.length;
		int lenY = picture[0].length;
		for (int i = 0; i < lenX; i++)
			for (int j = 0; j < lenY; j++) {
				if (picture[i][j] == 'B'){
					if (check(i, j, picture) == true)
						count++;
					break; //Ignore following Bs
				}
			}
		return count;
	}
	public boolean check(int x, int y, char[][] picture) {
		int lenX = picture.length;
		int lenY = picture[x].length;

		for (int i = 0; i < lenX; i++){
			if (i != x && picture[i][y] == 'B') return false;
		}

		//because elements before j must not 'B'
		for (int j = y + 1; j < lenY; j++) {
			if (picture[x][j] == 'B') return false;
		}

		return true;
	}

}











