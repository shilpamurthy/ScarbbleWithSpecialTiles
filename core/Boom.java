package edu.cmu.cs.cs214.hw4.core;

public class Boom extends SpecialTiles {

	String type = "boom";

	public Boom(Position pos) {
		super(pos);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return this.type;
	}

	/**
	 * calls on teh function in game that destroys all letter tiles within a
	 * radius of 3
	 */
	public int effect(Game game, Position pos, int score) {
		int temp = game.destroy3Rad(pos);
		return score - temp;
	}

}
