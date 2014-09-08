package edu.cmu.cs.cs214.hw4.core;

public class PlainTile extends BoardTiles {

	int multiplier;

	public PlainTile(Position loc) {
		super(loc, 1, 1, false);
	}

	public String getType() {
		return "plaintile";
	}

}
