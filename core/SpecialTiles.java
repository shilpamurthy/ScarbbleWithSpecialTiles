package edu.cmu.cs.cs214.hw4.core;

public abstract class SpecialTiles extends Tiles {

	boolean activated;

	public SpecialTiles(Position location) {
		super(location);
	}

	public abstract String getType();

	public abstract int effect(Game game, Position pos, int score);
}
