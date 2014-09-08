package edu.cmu.cs.cs214.hw4.core;

public class DoubleLetterScore extends BoardTiles {

	public DoubleLetterScore(Position loc) {
		super(loc, 2, 1, false);
	}

	public String getType() {
		return "doubleletterscore";
	}

}
