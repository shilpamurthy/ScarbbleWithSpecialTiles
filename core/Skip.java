package edu.cmu.cs.cs214.hw4.core;

public class Skip extends SpecialTiles {

	String type = "skip";

	public Skip(Position pos) {
		super(pos);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return this.type;
	}

	public int effect(Game game, Position pos, int score) {
		game.skip();
		return score;
	}
}
