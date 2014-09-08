package edu.cmu.cs.cs214.hw4.core;

import java.util.*;

public class Move extends Turn{

	ArrayList<Letter> move;

	public Move(ArrayList<Letter> m) {
		this.move = m;
	}

	/**
	 * FUnction to check if the move is placed on valid empty locations
	 * 
	 * @param board
	 *            The board in question
	 * @return true if all the letters are being placed on valid, empty
	 *         locations
	 */
	public boolean isPlacedOnEmptyLocations(Board board) {
		for (Letter let : this.move) {
			Position pos = let.getPosition();
			if (!let.getPosition().isValidPosition())
				return false;
			if (!(board.canATileBePlaced(pos)))
				return false;
		}

		return true;
	}

	/**
	 * Function that returns whether the move is valid
	 * 
	 * @param board
	 *            The board on which the move is placed
	 * @param isStart
	 *            whether it is the start of the game or not
	 * @return true if it is a valid move and false otherwise
	 */
	public boolean isValidMove(Board board, boolean isStart) {
		boolean res = false;
		boolean sameRow = true, sameCol = true;
		if (isStart) {
			// If it is the start then one of the tiles have to be placed in the
			// middle
			System.out.println("It is the start");
			for (Letter let : this.move) {
				Position pos = let.getPosition();
				if ((pos.getX() == 7) && (pos.getY() == 7))
				{
					System.out.println("Letter placed in middle");
					res = true;
				}
			}
		} else {
			// If it is not the start then at least one letter has to be
			// adjacent to another one
			System.out.println("NOT START");
			for (Letter let : this.move) {
				Position pos = let.getPosition();
				if (pos.isAdjacentOccupied(board))
				{
					System.out.println("Not start and is adjacent");
					res = true;
				}
			}
		}

		int row = (this.move.get(0)).getPosition().getX();
		int col = (this.move.get(0)).getPosition().getY();
		if (res) {
			for (Letter let : this.move) {
				// Check if all are either in the same row or column
				int rown = (let.getPosition().getX());
				int coln = (let.getPosition().getY());
				if (row != rown)
					sameRow = false;
				if (col != coln)
					sameCol = false;
			}
		}

		if (sameRow) {
			// check if there are any gaps in placing the letters on the board
			// by seeing if all
			// of the positions between the min and max are either occupied
			// already or a part of the move
			Position minPos = this.move.get(0).getPosition();
			Position maxPos = this.move.get(0).getPosition();
			for (Letter let : this.move) {
				if (let.getPosition().getX() < minPos.getX())
					minPos = let.getPosition();

				if (let.getPosition().getX() > maxPos.getX())
					maxPos = let.getPosition();
			}

			sameRow = isHorizontalGap(this, minPos, maxPos, board);

		}

		if (sameCol) {
			// check if there are any gaps in placing the letters on the board
			// by seeing if all
			// of the positions between the min and max are either occupied
			// already or a part of the move
			Position minPos = this.move.get(0).getPosition();
			Position maxPos = this.move.get(0).getPosition();
			int size = this.move.size();
			for (Letter let : this.move) {
				if (let.getPosition().getY() < minPos.getY())
					minPos = let.getPosition();
				if (let.getPosition().getY() > maxPos.getY())
					maxPos = let.getPosition();
			}
			for (Letter let1 : this.move) {
				if (let1.getPosition().getY() > minPos.getY() + size + 1)
					sameCol = false;
			}

			sameCol = isVerticalGap(this, minPos, maxPos, board);

		}

		return (res && (sameRow || sameCol));
	}

	/**
	 * Thsi function gets all the words formed by making the move
	 * 
	 * @param board
	 *            The board in question
	 * @return an arraylist of all the words that are formed
	 */
	public ArrayList<String> getAllWords(Board board) {
		ArrayList<String> result = new ArrayList<String>();
		StringBuilder word1 = new StringBuilder();
		StringBuilder word2 = new StringBuilder();
		boolean[][] tempBool = new boolean[15][15];
		char[][] tempChar = new char[15][15];
		// set all the corresponding positions of the
		// move to true to denote that they are being temporarily occupied
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
				tempBool[i][j] = false;
		for (Letter let : this.move) {
			// populate the matrices appropriately
			Position pos = let.getPosition();
			tempBool[pos.getX()][pos.getY()] = true;
			tempChar[pos.getX()][pos.getY()] = let.letter;
		}
		// For every letter if the word is horizontal then go up all the way to
		// the left and start constructing the word
		// by moving right each time and appending the character, if the word is
		// vertical, go all the way to the top and do the
		// same until you reach the end of the word. Put all of this in an
		// arrayList.
		for (Letter let : this.move) {
			// Check whether the word is horizontal or vartical
			boolean horiz = false, vert = false;
			Position pos = let.getPosition();
			// Find the minimum position
			while (pos.isLeftOccupied(board)
					|| tempBool[pos.getX()][pos.getY() - 1]) {
				horiz = true;
				pos = pos.getLeft();
			}
			if (!horiz) {
				horiz = pos.isRightOccupied(board)
						|| tempBool[pos.getX()][pos.getY() + 1];
			}
			
			if (horiz) {
				// Go through from the minimum position appending each letter.
				while (pos.isValidPosition()
						&& (!(board.canATileBePlaced(pos)))
						|| (tempBool[pos.getX()][pos.getY()])) {
					char l;
					if ((!(board.canATileBePlaced(pos))))
						l = (board.getLetterAt(pos)).getLetter();
					else
						l = tempChar[pos.getX()][pos.getY()];
					word1.append(l);
					pos = pos.getRight();
				}
				result.add(word1.toString());
				word1 = new StringBuilder();
			}
			Position pos1 = let.getPosition();
			// Find the minimum vertical position
			while (pos1.isUpOccupied(board)
					|| tempBool[pos1.getX() - 1][pos1.getY()]) {
				vert = true;
				pos1 = pos1.getUp();
			}
			if (!vert) {
				vert = pos1.isDownOccupied(board)
						|| tempBool[pos1.getX() + 1][pos1.getY()];
			}
			if (vert) {
				// Go through the entire word from the minimum vertical positoin
				while (pos1.isValidPosition()
						&& (!(board.canATileBePlaced(pos1)))
						|| (tempBool[pos1.getX()][pos1.getY()])) {
					char l;
					if ((!(board.canATileBePlaced(pos1))))
						l = (board.getLetterAt(pos1)).getLetter();
					else
						l = tempChar[pos1.getX()][pos1.getY()];
					word2.append(l);
					pos1 = pos1.getDown();
				}
				result.add(word2.toString());
				word2 = new StringBuilder();
			}
		}

		return result;
	}

	/**
	 * This function gets the points from all the words that are formed
	 * 
	 * @param board
	 *            the board on which the move is made
	 * @return the total score you get from all the words
	 */
	public int getAllWordPoints(Board board) {
		// Store the words that are vertical and horizontal in a hashset so that
		// no two words are scored twice
		// but if the words are horizontal and vertical they have to be scored
		// separately
		HashSet<String> res1 = new HashSet<String>();
		HashSet<String> res2 = new HashSet<String>();
		StringBuilder word1 = new StringBuilder();
		StringBuilder word2 = new StringBuilder();
		boolean[][] tempBool = new boolean[15][15];
		char[][] tempChar = new char[15][15];
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
				tempBool[i][j] = false;
		// set all the corresponding positions of the
		// move to true to denote that they are being temporarily occupied
		for (Letter let : this.move) {
			Position pos = let.getPosition();
			tempBool[pos.getX()][pos.getY()] = true;
			tempChar[pos.getX()][pos.getY()] = let.getLetter();
		}
		int count = 0;
		for (Letter let : this.move) {
			// Word multiplier that keeps track of what the word should be
			// multiplied with
			int wordMultiplier = 1;
			boolean horiz = false, vert = false;
			Position pos = let.getPosition();
			// Same logic as get all words
			// For every letter if the word is horizontal then go up all the way
			// to the left and start constructing the word
			// by moving right each time and appending the character, if the
			// word is vertical, go all the way to the top and do the
			// same until you reach the end of the word.
			while (pos.isLeftOccupied(board)
					|| tempBool[pos.getX()][pos.getY() - 1]) {
				horiz = true;
				pos = pos.getLeft();
			}
			if (!horiz) {
				horiz = pos.isRightOccupied(board)
						|| tempBool[pos.getX()][pos.getY() + 1];
			}
			if (horiz) {
				int temp = 0;
				while (pos.isValidPosition()
						&& (!(board.canATileBePlaced(pos)))
						|| (tempBool[pos.getX()][pos.getY()])) {
					char l;
					// case for if the letter is already on the board
					// then we don't apply any multipliers to it
					if ((!(board.canATileBePlaced(pos)))) {
						temp += (board.getLetterAt(pos)).getPoints();
						l = board.getLetterAt(pos).getLetter();
					}
					// Case if the letter is a part of the move you are making
					else {
						Letter letT = new Letter(pos,
								tempChar[pos.getX()][pos.getY()]);
						int points = letT.getPoints();
						int mul = board.getState(pos).getLetterMultiplier();
						// get the letter multiplier
						temp += points * mul;
						// apply the word multiplier to the overall word
						// multiplier
						wordMultiplier *= board.getState(pos)
								.getWordMultiplier();
						l = tempChar[pos.getX()][pos.getY()];
					}
					word1.append(l);
					pos = pos.getRight();
				}
				// Put the word in the hashset
				// Only add to the overall score if the word has not yet been
				// seen
				if (!(res1.contains(word1.toString()))) {
					res1.add(word1.toString());
					count += (wordMultiplier) * temp;
				}
				word1 = new StringBuilder();
			}

			wordMultiplier = 1;
			Position pos1 = let.getPosition();
			// Same logic as get all words
			// For every letter if the word is horizontal then go up all the way
			// to the left and start constructing the word
			// by moving right each time and appending the character, if the
			// word is vertical, go all the way to the top and do the
			// same until you reach the end of the word.
			while (pos1.isUpOccupied(board)
					|| tempBool[pos1.getX() - 1][pos1.getY()]) {
				vert = true;
				pos1 = pos1.getUp();
			}
			if (!vert) {
				vert = pos1.isDownOccupied(board)
						|| tempBool[pos1.getX() + 1][pos1.getY()];
			}
			if (vert) {
				int temp1 = 0;
				while (pos1.isValidPosition()
						&& ((!(board.canATileBePlaced(pos1))) || (tempBool[pos1
								.getX()][pos1.getY()]))) {
					char l;
					// case for if the letter is already on the board
					// then we don't apply any multipliers to it
					if ((!(board.canATileBePlaced(pos1)))) {
						temp1 += (board.getLetterAt(pos1)).getPoints();
						l = board.getLetterAt(pos1).getLetter();
					}
					// Case if the letter is a part of the move you are making
					else {
						Letter letT = new Letter(pos,
								tempChar[pos1.getX()][pos1.getY()]);
						int points = letT.getPoints();
						int mul = board.getState(pos1).getLetterMultiplier();
						temp1 += points * mul;
						wordMultiplier *= board.getState(pos1)
								.getWordMultiplier();
						l = tempChar[pos1.getX()][pos1.getY()];
					}
					word2.append(l);
					pos1 = pos1.getDown();
				}
				// Put the word in the hashset
				// Only add to the overall score if the word has not yet been
				// seen
				if (!(res2.contains(word2.toString()))) {
					count += (wordMultiplier * temp1);
				}
				res2.add(word2.toString());
				word2 = new StringBuilder();
			}
		}

		return count;
	}

	/**
	 * This function checks if all teh words in the array are in the dicionary
	 * 
	 * @param words
	 *            words to be checked
	 * @param dict
	 *            The given dictionary
	 * @return true if all the words are in the dictionary and false otherwise
	 */
	public boolean areWordsValid(ArrayList<String> words, Dictionary dict) {
		boolean res = true;
		for (String word : words) {
			if (!(dict.isWord(word)))
				res = false;
		}

		return res;
	}

	/**
	 * Returns the positions of the move
	 * 
	 * @return
	 */
	public ArrayList<Position> getPositions() {
		ArrayList<Position> result = new ArrayList<Position>();
		for (Letter let : this.move) {
			result.add(let.getPosition());
		}

		return result;
	}

	/**
	 * returns the type of move
	 */
	public String getType() {
		return "move";
	}

	/**
	 * Function that checks to see if there are any horizontal gaps between the
	 * min word and max
	 * 
	 * @param move
	 *            the given move
	 * @param pMin
	 *            the minimum position
	 * @param pMax
	 *            the maximum position
	 * @param board
	 *            the board in question
	 * @return true if there are no horizontal gaps and false otherwise
	 */
	public boolean isHorizontalGap(Move move, Position pMin, Position pMax,
			Board board) {
		boolean[][] temp = new boolean[15][15];
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
				temp[i][j] = false;
		for (Letter let : this.move) {
			Position pos = let.getPosition();
			temp[pos.getX()][pos.getY()] = true;
		}

		while (pMin.getX() <= pMax.getX()) {
			if (!((temp[pMin.getX()][pMin.getY()]) || !(board
					.canATileBePlaced(pMin))))
				return false;

			pMin = new Position(pMin.getX() + 1, pMin.getY());
		}

		return true;
	}

	/**
	 * Function that checks to see if there are any vertices gaps between the
	 * min word and max
	 * 
	 * @param move
	 *            the given move
	 * @param pMin
	 *            the minimum position
	 * @param pMax
	 *            the maximum position
	 * @param board
	 *            the board in question
	 * @return true if there are no vertical gaps and false otherwise
	 */
	public boolean isVerticalGap(Move move, Position pMin, Position pMax,
			Board board) {
		boolean[][] temp = new boolean[15][15];
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
				temp[i][j] = false;
		for (Letter let : this.move) {
			Position pos = let.getPosition();
			temp[pos.getX()][pos.getY()] = true;
		}

		while (pMin.getY() <= pMax.getY()) {
			if (!((temp[pMin.getX()][pMin.getY()]) || !(board
					.canATileBePlaced(pMin))))
				return false;

			pMin = new Position(pMin.getX(), pMin.getY() + 1);
		}

		return true;
	}

	/**
	 * Adds the tiles of the move to the board
	 * 
	 * @param board
	 */
	public void addTilesToBoard(Board board) {
		for (Letter let : this.move) {
			board.placeTile(let);
		}
	}

}
