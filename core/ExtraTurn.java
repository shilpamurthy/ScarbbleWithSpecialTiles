package edu.cmu.cs.cs214.hw4.core;

public class ExtraTurn extends SpecialTiles{

	public ExtraTurn(Position location) {
		super(location);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "extraturn";
	}

	@Override
	public int effect(Game game, Position pos, int score) {
		// TODO Auto-generated method stub
		game.extraTileEffect(getOwner());
		return score;
	}

}
