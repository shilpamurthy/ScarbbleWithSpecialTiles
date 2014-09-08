package edu.cmu.cs.cs214.hw4.core;

public class DummySpecialTile extends SpecialTiles {

	String type = "dummyspecialtile";

	/**
	 * So that each place of the specialTile matrix can be populated, since I
	 * get effect from each special tile that is under the move
	 * 
	 * @param pos
	 *            position at which the dummy tile is placed
	 */
	public DummySpecialTile(Position pos) {
		super(pos);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return this.type;
	}

	public int effect(Game game, Position pos, int score) {
		return score;
	}

}
