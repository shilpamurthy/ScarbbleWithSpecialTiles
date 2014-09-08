package edu.cmu.cs.cs214.hw4.core;

public class ReverseOrderPlayer extends SpecialTiles {

	String type = "reverseorderplayer";

	public ReverseOrderPlayer(Position pos) {
		super(pos);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return this.type;
	}

	public int effect(Game game, Position pos, int score) {
		game.reverseOrder();
		return score;
	}
}
