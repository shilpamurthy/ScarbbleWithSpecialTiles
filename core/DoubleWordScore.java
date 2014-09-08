package edu.cmu.cs.cs214.hw4.core;

public class DoubleWordScore extends BoardTiles {

	public DoubleWordScore(Position loc) {
		super(loc, 1, 2, true);
	}

	public String getType() {
		return "doublewordscore";
	}

}
