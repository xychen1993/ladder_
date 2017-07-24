/*
8.1 Design the data structures for a generic deck of cards. Explain how you would subclass the data structures to implement blackjack.
*/
/*
Cards : a list of numbers from 1 to 13, and each number can appear up to 4 times
Player: has Cards (starts with 2 cards)
		status: reach 21, less that 21, exceed 21
		action: ask for one more card
BlackJack: has a list of players
		   status: game ongoing
		   		   game end (someone reached 21; or no one asks for more card)
		   action: give card to player
		   		   check for winner
		   		   

*/