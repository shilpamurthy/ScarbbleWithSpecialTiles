package edu.cmu.cs.cs214.hw4.core;

public class TripleLetterScore extends BoardTiles {

	public TripleLetterScore(Position loc) {
		super(loc, 3, 1, false);
	}

	public String getType() {
		return "tripleletterscore";
	}

}
