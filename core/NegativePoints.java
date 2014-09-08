package edu.cmu.cs.cs214.hw4.core;

public class NegativePoints extends SpecialTiles {

	String type = "negativepoints";

	public NegativePoints(Position pos) {
		super(pos);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return this.type;
	}

	public int effect(Game game, Position pos, int score) {
		return -score;
	}
}
