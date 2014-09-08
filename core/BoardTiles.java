package edu.cmu.cs.cs214.hw4.core;

public abstract class BoardTiles extends Tiles {

	int Lettermultiplier, WordMultiplier;
	boolean isWM;

	/**
	 * Abstract class for board tiles that are placed on the board
	 * 
	 * @param location
	 *            position of the tile
	 * @param Lmult
	 *            the letter multiplier associated with the tile
	 * @param Wmult
	 *            the word multiplier associated with the tile
	 * @param isWM
	 */
	public BoardTiles(Position location, int Lmult, int Wmult, boolean isWM) {
		super(location);
		this.Lettermultiplier = Lmult;
		this.WordMultiplier = Wmult;
		this.isWM = isWM;
	}

	public int getLetterMultiplier() {
		return this.Lettermultiplier;
	}

	public int getWordMultiplier() {
		return this.WordMultiplier;
	}

	public abstract String getType();
}
