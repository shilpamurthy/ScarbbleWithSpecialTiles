package edu.cmu.cs.cs214.hw4.core;

public abstract class Tiles {

	Position location;
	Player owner;
	int points;

	/**
	 * Abstract tile class
	 * 
	 * @param loc
	 *            Location at which the tile is
	 */
	public Tiles(Position loc) {
		this.location = loc;
		this.owner = new Player("", -1);
	}

	/**
	 * Set points for the tile
	 * 
	 * @param p
	 */
	public void setPoints(int p) {
		this.points = p;
	}

	/**
	 * Set the location of the tile
	 * 
	 * @param loc
	 *            location specified
	 */
	public void setLocation(Position loc) {
		this.location = loc;
	}

	/**
	 * Set the owner of the tile, null if no one is the owner
	 * 
	 * @param p
	 */
	public void setOwner(Player p) {
		this.owner = p;
	}

	/**
	 * gets the owner of the tile
	 * 
	 * @return player who owns the tile, null otherwise
	 */
	public Player getOwner() {
		return this.owner;
	}

	/**
	 * gets the position of th etile
	 * 
	 * @return position of the tile
	 */
	public Position getPosition() {
		return this.location;
	}

	/**
	 * Points associated with the tile
	 * 
	 * @return
	 */
	public int getPoints() {
		return this.points;
	}

	public abstract String getType();

}
