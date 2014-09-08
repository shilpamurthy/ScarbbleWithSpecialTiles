package edu.cmu.cs.cs214.hw4.core;

public class TripleWordScore extends BoardTiles {

	public TripleWordScore(Position loc) {
		super(loc, 1, 3, true);
	}

	public String getType() {
		return "triplewordscore";
	}

}
