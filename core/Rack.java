package edu.cmu.cs.cs214.hw4.core;

public interface Rack {

	public void useTiles(int[] indexes);

	public Tiles[] getTiles(int[] indexes);

	public void addTile(Tiles tile);

}
