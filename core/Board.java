package edu.cmu.cs.cs214.hw4.core;

import java.util.*;

public class Board {

	BoardTiles[][] state = new BoardTiles[15][15];
	Letter[][] isOccupiedBy = new Letter[15][15];
	SpecialTiles[][] spTiles = new SpecialTiles[15][15];
	boolean[][] isOccupied = new boolean[15][15];
	boolean isFull = false;

	/**
	 * This method constructs the scrabble board by populating the board with
	 * word multipliers and letter multipliers and initializing the properties.
	 * We populate the plain tiles initially because we don't want to override
	 * the populating of the multipliers.
	 */
	public Board() {
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
				isOccupied[i][j] = false;

		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
				spTiles[i][j] = new DummySpecialTile(new Position(i, j));

		populatePlainTile();
		populate3WordScore();
		populate3LetterScore();
		populate2LetterScore();
		populate2WordScore();

	}

	/**
	 * This method populates all the plain tiles of the board initially
	 */
	public void populatePlainTile() {
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
				this.state[i][j] = new PlainTile(new Position(i, j));
	}

	/**
	 * This method populates the 3 word multiplier
	 */
	public void populate3WordScore() {
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++) {
				if (((i == 0) || (i == 14))
						&& ((j == 0) || (j == 7) || (j == 14))) {
					this.state[i][j] = new TripleWordScore(new Position(i, j));
				}
				if ((i == 7) && ((j == 0) || (j == 14))) {
					this.state[i][j] = new TripleWordScore(new Position(i, j));
				}
			}
	}

	/**
	 * This method populates the 2 Letter multiplier
	 */
	public void populate2LetterScore() {
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++) {
				if (((i == 0) || (i == 14)) && ((j == 3) || (j == 11)))
					this.state[i][j] = new DoubleLetterScore(new Position(i, j));
				if (((i == 3) || (i == 11))
						&& ((j == 0) || (j == 14) || (j == 7)))
					this.state[i][j] = new DoubleLetterScore(new Position(i, j));
				if (((i == 2) || (i == 12)) && ((j == 6) || (j == 8)))
					this.state[i][j] = new DoubleLetterScore(new Position(i, j));
				if (((i == 6) || (i == 8))
						&& ((j == 2) || (j == 6) || (j == 8) || (j == 12)))
					this.state[i][j] = new DoubleLetterScore(new Position(i, j));
				if ((i == 7) && ((j == 3) || (j == 11)))
					this.state[i][j] = new DoubleLetterScore(new Position(i, j));

			}
	}

	/**
	 * This method populates the 3 Letter multiplier
	 */
	public void populate3LetterScore() {
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++) {
				if (((i == 1) || (i == 13)) && ((j == 5) || (j == 9)))
					this.state[i][j] = new TripleLetterScore(new Position(i, j));
				if (((i == 5) || (i == 9))
						&& ((j == 1) || (j == 5) || (j == 9) || (j == 13)))
					this.state[i][j] = new TripleLetterScore(new Position(i, j));
			}
	}

	/**
	 * This method populates the 2 word multiplier
	 */
	public void populate2WordScore() {
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++) {
				if (((i == 1) || (i == 13)) && ((j == 1) || (j == 13)))
					this.state[i][j] = new DoubleWordScore(new Position(i, j));
				if (((i == 2) || (i == 12)) && ((j == 2) || (j == 12)))
					this.state[i][j] = new DoubleWordScore(new Position(i, j));
				if (((i == 3) || (i == 11)) && ((j == 3) || (j == 11)))
					this.state[i][j] = new DoubleWordScore(new Position(i, j));
				if (((i == 4) || (i == 10)) && ((j == 4) || (j == 10)))
					this.state[i][j] = new DoubleWordScore(new Position(i, j));

			}
	}

	/**
	 * This method returns whether you can play a tile on the board at that
	 * position by seeing whether is is being occupied which is stored in the
	 * boolean matrix
	 * 
	 * @param pos
	 *            the position on the board to be examined
	 * @return whether one can play a letter tile at the position on the board.
	 */
	public boolean canATileBePlaced(Position pos) {
		int x = pos.getX();
		int y = pos.getY();
		return (!(this.isOccupied[x][y]));
	}

	/**
	 * This method gets the letter at the desired position
	 * 
	 * @param pos
	 *            the position we want to retrieve the letter from
	 * @return a letter tile at the position if it is there or null
	 */
	public Letter getLetterAt(Position pos) {
		return this.isOccupiedBy[pos.getX()][pos.getY()];
	}

	/**
	 * This method returns the board piece(multiplier) at that position
	 * 
	 * @param pos
	 *            desired position to be examined
	 * @return either a 3Xword, 2Xword, 3Xletter, 2Xletter or plain tile
	 */
	public BoardTiles getState(Position pos) {
		return this.state[pos.getX()][pos.getY()];
	}

	/**
	 * Gets the special tile at a position
	 * 
	 * @param pos
	 *            the desired position
	 * @return either a special tile at the position or null if no special tiles
	 *         are there
	 */
	public SpecialTiles getSpTile(Position pos) {
		return this.spTiles[pos.getX()][pos.getY()];
	}

	/**
	 * This is a method that is used when boom is called. The appropriate
	 * positions are populated with null which is analogous to removing a letter
	 * from that position and the boolean matrix is updated accordingly. This
	 * method also keeps track of all the points of the letters that were
	 * destroyed and returns that as an integer
	 * 
	 * @param pos
	 *            The center location of the boom effect
	 * @return the sum of all the scores of the letters that were destroyed
	 */
	public int invalidate3Rad(Position pos) {
		ArrayList<Position> BoomPos = new ArrayList<Position>();
		int x = pos.getX();
		int y = pos.getY();
		int endY = x + 1;
		int endX = y + 1;
		BoomPos.add(new Position(x - 2, y));
		BoomPos.add(new Position(x + 2, y));
		BoomPos.add(new Position(x, y - 2));
		BoomPos.add(new Position(x, y + 2));
		int tempScore = 0;

		for (int i = x - 1; i <= endX; i++)
			for (int j = y - 1; j <= endY; j++) {
				BoomPos.add(new Position(i, j));
			}

		for (Position posses : BoomPos) {
			int a = posses.getX();
			int b = posses.getY();
			if (posses.isValidPosition()) {
				if (this.isOccupied[a][b]) {
					tempScore += this.isOccupiedBy[a][b].points;
				}
				this.isOccupiedBy[a][b] = null;
				this.isOccupied[a][b] = false;
			}
		}

		return tempScore;

	}

	/**
	 * This function places a tile on to the board by updating the appropriate
	 * matrixes
	 * 
	 * @param tile
	 *            The tile that has to be placed on the board.
	 */
	public void placeTile(Tiles tile) {
		Position pos = tile.getPosition();
		if (tile.getType().equals("letter") && pos.isValidPosition()) {
			this.isOccupiedBy[pos.getX()][pos.getY()] = (Letter) tile;
			this.isOccupied[pos.getX()][pos.getY()] = true;
			return;
		} else
			this.spTiles[pos.getX()][pos.getY()] = (SpecialTiles) tile;
	}

}
